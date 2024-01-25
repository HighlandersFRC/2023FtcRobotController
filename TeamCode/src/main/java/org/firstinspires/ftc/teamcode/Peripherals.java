package org.firstinspires.ftc.teamcode;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystems;

public class Peripherals extends Subsystems {
    public static String name = "Peripherals";
    public static AHRS navX;
    public static IMU imu;


    public static void initialize(HardwareMap hardwareMap){
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
        navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), AHRS.DeviceDataType.kProcessedData);
    }
    public static double getimuYaw(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
    public static void zeroIMU(){
        imu.resetYaw();
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
    public static boolean getMagCal(){return navX.isMagnetometerCalibrated();}

}