package org.firstinspires.ftc.teamcode.Tools;

import android.annotation.SuppressLint;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.concurrent.TimeUnit;


@Autonomous

public class Vision extends LinearOpMode {

    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() throws InterruptedException {


        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
               //.setLensIntrinsics(515.155, 515.155, 284.375, 216.698)
                .setLensIntrinsics(510.678, 510.678, 318.787, 228.335) //(secondary calc chasis bot)
                .setTagLibrary(AprilTagGameDatabase.getCurrentGameTagLibrary())
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .build();

        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "webcam1"))
                .setCameraResolution(new Size(640,480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)


                .build();

        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {}



        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(15, TimeUnit.MILLISECONDS );

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(255);


        waitForStart();


        while (!isStopRequested() && opModeIsActive()){

            visionPortal.saveNextFrameRaw("Test");

            if(tagProcessor.getDetections().size() > 0) {
                AprilTagDetection tag = tagProcessor.getDetections().get(0);



                int id = tag.id; //'tag' is an object with an 'id' property.
                DistanceUnit Unit = DistanceUnit.METER ;





                telemetry.addLine(String.format("XYZ %6.2f %6.2f %6.2f", tag.ftcPose.x, tag.ftcPose.y, tag.ftcPose.z));
                telemetry.addData("x", tag.ftcPose.x);
                telemetry.addData("y", tag.ftcPose.y);
                telemetry.addData("z", tag.ftcPose.z);
                telemetry.addData("Unit", Unit);
                telemetry.addData("tagID", tag.id);
                telemetry.addData("exposure", exposure.isExposureSupported());
                telemetry.addData("roll", tag.ftcPose.roll);
                telemetry.addData("pitch", tag.ftcPose.pitch);
                telemetry.addData("yaw", tag.ftcPose.yaw);


            }

            telemetry.update();


        }





    }

}





