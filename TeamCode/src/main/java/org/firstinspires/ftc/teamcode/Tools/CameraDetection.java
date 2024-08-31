
package org.firstinspires.ftc.teamcode.Tools;
// imports
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import java.util.concurrent.TimeUnit;

@TeleOp
public class CameraDetection extends LinearOpMode {
    public static double CorrectX;
    public static double CorrectY;
    public static double theta;
    public static VisionPortal visionPortal;
         static AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setLensIntrinsics(626.731, 626.731, 642.398, 380.131)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagLookUp.getSmallLibrary())
                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
                .build();



public static void init(HardwareMap hardwareMap) {
    visionPortal = new VisionPortal.Builder()
            .addProcessor(tagProcessor)
            .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
            .setCameraResolution(new Size(1280, 720))
            .enableLiveView(true)
            .setStreamFormat(VisionPortal.StreamFormat.YUY2)
            .build();
}
public static void update(){
        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
        }

        // Exposure and gain control
        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(10, TimeUnit.MILLISECONDS);

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(200);
        ;

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

                    CorrectX = Constants.yCorrected(pose.y);
                    CorrectY = -Constants.xCorrected(pose.x);
                    double r = Math.sqrt((CorrectX * CorrectX) + (CorrectY * CorrectY));
                    theta = (Math.atan2(CorrectY, CorrectX));

                    double robotYaw = (Peripherals.getYaw());
                    double angleoffset = (theta + robotYaw);

                    double xt = r * (Math.cos(angleoffset + Math.PI));
                    double yt = r * (Math.sin(angleoffset + Math.PI));

                    // Retrieve AprilTagData from hashmap
                    Constants.AprilTagData tagData = Constants.aprilTagMap.get(detection.id);
                    double FieldX = xt + (tagData != null ? tagData.positionX : 0);
                    double FieldY = yt + (tagData != null ? tagData.positionY : 0);

                    // Retrieve tagAngle from hashmap
                    double tagyaw = tagData != null ? tagData.tagangle : 0;
                    double robotyawcalculated = (tagyaw + 180) - pose.yaw;
                }     }
    }
    public static double getCameraX(){
    return CorrectX;
    }
    public static double getCameraY(){
    return CorrectY;
    }
    public static double getCameraTheta(){
    return theta;
    }
    public static double getDetections(){
    return tagProcessor.getDetections().size();
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}