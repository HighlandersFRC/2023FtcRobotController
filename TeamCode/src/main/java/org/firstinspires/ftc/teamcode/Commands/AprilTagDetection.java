package org.firstinspires.ftc.teamcode.Commands;

import android.util.Size;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Tools.PID;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.concurrent.TimeUnit;

public class AprilTagDetection extends Command{
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
    public DcMotor Left_Back;
    public DcMotor Right_Back;
    public DcMotor Left_Front;
    public DcMotor Right_Front;
    public IMU imu;
    public double speed;
    PID PID = new PID(0.015, 0.0, 0.0);
    public AprilTagDetection(HardwareMap hardwareMap, double Speed){
        PID.setSetPoint(0);
        Left_Front = hardwareMap.dcMotor.get("Left_Front");
        Right_Front = hardwareMap.dcMotor.get("Right_Front");
        Left_Back = hardwareMap.dcMotor.get("Left_Back");
        Right_Back = hardwareMap.dcMotor.get("Right_Back");
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);

        PID.setMaxInput(180);
        PID.setMinInput(-180);
        PID.setContinuous(true);
        PID.setMinOutput(-0.05);
        PID.setMaxOutput(0.05);

        this.speed = Speed;

        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "webcam1"))
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .build();

        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {}

        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(15, TimeUnit.MILLISECONDS);

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(255);
    }
    public void start() {
    }

    public void execute() {

        if(tagProcessor.getDetections().size() > 0) {
            org.firstinspires.ftc.vision.apriltag.AprilTagDetection tag = tagProcessor.getDetections().get(0);

            int id = tag.id; // This assumes 'tag' is an object with an 'id' property.
            DistanceUnit Unit = DistanceUnit.METER ;
            double tagYaw = tag.ftcPose.yaw;
            PID.updatePID(tagYaw);
            double deviation = PID.getResult();

            Right_Front.setPower(-speed - deviation);
            Left_Front.setPower(-(-speed + deviation));
            Right_Back.setPower(-speed - deviation);
            Left_Back.setPower(-(-speed + deviation));
        } else if (tagProcessor.getDetections().size() >= 1) {
            org.firstinspires.ftc.vision.apriltag.AprilTagDetection tag = tagProcessor.getDetections().get(1);
            int id = tag.id; // This assumes 'tag' is an object with an 'id' property.
            DistanceUnit Unit = DistanceUnit.METER ;
            double tagYaw = tag.ftcPose.yaw;
            PID.updatePID(tagYaw);
            double deviation = PID.getResult();

            Right_Front.setPower(-speed - deviation);
            Left_Front.setPower(-(-speed + deviation));
            Right_Back.setPower(-speed - deviation);
            Left_Back.setPower(-(-speed + deviation));
        } else if (tagProcessor.getDetections().size() >= 2) {
            org.firstinspires.ftc.vision.apriltag.AprilTagDetection tag = tagProcessor.getDetections().get(2);

            int id = tag.id; // This assumes 'tag' is an object with an 'id' property.
            DistanceUnit Unit = DistanceUnit.METER ;
            double tagYaw = tag.ftcPose.yaw;
            PID.updatePID(tagYaw);
            double deviation = PID.getResult();

            Right_Front.setPower(-speed - deviation);
            Left_Front.setPower(-(-speed + deviation));
            Right_Back.setPower(-speed - deviation);
            Left_Back.setPower(-(-speed + deviation));
        }
        double deviation = PID.getResult();

        Right_Front.setPower(-speed - deviation);
        Left_Front.setPower(-(-speed + deviation));
        Right_Back.setPower(-speed - deviation);
        Left_Back.setPower(-(-speed + deviation));
    }

    public void end() {

    }

    public boolean isFinished() {
        return false;
    }
}