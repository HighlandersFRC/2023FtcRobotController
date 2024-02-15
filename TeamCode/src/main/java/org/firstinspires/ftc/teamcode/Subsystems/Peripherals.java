package org.firstinspires.ftc.teamcode.Subsystems;
import android.annotation.SuppressLint;
import android.util.Size;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
public class Peripherals extends Subsystems{
    public static String name = "Peripherals";
    public static AHRS navX;
    public static IMU imu;
    static VisionPortal visionPortal;
    static AprilTagProcessor tagProcessor;
    public static double navXOffset;
    public static void initialize(HardwareMap hardwareMap){
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        imu.initialize(parameters);
        navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), AHRS.DeviceDataType.kProcessedData);
    }
    @SuppressLint("SuspiciousIndentation")
    public static double getYaw() {
        if (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) == 0) {
            if ((navX.getYaw() - navXOffset) > 180) {
                Constants.currentNavigationalSensor = "NavX";
                return -(navX.getYaw() - navXOffset - 360);
            } else if ((navX.getYaw() - navXOffset) < -180) {
                Constants.currentNavigationalSensor = "NavX";
                return -(navX.getYaw() - navXOffset + 360);
            } else {
                Constants.currentNavigationalSensor = "NavX";
                return -(navX.getYaw() - navXOffset);
            }
        } else {
            Constants.currentNavigationalSensor = "IMU";
            return -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        }
    }
    public static double getNavXYaw(){
        if ((navX.getYaw() - navXOffset) > 180) {
            Constants.currentNavigationalSensor = "NavX";
            return -(navX.getYaw() - navXOffset - 360);
        } else if ((navX.getYaw() - navXOffset) < -180) {
            Constants.currentNavigationalSensor = "NavX";
            return -(navX.getYaw() - navXOffset + 360);
        } else {
            Constants.currentNavigationalSensor = "NavX";
            return -(navX.getYaw() - navXOffset);
        }
    }
    public static void resetYaw(){
        navXOffset = navX.getYaw();
        imu.resetYaw();
    }
    public static double getRoll(){
        return navX.getRoll();
    }
    public static double getPitch(){
        return navX.getPitch();
    }
    public static boolean navxIsConnect(){return navX.isConnected();}
    public static double imuYaw(){return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);}
    public static void  imuReset(){imu.resetYaw();}
}