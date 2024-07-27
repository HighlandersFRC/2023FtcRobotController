package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Peripherals extends Subsystem {

    private static DcMotor leftMotor;
    private static DcMotor rightMotor;
    private double xPosition = 0, yPosition = 0, theta = 0;
    private int lastLeftPosition = 0, lastRightPosition = 0;
    private double wheelDiameter = 0.1; // Wheel diameter in meters
    private double wheelBase = 0.3; // Distance between the two wheels in meters
    private static IMU imu;

    public Peripherals(String name) {
        super();
    }


    public static void initialize(HardwareMap hardwareMap) {

        imu = hardwareMap.get(IMU.class, "imu");
    }


    public static double getYawDegrees(){
        return  imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public static double getYaw() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public static double getRoll() {
        return imu.getRobotYawPitchRollAngles().getRoll(AngleUnit.DEGREES);
    }

    public static double getPitch() {
        return imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES);
    }

    public static void resetYaw() {
        imu.resetYaw();
    }
}
