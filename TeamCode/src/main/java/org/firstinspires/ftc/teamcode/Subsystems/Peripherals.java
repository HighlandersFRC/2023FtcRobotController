package org.firstinspires.ftc.teamcode.Subsystems;

import android.util.Size;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class Peripherals extends Subsystems{
    public static String name = "Peripherals";
    public static AHRS navX;

    public static void initialize(HardwareMap hardwareMap){
        navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), AHRS.DeviceDataType.kProcessedData);
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
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .build();

    }
    public static double getYaw(){
        return navX.getYaw();
    }
    public static void resetYaw(){
        navX.zeroYaw();
    }
    public static double getRoll(){
        return navX.getRoll();
    }
    public static double getPitch(){
        return navX.getPitch();
    }
    public static double getAprilTagYaw(double targetAprilTagID){return 0;}
}