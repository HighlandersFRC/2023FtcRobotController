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

        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setLensIntrinsics(626.731, 626.731, 642.398, 380.131)
                //640 p .setLensIntrinsics(428.945, 428.945, 316.573,254.194 )
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagCustomLibrary.getSmallLibrary())
                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
                .build();

        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .build();

        // Ensure the camera is streaming
        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
        }

        // Set camera exposure and gain
        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(20, TimeUnit.MILLISECONDS);

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(200);
        waitForStart();



        while (!isStopRequested() && opModeIsActive()) {
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

                    double range = Math.sqrt(z * z + y * y); // 3D distance
                    double bearing = Math.toDegrees(Math.atan2(y, -x)); // Horizontal angle
                    double elevation = Math.toDegrees(Math.atan2(z, Math.sqrt(x * x + y * y))); // Vertical angle

                    AprilTagPoseFtc pose = new AprilTagPoseFtc(x, y, z, yaw, roll, pitch, range, bearing, elevation);

                    double Correctedrange = Constants.fiveCorrected(pose.range);



                    telemetry.addData("not corrected distance",pose.range);
                    telemetry.addData("Tag ID", detection.id);
                    telemetry.addData("tagsize", detection.metadata.tagsize);
                    telemetry.addData("x", pose.x);
                    telemetry.addData("y", pose.y);
                    telemetry.addData("z", pose.z);
                    telemetry.addData("roll", pose.roll);
                    telemetry.addData("pitch", pose.pitch);
                    telemetry.addData("yaw", pose.yaw);
                    telemetry.addData("range corrected", Correctedrange);
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

