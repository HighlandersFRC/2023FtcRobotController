package org.firstinspires.ftc.teamcode.Subsystems;
import android.util.Size;

import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Peripherals extends Subsystems{
    public static String name = "Peripherals";
    public static AHRS navX;
    static VisionPortal visionPortal;
    static AprilTagProcessor tagProcessor;
    public static double navXOffset;
    public static void initialize(HardwareMap hardwareMap){
        navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), AHRS.DeviceDataType.kProcessedData);
    }
    public static double getYaw(){
        if ((navX.getYaw() - navXOffset ) > 180){
            return -(navX.getYaw() - navXOffset - 360);
        }else
        if ((navX.getYaw() - navXOffset ) < -180){
            return -(navX.getYaw() - navXOffset + 360);
        }
        else {
            return -(navX.getYaw() - navXOffset);
        }
    }
    public static void resetYaw(){navXOffset = navX.getYaw();}
    public static double getRoll(){
        return navX.getRoll();
    }
    public static double getPitch(){
        return navX.getPitch();
    }
}
//s