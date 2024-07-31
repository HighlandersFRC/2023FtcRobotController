////used for testing go to AprilTagCustomDetection
//package org.firstinspires.ftc.teamcode;
//
//import android.util.Size;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
//import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
//import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
//import java.util.concurrent.TimeUnit;
//
//@TeleOp
//public class NewAprilTagDetection extends LinearOpMode {
//
//    public void runOpMode() throws InterruptedException {
//
//        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
//                .setDrawAxes(true)
//                .setDrawCubeProjection(true)
//                .setDrawTagID(true)
//                .setDrawTagOutline(true)
//                .setLensIntrinsics(315.824, 315.824, 319.008, 192.166)
//                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
//                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
//                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
//                .build();
//
//        VisionPortal visionPortal = new VisionPortal.Builder()
//                .addProcessor(tagProcessor)
//                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
//                .setCameraResolution(new Size(640, 360))
//                .enableLiveView(true)
//                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
//                .build();
//
//        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {}
//
//        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
//        exposure.setMode(ExposureControl.Mode.Manual);
//        exposure.setExposure(30, TimeUnit.MILLISECONDS);
//
//        GainControl gain = visionPortal.getCameraControl(GainControl.class);
//        gain.setGain(200);
//
//        waitForStart();
//
//        while (!isStopRequested() && opModeIsActive()) {
//            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.OPENCV_SOLVEPNP_EPNP);
//            visionPortal.getCameraState();
//
//            for (AprilTagDetection detection : tagProcessor.getDetections()) {
//                if (detection.rawPose != null) {
//
//                    double x = detection.rawPose.x;
//                    double y = detection.rawPose.z;
//                    double z = detection.rawPose.y;
//
//                    Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
//                   double yaw = -rot.firstAngle;
//                    double roll = rot.thirdAngle;
//                    double pitch = rot.secondAngle;
//
//                    double range = Math.sqrt(z * z + y * y); // 3D distance
//                    double bearing = Math.toDegrees(Math.atan2(y, -x)); // Horizontal angle
//                    double elevation = Math.toDegrees(Math.atan2(z, Math.sqrt(x * x + y * y))); // Vertical angle
//
//                    AprilTagPoseFtc pose = new AprilTagPoseFtc(x, y, z, yaw, roll, pitch, range, bearing, elevation);
//
//                    double distanceInMeters = pose.range;
//
//
//                    telemetry.addData("Distance (meters)", distanceInMeters);
//                    telemetry.addData("x", pose.x);
//                    telemetry.addData("y", pose.y);
//                    telemetry.addData("z", pose.z);
//                    telemetry.addData("roll", pose.roll);
//                    telemetry.addData("pitch", pose.pitch);
//                    telemetry.addData("yaw", pose.yaw);
//                    telemetry.addData("range (3D distance)", pose.range);
//                    telemetry.addData("bearing (horizontal angle)", pose.bearing);
//                    telemetry.addData("elevation (vertical angle)", pose.elevation);
//                    telemetry.addData("exposure", exposure.isExposureSupported());
//                    telemetry.addData("tagid", detection.id);
//                    telemetry.update();
//
//                }
//            }
//        }
//    }
//}
//
//
//////100! :)
//////testing data for offset
//////actual 10.5 in v: 0.35 meters
//////13.5 in v: 0.42 m
//////17.5 v: 0.539m
//////23 in v:0.706 m
//////42 7/8 inches v: 1.47m
//
////
////
////
////
/////*
////package org.firstinspires.ftc.teamcode;
////
////import android.util.Size;
////import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
////import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
////import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
////import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
////import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
////import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
////import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
////import org.firstinspires.ftc.vision.VisionPortal;
////import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
////import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
////import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
////import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
////import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
////import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
////import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
////import java.util.concurrent.TimeUnit;
////
////@TeleOp
////public class NewAprilTagDetection extends LinearOpMode {
////
////    public void runOpMode() throws InterruptedException {
////
////        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
////                .setDrawAxes(true)
////                .setDrawCubeProjection(true)
////                .setDrawTagID(true)
////                .setDrawTagOutline(true)
////                .setLensIntrinsics(312.056, 312.056, 313.04, 200.062)
////                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
////                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
////                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
////                .build();
////
////        VisionPortal visionPortal = new VisionPortal.Builder()
////                .addProcessor(tagProcessor)
////                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
////                .setCameraResolution(new Size(640, 360))
////                .enableLiveView(true)
////                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
////                .build();
////
////        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {}
////
////        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
////        exposure.setMode(ExposureControl.Mode.Manual);
////        exposure.setExposure(30, TimeUnit.MILLISECONDS);
////
////        GainControl gain = visionPortal.getCameraControl(GainControl.class);
////        gain.setGain(200);
////
////        waitForStart();
////
////        while (!isStopRequested() && opModeIsActive()) {
////            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN);
////            visionPortal.getCameraState();
////
////            for (AprilTagDetection detection : tagProcessor.getDetections()) {
////                if (detection.rawPose != null) {
////
////                    double x = detection.rawPose.x;
////                    double y = detection.rawPose.z;
////                    double z = -detection.rawPose.y;
////
////                    Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
////                    double yaw = -rot.firstAngle;
////                    double roll = rot.thirdAngle;
////                    double pitch = rot.secondAngle;
////
////                    double range = Math.sqrt(z * z + y * y); // 3D distance
////                    double bearing = Math.toDegrees(Math.atan2(y, -x)); // Horizontal angle
////                    double elevation = Math.toDegrees(Math.atan2(z, Math.sqrt(x * x + y * y))); // Vertical angle
////
////                    AprilTagPoseFtc pose = new AprilTagPoseFtc(x, y, z, yaw, roll, pitch, range, bearing, elevation);
////
////                    double distanceInMeters = pose.range;
////
////                    telemetry.addData("Distance (meters)", distanceInMeters);
////                    telemetry.addData("x", pose.x);
////                    telemetry.addData("y", pose.y);
////                    telemetry.addData("z", pose.z);
////                    telemetry.addData("roll", pose.roll);
////                    telemetry.addData("pitch", pose.pitch);
////                    telemetry.addData("yaw", pose.yaw);
////                    telemetry.addData("range (3D distance)", pose.range);
////                    telemetry.addData("bearing (horizontal angle)", pose.bearing);
////                    telemetry.addData("elevation (vertical angle)", pose.elevation);
////                    telemetry.addData("exposure", exposure.isExposureSupported());
////                    telemetry.addData("tagid", detection.id);
////                    telemetry.update();
////                }
////            }
////        }
////    }
////}
////
////
//////100! :)*/
////package org.firstinspires.ftc.teamcode;
////
////import android.util.Size;
////import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
////import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
////import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
////import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
////import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
////import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
////import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
////import org.firstinspires.ftc.vision.VisionPortal;
////import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
////import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
////import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
////import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
////import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
////import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
////import java.util.concurrent.TimeUnit;
////
////@TeleOp
////public class NewAprilTagDetection extends LinearOpMode {
////
////    public void runOpMode() throws InterruptedException {
////
////        // Tag size in meters (5 inches in meters)
////        double tagSize = 0.127;
////        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
////                .setDrawAxes(true)
////                .setDrawCubeProjection(true)
////                .setDrawTagID(true)
////                .setDrawTagOutline(true)
////                .setLensIntrinsics(312.056, 312.056, 313.04, 200.062)
////                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
////                .setTagLibrary(AprilTagCustomLibrary.getsmallLibrary())
////                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
////                .build();
////
////        VisionPortal visionPortal = new VisionPortal.Builder()
////                .addProcessor(tagProcessor)
////                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
////                .setCameraResolution(new Size(640, 360))
////                .enableLiveView(true)
////                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
////                .build();
////
////        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {}
////
////        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
////        exposure.setMode(ExposureControl.Mode.Manual);
////        exposure.setExposure(30, TimeUnit.MILLISECONDS);
////
////        GainControl gain = visionPortal.getCameraControl(GainControl.class);
////        gain.setGain(200);
////
////        waitForStart();
////
////        while (!isStopRequested() && opModeIsActive()) {
////            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN);
////            visionPortal.getCameraState();
////
////            for (AprilTagDetection detection : tagProcessor.getDetections()) {
////                if (detection.rawPose != null) {
////
////                    double x = detection.rawPose.x;
////                    double y = detection.rawPose.z;
////                    double z = -detection.rawPose.y;
////
////                    Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
////                    double yaw = -rot.firstAngle;
////                    double roll = rot.thirdAngle;
////                    double pitch = rot.secondAngle;
////
////                    double range = Math.sqrt(z * z + y * y); // 3D distance
////                    double bearing = Math.toDegrees(Math.atan2(y, -x)); // Horizontal angle
////                    double elevation = Math.toDegrees(Math.atan2(z, Math.sqrt(x * x + y * y))); // Vertical angle
////
////                    AprilTagPoseFtc pose = new AprilTagPoseFtc(x, y, z, yaw, roll, pitch, range, bearing, elevation);
////
////                    telemetry.addData("x", pose.x);
////                    telemetry.addData("y", pose.y);
////                    telemetry.addData("z", pose.z);
////                    telemetry.addData("roll", pose.roll);
////                    telemetry.addData("pitch", pose.pitch);
////                    telemetry.addData("yaw", pose.yaw);
////                    telemetry.addData("range (3D distance)", pose.range);
////                    telemetry.addData("bearing (horizontal angle)", pose.bearing);
////                    telemetry.addData("elevation (vertical angle)", pose.elevation);
////                    telemetry.addData("exposure", exposure.isExposureSupported());
////                    telemetry.addData("tagid", detection.id);
////                    telemetry.update();
////                }
////            }
////        }
////    }
////}
////

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
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import org.firstinspires.ftc.teamcode.Tools.pm;

@TeleOp
public class NewAprilTagDetection extends LinearOpMode {

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

