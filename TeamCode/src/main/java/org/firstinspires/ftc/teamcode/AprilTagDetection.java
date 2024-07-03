package org.firstinspires.ftc.teamcode;

import android.util.Size;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import java.util.concurrent.TimeUnit;

@TeleOp
public class AprilTagDetection extends LinearOpMode {


    public void runOpMode() throws InterruptedException {

        // AprilTag processor configuration
        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setLensIntrinsics(626.731, 626.731, 642.398, 380.131)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
                .build();

        // Vision portal configuration
        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .build();

        // Wait for the camera to start streaming
        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {}

        // Camera exposure and gain settings
        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(30, TimeUnit.MILLISECONDS);

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(200);

        // Wait for the start signal
        waitForStart();

        // Main loop
        while (!isStopRequested() && opModeIsActive()) {
            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN);
            visionPortal.getCameraState();

            if (tagProcessor.getDetections().size() > 0) {
                org.firstinspires.ftc.vision.apriltag.AprilTagDetection tag = tagProcessor.getDetections().get(0);

                // Calculate the distance using custom math
                double distanceMeters = Math.sqrt(
                      tag.ftcPose.x * tag.ftcPose.x +
                              tag.ftcPose.y * tag.ftcPose.y

                );

                // Display the telemetry data
                telemetry.addData("x", tag.ftcPose.x);
                telemetry.addData("z", tag.ftcPose.z);
                telemetry.addData("roll", tag.ftcPose.roll);
                telemetry.addData("pitch", tag.ftcPose.pitch);
                telemetry.addData("yaw", tag.ftcPose.yaw);
                telemetry.addData("total distance (meters)", distanceMeters);
                telemetry.addData("exposure", exposure.isExposureSupported());
                telemetry.addData("distance y", tag.ftcPose.y);
                telemetry.addData("tagid", tag.id);
            }
            telemetry.update();
        }
    }
}



//using tag.ftcPose.range

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
//import java.util.concurrent.TimeUnit;
//
//@TeleOp
//public class AprilTagDetection extends LinearOpMode {
//
//
//    public void runOpMode() throws InterruptedException {
//
//        // AprilTag processor configuration
//        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
//                .setDrawAxes(true)
//                .setDrawCubeProjection(true)
//                .setDrawTagID(true)
//                .setDrawTagOutline(true)
//                .setLensIntrinsics(626.731, 626.731, 642.398, 380.131)
//                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
//                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
//                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
//                .build();
//
//        // Vision portal configuration
//        VisionPortal visionPortal = new VisionPortal.Builder()
//                .addProcessor(tagProcessor)
//                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
//                .setCameraResolution(new Size(1280, 720))
//                .enableLiveView(true)
//                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
//                .build();
//
//        // Wait for the camera to start streaming
//        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {}
//
//        // Camera exposure and gain settings
//        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
//        exposure.setMode(ExposureControl.Mode.Manual);
//        exposure.setExposure(30, TimeUnit.MILLISECONDS);
//
//        GainControl gain = visionPortal.getCameraControl(GainControl.class);
//        gain.setGain(200);
//
//        // Wait for the start signal
//        waitForStart();
//
//        // Main loop
//        while (!isStopRequested() && opModeIsActive()) {
//            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN);
//            visionPortal.getCameraState();
//
//            if (tagProcessor.getDetections().size() > 0) {
//                org.firstinspires.ftc.vision.apriltag.AprilTagDetection tag = tagProcessor.getDetections().get(0);
//
//                double distanceMeters = tag.ftcPose.range;
//
//                // Display the telemetry data
//                telemetry.addData("x", tag.ftcPose.x);
//                telemetry.addData("z", tag.ftcPose.z);
//                telemetry.addData("roll", tag.ftcPose.roll);
//                telemetry.addData("pitch", tag.ftcPose.pitch);
//                telemetry.addData("yaw", tag.ftcPose.yaw);
//                telemetry.addData("total distance (meters)", distanceMeters);
//                telemetry.addData("exposure", exposure.isExposureSupported());
//                telemetry.addData("distance y", tag.ftcPose.y);
//                telemetry.addData("tagid", tag.id);
//            }
//            telemetry.update();
//        }
//    }
//}
//
//
//
////// other code using documentation not correct setup to use
////
////package org.firstinspires.ftc.teamcode;
////
////        import android.util.Size;
////        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
////        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
////        import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
////        import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
////        import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
////        import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
////        import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
////        import org.firstinspires.ftc.vision.VisionPortal;
////        import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
////        import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
////        import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
////        import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
////        import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
////        import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
////        import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
////
////        import java.util.concurrent.TimeUnit;
////
////@TeleOp
////public class AprilTagDetection extends LinearOpMode {
////
////    public void runOpMode() throws InterruptedException {
////        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
////                .setDrawAxes(true)
////                .setDrawCubeProjection(true)
////                .setDrawTagID(true)
////                .setDrawTagOutline(true)
////                .setLensIntrinsics(420.017, 420.017, 322.629, 248.306)
////                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
////                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
////                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
////                .build();
////
////        VisionPortal visionPortal = new VisionPortal.Builder()
////                .addProcessor(tagProcessor)
////                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
////                .setCameraResolution(new Size(640, 480))
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
////                    detection.ftcPose = new AprilTagPoseFtc();
////
////                    detection.ftcPose.x = detection.rawPose.x;
////                    detection.ftcPose.y = detection.rawPose.z;
////                    detection.ftcPose.z = -detection.rawPose.y;
////
////                    Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
////                    detection.ftcPose.yaw = -rot.firstAngle;
////                    detection.ftcPose.roll = rot.thirdAngle;
////                    detection.ftcPose.pitch = rot.secondAngle;
////
////                    detection.ftcPose.range = Math.hypot(detection.ftcPose.x, detection.ftcPose.y);
////                    detection.ftcPose.bearing = AngleUnit.DEGREES.fromUnit(AngleUnit.RADIANS, Math.atan2(-detection.ftcPose.x, detection.ftcPose.y));
////                    detection.ftcPose.elevation = AngleUnit.DEGREES.fromUnit(AngleUnit.RADIANS, Math.atan2(detection.ftcPose.z, detection.ftcPose.y));
////                }
////
////                double distanceInMeters = detection.ftcPose.range;
////
////                telemetry.addData("Distance (meters)", distanceInMeters);
////                telemetry.addData("x", detection.ftcPose.x);
////                telemetry.addData("y", detection.ftcPose.y);
////                telemetry.addData("z", detection.ftcPose.z);
////                telemetry.addData("roll", detection.ftcPose.roll);
////                telemetry.addData("pitch", detection.ftcPose.pitch);
////                telemetry.addData("yaw", detection.ftcPose.yaw);
////                telemetry.addData("exposure", exposure.isExposureSupported());
////                telemetry.addData("tagid", detection.id);
////                telemetry.update();
////            }
////        }
////    }
////}
