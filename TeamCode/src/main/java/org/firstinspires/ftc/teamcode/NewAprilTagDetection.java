//used for testing go to AprilTagCustomDetection
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
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import java.util.concurrent.TimeUnit;

@TeleOp
public class NewAprilTagDetection extends LinearOpMode {

    public void runOpMode() throws InterruptedException {

        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setLensIntrinsics(315.824, 315.824, 319.008, 192.166)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
                .build();

        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
                .setCameraResolution(new Size(640, 360))
                .enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .build();

        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {}

        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(30, TimeUnit.MILLISECONDS);

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(200);

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.OPENCV_SOLVEPNP_EPNP);
            visionPortal.getCameraState();

            for (AprilTagDetection detection : tagProcessor.getDetections()) {
                if (detection.rawPose != null) {

                    double x = detection.rawPose.x;
                    double y = detection.rawPose.z;
                    double z = detection.rawPose.y;

                    Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
                   double yaw = -rot.firstAngle;
                    double roll = rot.thirdAngle;
                    double pitch = rot.secondAngle;

                    double range = Math.sqrt(z * z + y * y); // 3D distance
                    double bearing = Math.toDegrees(Math.atan2(y, -x)); // Horizontal angle
                    double elevation = Math.toDegrees(Math.atan2(z, Math.sqrt(x * x + y * y))); // Vertical angle

                    AprilTagPoseFtc pose = new AprilTagPoseFtc(x, y, z, yaw, roll, pitch, range, bearing, elevation);

                    double distanceInMeters = pose.range;


                    telemetry.addData("Distance (meters)", distanceInMeters);
                    telemetry.addData("x", pose.x);
                    telemetry.addData("y", pose.y);
                    telemetry.addData("z", pose.z);
                    telemetry.addData("roll", pose.roll);
                    telemetry.addData("pitch", pose.pitch);
                    telemetry.addData("yaw", pose.yaw);
                    telemetry.addData("range (3D distance)", pose.range);
                    telemetry.addData("bearing (horizontal angle)", pose.bearing);
                    telemetry.addData("elevation (vertical angle)", pose.elevation);
                    telemetry.addData("exposure", exposure.isExposureSupported());
                    telemetry.addData("tagid", detection.id);
                    telemetry.update();

                }
            }
        }
    }
}


////100! :)
////testing data for offset
////actual 10.5 in v: 0.35 meters
////13.5 in v: 0.42 m
////17.5 v: 0.539m
////23 in v:0.706 m
////42 7/8 inches v: 1.47m

//
//
//
//
///*
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
//                .setLensIntrinsics(312.056, 312.056, 313.04, 200.062)
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
//            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN);
//            visionPortal.getCameraState();
//
//            for (AprilTagDetection detection : tagProcessor.getDetections()) {
//                if (detection.rawPose != null) {
//
//                    double x = detection.rawPose.x;
//                    double y = detection.rawPose.z;
//                    double z = -detection.rawPose.y;
//
//                    Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
//                    double yaw = -rot.firstAngle;
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
//                }
//            }
//        }
//    }
//}
//
//
////100! :)*/
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
//        // Tag size in meters (5 inches in meters)
//        double tagSize = 0.127;
//        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
//                .setDrawAxes(true)
//                .setDrawCubeProjection(true)
//                .setDrawTagID(true)
//                .setDrawTagOutline(true)
//                .setLensIntrinsics(312.056, 312.056, 313.04, 200.062)
//                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
//                .setTagLibrary(AprilTagCustomLibrary.getsmallLibrary())
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
//            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN);
//            visionPortal.getCameraState();
//
//            for (AprilTagDetection detection : tagProcessor.getDetections()) {
//                if (detection.rawPose != null) {
//
//                    double x = detection.rawPose.x;
//                    double y = detection.rawPose.z;
//                    double z = -detection.rawPose.y;
//
//                    Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
//                    double yaw = -rot.firstAngle;
//                    double roll = rot.thirdAngle;
//                    double pitch = rot.secondAngle;
//
//                    double range = Math.sqrt(z * z + y * y); // 3D distance
//                    double bearing = Math.toDegrees(Math.atan2(y, -x)); // Horizontal angle
//                    double elevation = Math.toDegrees(Math.atan2(z, Math.sqrt(x * x + y * y))); // Vertical angle
//
//                    AprilTagPoseFtc pose = new AprilTagPoseFtc(x, y, z, yaw, roll, pitch, range, bearing, elevation);
//
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
//                }
//            }
//        }
//    }
//}
//
