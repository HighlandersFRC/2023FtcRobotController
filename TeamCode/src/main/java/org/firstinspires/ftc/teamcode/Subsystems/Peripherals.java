package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Peripherals extends Subsystem {

    private static DcMotor leftMotor;
    private static DcMotor rightMotor;
    private static IMU imu;

    public Peripherals(String name) {
        super();
    }

    public static void initialize(HardwareMap hardwareMap) {
        imu = hardwareMap.get(IMU.class, "imu"); // Ensure this matches your configuration
    }

    public static double getYawDegrees() {
        if (imu != null) {
            return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        } else {
            // Handle the case where imu is not initialized
            return Double.NaN; // or an appropriate default/error value
        }
    }

    public static double getYaw() {
        if (imu != null) {
            return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        } else {
            return Double.NaN;
        }
    }

    public static double getRoll() {
        if (imu != null) {
            return imu.getRobotYawPitchRollAngles().getRoll(AngleUnit.DEGREES);
        } else {
            return Double.NaN;
        }
    }

    public static double getPitch() {
        if (imu != null) {
            return imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES);
        } else {
            return Double.NaN;
        }
    }

    public static void resetYaw() {
        if (imu != null) {
            imu.resetYaw();
        }
    }
}
