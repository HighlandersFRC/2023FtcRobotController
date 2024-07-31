/*
//package org.firstinspires.ftc.teamcode;
//
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//
//public class AprilTagLibrary {
//
//    public static org.firstinspires.ftc.vision.apriltag.AprilTagLibrary getSmallLibrary() {
//return new org.firstinspires.ftc.vision.apriltag.AprilTagLibrary.Builder()
//        .addTag(
//                7,
//                "tag 7",
//                0.127,
//                DistanceUnit.METER
//        )
//        .build();
//
//    }
//}
package org.firstinspires.ftc.teamcode;

// imports
import android.util.Size;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
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
import org.firstinspires.ftc.teamcode.Tools.pm;
import org.firstinspires.ftc.teamcode.Tools.Odometry;

@TeleOp
public class AprilTagOdometryTest extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        // AprilTagProcessor setup
        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
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
        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .build();

        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
        }

        // Exposure and gain control
        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(10, TimeUnit.MILLISECONDS);

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(200);

        // IMU setup
        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        // Initialize Odometry
        Odometry.initialize(hardwareMap);

        // IMU yaw reset with controller and initialization
        imu.resetYaw();

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            if (gamepad1.x) {
                imu.resetYaw();
            }

            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN);

            for (AprilTagDetection detection : tagProcessor.getDetections()) {
                if (detection.rawPose != null) {
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

                    // Merge pose with odometry
                    pm.mergePose(FieldX, FieldY, theta);

                    // Get updated odometry position
                    double odometryX = Odometry.getX();
                    double odometryY = Odometry.getY();
                    double odometryTheta = Odometry.getTheta();

                    telemetry.addData("Odometry X", odometryX);
                    telemetry.addData("Odometry Y", odometryY);
                    telemetry.addData("Odometry Theta", odometryTheta);

                    telemetry.addData("robotyawcalculated", theta);
                    telemetry.addData("pose", String.format("(%.2f, %.2f)", FieldX, FieldY));
                    telemetry.addData("CorrectX", CorrectX);
                    telemetry.addData("CorrectY", CorrectY);
                    telemetry.addData("radius", r);
                    telemetry.addData("angleoffset", angleoffset);
                    telemetry.addData("xt", xt);
                    telemetry.addData("yt", yt);
                    telemetry.addData("Tag ID", detection.id);
                    telemetry.addData("tagsize", detection.metadata.tagsize);
                    telemetry.addData("x", CorrectX);
                    telemetry.addData("y", CorrectY);
                    telemetry.addData("z", 5);
                    telemetry.addData("robotyaw", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
                    telemetry.addData("pose.yaw", pose.yaw);
                    telemetry.addData("roll", pose.roll);
                    telemetry.addData("pitch", pose.pitch);
                    telemetry.addData("yaw", Math.toDegrees(Math.PI) - (pose.yaw));
                    telemetry.addData("bearing (horizontal angle)", pose.bearing);
                    telemetry.addData("elevation (vertical angle)", pose.elevation);
                    telemetry.addData("Raw Pose x", detection.rawPose.x);
                    telemetry.addData("Raw Pose y", detection.rawPose.y);
                    telemetry.addData("Raw Pose z", detection.rawPose.z);
                    telemetry.addData("exposure", exposure.isExposureSupported());
                    telemetry.update();
                }
            }
        }
    }
}
*/
