package org.firstinspires.ftc.teamcode.Tools;

import android.util.Size;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;

import java.util.concurrent.TimeUnit;

public class FieldOfMerit {

    public static AprilTagProcessor tagProcessor;
    public static VisionPortal visionPortal;
    private Telemetry telemetry;

    private static double fieldX;
    private static double fieldY;
    private static double theta;
    public static double x;
    public static double y;
    public static double tagyaw;
    public static String currentState = "Odometry Pods";

    public static void initialize(HardwareMap hardwareMap) {
        Drive.initialize(hardwareMap);
        Peripherals.initialize(hardwareMap);

        tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setLensIntrinsics(626.731, 626.731, 642.398, 380.131)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagLookUp.getSmallLibrary())
                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
                .build();


        visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .build();

        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {

        }

        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(10, TimeUnit.MILLISECONDS);

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(200);

        Peripherals.resetYaw();
    }

    public static void processTags() {
        tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN);

        boolean tagDetected = false;

        for (AprilTagDetection detection : tagProcessor.getDetections()) {
            if (detection.rawPose != null) {
                tagDetected = true;

                currentState = "Vision";

                x = detection.rawPose.x;
                y = detection.rawPose.z;
                double z = -detection.rawPose.y;

                Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
                double yaw = -rot.firstAngle;
                double roll = rot.thirdAngle;
                double pitch = rot.secondAngle;

                double range = Math.sqrt(5 * 5 + y * y);
                double bearing = Math.toDegrees(Math.atan2(x, y));
                double elevation = Math.toDegrees(Math.atan2(z, Math.sqrt(x * x + y * y)));

                AprilTagPoseFtc pose = new AprilTagPoseFtc(x, y, z, yaw, roll, pitch, range, bearing, elevation);

                double CorrectX = Constants.yCorrected(pose.y);
                double CorrectY = -Constants.xCorrected(pose.x);
                double r = Math.sqrt((CorrectX * CorrectX) + (CorrectY * CorrectY));
                double thetapolar = (Math.atan2(CorrectY, CorrectX));

                double robotYaw = Peripherals.getYaw();
                double angleoffset = (thetapolar + robotYaw);

                double xt = r * (Math.cos(angleoffset + Math.PI));
                double yt = r * (Math.sin(angleoffset + Math.PI));


                Constants.AprilTagData tagData = Constants.aprilTagMap.get(detection.id);
                fieldX = xt + (tagData != null ? tagData.positionX : 0);
                fieldY = yt + (tagData != null ? tagData.positionY : 0);

                tagyaw = tagData != null ? tagData.tagangle : 0;
                theta = (tagyaw + 180) - pose.yaw;

                Drive.setPosition(fieldX, fieldY, theta);
                FinalPose.setfinalPose(fieldX, fieldY, theta);
            }
        }

        if (!tagDetected) {
            fieldX = Drive.getOdometryX();
            fieldY = Drive.getOdometryY();
            theta = Drive.getOdometryTheta();

            currentState = "Odometry Pods";
            FinalPose.setfinalPose(fieldX, fieldY, theta);
            tagDetected = true;
        }
    }

    public double getFieldX() {
        return fieldX;
    }

    public double getFieldY() {
        return fieldY;
    }

    public double getTheta() {
        return theta;
    }
    public static void getIDPose(){

    }
}