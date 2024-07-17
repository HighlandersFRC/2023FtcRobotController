package org.firstinspires.ftc.teamcode;
//imports
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
import java.util.concurrent.TimeUnit;

@TeleOp
public class AprilTagCustomDetection extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
//AprilTagProcessor setup
        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setLensIntrinsics(626.731, 626.731, 642.398, 380.131)
                //lens intrinsics for running at 640p .setLensIntrinsics(428.945, 428.945, 316.573,254.194 )
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagCustomLibrary.getSmallLibrary()) //custom library that you can change tag sizes in
                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
                .build();
//VisionPortal setup
        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)//do not use MJPEG
                .build();


        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
        }


        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(20, TimeUnit.MILLISECONDS);//change the exposure time according to field lighting

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(200);//you can change this too for more accuracy
        waitForStart();



        while (!isStopRequested() && opModeIsActive()) {
            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN); //built in pose solver

            for (AprilTagDetection detection : tagProcessor.getDetections()) {
                if (detection.rawPose != null) {
                    double x = detection.rawPose.x;
                    double y = detection.rawPose.z;
                    double z = -detection.rawPose.y;
//lines 64-73 are from ftc documentation for the ftc coordinate system
                    Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
                    double yaw = -rot.firstAngle;
                    double roll = rot.thirdAngle;
                    double pitch = rot.secondAngle;
//pythagorean math to get the range this is not corrected and is not accurate
                    double range = Math.sqrt(z * z + y * y);
                    //not sure if 77 and 78 are being used
                    double bearing = Math.toDegrees(Math.atan2(y, -x));
                    double elevation = Math.toDegrees(Math.atan2(z, Math.sqrt(x * x + y * y)));

                    AprilTagPoseFtc pose = new AprilTagPoseFtc(x, y, z, yaw, roll, pitch, range, bearing, elevation);
//corrected range uses a quartic regression to calculate an offset found in Constants
                    double CorrectedRange = Constants.fiveCorrected(pose.range);
                    //the angle between the camera and the tag center.
                    double theta = Math.asin(z/CorrectedRange);

//telemetry
                    telemetry.addData("not corrected distance",pose.range);
                    telemetry.addData("range corrected", CorrectedRange);
                    telemetry.addData("theta",theta);
                    telemetry.addData("Tag ID", detection.id);
                    telemetry.addData("tagsize", detection.metadata.tagsize);
                    telemetry.addData("x", pose.x);
                    telemetry.addData("y", pose.y);
                    telemetry.addData("z", pose.z);
                    telemetry.addData("roll", pose.roll);
                    telemetry.addData("pitch", pose.pitch);
                    telemetry.addData("yaw", pose.yaw);
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

