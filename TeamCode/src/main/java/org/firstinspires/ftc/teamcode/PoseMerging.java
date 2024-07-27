package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class PoseMerging {

    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private IMU imu;

    private double cameraX, cameraY, cameraTheta;
    private boolean cameraVisible;

    public PoseMerging(HardwareMap hardwareMap) {
        leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right_motor");
        imu = hardwareMap.get(IMU.class, "imu");
        cameraVisible = false;
    }

    public void updateCameraPose(double x, double y, double theta) {
        cameraX = x;
        cameraY = y;
        cameraTheta = theta;
        cameraVisible = true;
    }

    public void updatePose() {
        if (cameraVisible) {
            double odomX = getOdomX();
            double odomY = getOdomY();
            double odomTheta = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double mergedX = (odomX + cameraX) / 2;
            double mergedY = (odomY + cameraY) / 2;
            double mergedTheta = (odomTheta + cameraTheta) / 2;

            // Use mergedX, mergedY, and mergedTheta as needed
        }
    }

    private double getOdomX() {
        // Calculate odometry X based on encoder values
        return 0; // Placeholder
    }

    private double getOdomY() {
        // Calculate odometry Y based on encoder values
        return 0; // Placeholder
    }
}
