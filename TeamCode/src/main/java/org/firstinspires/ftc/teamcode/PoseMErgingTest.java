/*
package org.firstinspires.ftc.teamcode;

import android.util.Size;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Tools.Odometry;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import java.util.concurrent.TimeUnit;

@TeleOp
public class PoseMErgingTest extends LinearOpMode {

    @Override
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

        // Exposure and Gain settings
        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(10, TimeUnit.MILLISECONDS);

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(200);

        // IMU setup
        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters imuParameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(imuParameters);
        imu.resetYaw();

        // Odometry setup
        Odometry odometry = new Odometry(hardwareMap);

        // Pose merging setup
        PoseMerging poseMerging = new PoseMerging(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            // Update odometry
            odometry.update();

            // AprilTag detections
            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN);
            for (AprilTagDetection detection : tagProcessor.getDetections()) {
                if (detection.rawPose != null) {
                    double x = detection.rawPose.x;
                    double y = detection.rawPose.z;
                    double z = -detection.rawPose.y;

                    double yaw = -Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES).firstAngle;

                    // Convert to field coordinates
                    double CorrectX = ConstantsVision.yCorrected(y);
                    double CorrectY = -ConstantsVision.xCorrected(x);
                    double r = Math.sqrt((CorrectX * CorrectX) + (CorrectY * CorrectY));
                    double polartheta = Math.atan2(CorrectY, CorrectX);
                    double robotYaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
                    double angleoffset = polartheta + robotYaw;
                    double xt = r * Math.cos(angleoffset + Math.PI);
                    double yt = r * Math.sin(angleoffset + Math.PI);

                    ConstantsVision.AprilTagData tagData = ConstantsVision.aprilTagMap.get(detection.id);
                    double FieldX = xt + (tagData != null ? tagData.positionX : 0);
                    double FieldY = yt + (tagData != null ? tagData.positionY : 0);
                    double tagyaw = tagData != null ? tagData.tagangle : 0;
                    double theta = (tagyaw + 180) - yaw;

                    // Update the camera pose in the pose merging system
                    poseMerging.updateCameraPose(FieldX, FieldY, theta);
                }
            }

            // Get the merged pose
            double[] mergedPose = poseMerging.getPose();

            // Telemetry
            telemetry.addData("Merged Pose X", mergedPose[0]);
            telemetry.addData("Merged Pose Y", mergedPose[1]);
            telemetry.addData("Merged Pose Theta", mergedPose[2]);
            telemetry.addData("Odometry X", odometry.getX());
            telemetry.addData("Odometry Y", odometry.getY());
            telemetry.addData("Odometry Theta", odometry.getTheta());
            telemetry.update();
        }
    }
}
*/
