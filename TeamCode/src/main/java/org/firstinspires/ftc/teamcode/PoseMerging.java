package org.firstinspires.ftc.teamcode;

import android.util.Size;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Tools.Odometry;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import java.util.concurrent.TimeUnit;

@TeleOp
public class PoseMerging extends LinearOpMode {

    private AprilTagProcessor tagProcessor;
    private VisionPortal visionPortal;
    private IMU imu;
    private Odometry odometry;
    private boolean tagVisible = false; // Flag to track if the AprilTag is visible

    @Override
    public void runOpMode() throws InterruptedException {
        // AprilTagProcessor setup
        tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setLensIntrinsics(626.731, 626.731, 642.398, 380.131)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagCustomLibrary.getSmallLibrary())
                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
                .build();

        // VisionPortal setup
        visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .build();

        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            // Wait for the camera to start streaming
        }

        // Exposure and gain control
        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(10, TimeUnit.MILLISECONDS);

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(200);

        // IMU setup
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);
        imu.resetYaw();

        // Initialize odometry with hardware map
        odometry = new Odometry(hardwareMap);
        odometry.initialize(hardwareMap); // Initialize hardware

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN);

            boolean tagDetected = false;

            for (AprilTagDetection detection : tagProcessor.getDetections()) {
                if (detection.rawPose != null) {
                    tagDetected = true;
                    tagVisible = true;
                    double x = detection.rawPose.x;
                    double y = detection.rawPose.z;
                    double z = -detection.rawPose.y;

                    Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
                    double yaw = -rot.firstAngle;
                    double roll = rot.thirdAngle;
                    double pitch = rot.secondAngle;
                    double range = Math.sqrt(5 * 5 + y * y);
                    double bearing = Math.toDegrees(Math.atan2(x, y));
                    double elevation = Math.toDegrees(Math.atan2(z, Math.sqrt(x * x + y * y)));

                    AprilTagPoseFtc pose = new AprilTagPoseFtc(x, y, z, yaw, roll, pitch, range, bearing, elevation);

                    double CorrectX = ConstantsVision.yCorrected(pose.y);
                    double CorrectY = -ConstantsVision.xCorrected(pose.x);
                    double r = Math.sqrt((CorrectX * CorrectX) + (CorrectY * CorrectY));
                    double thetapolar = (Math.atan2(CorrectY, CorrectX));

                    double robotYaw = (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
                    double angleoffset = (thetapolar + robotYaw);

                    double xt = r * (Math.cos(angleoffset + Math.PI));
                    double yt = r * (Math.sin(angleoffset + Math.PI));

                    // Retrieve AprilTagData from hashmap
                    ConstantsVision.AprilTagData tagData = ConstantsVision.aprilTagMap.get(detection.id);
                    double FieldX = xt + (tagData != null ? tagData.positionX : 0);
                    double FieldY = yt + (tagData != null ? tagData.positionY : 0);

                    // Retrieve tagAngle from hashmap
                    double tagyaw = tagData != null ? tagData.tagangle : 0;
                    double theta = (tagyaw + 180) - pose.yaw;

                    // Reset encoders to the detected position
                     odometry.setCurrentPositionAndResetEncoders(FieldX, FieldY, theta);


                    telemetry.addData("FieldX", FieldX);
                    telemetry.addData("FieldY", FieldY);
                    telemetry.addData("Theta", theta);
                    telemetry.update();
                }
            }

            if (!tagDetected) {
                tagVisible = false;
                // Update odometry if the tag is not visible
                odometry.update();

                // Retrieve the odometry values
                double x = Odometry.getOdometryX();
                double y = Odometry.getOdometryY();
                double theta = Odometry.getOdometryTheta();

                telemetry.addData("Odometry X", x);
                telemetry.addData("Odometry Y", y);
                telemetry.addData("Odometry Theta", theta);
                telemetry.update();
            }
        }
    }
}
