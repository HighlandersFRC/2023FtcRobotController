/*
package org.firstinspires.ftc.teamcode.Tools;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Odometry {
    public static DcMotor leftEncoderMotor, rightEncoderMotor, centerEncoderMotor;
    // Placeholder for OTOS sensor hardware instance
    public static OTOSSensor otosSensor;

    public static final double TICKS_PER_REV = 2000;
    public static final double WHEEL_DIAMETER = 48 / 1000.0;
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;

    public static final double TRACK_WIDTH = 12.5 * 25.4 / 1000.0;
    public static final double CORRECTION_FACTOR = 0.97;

    public static double x = 0.0;
    public static double y = 0.0;
    public static double theta = 0.0;

    public static double otosX = 0.0;
    public static double otosY = 0.0;
    public static double otosTheta = 0.0;

    public static int lastLeftPos = 0;
    public static int lastRightPos = 0;
    public static int lastCenterPos = 0;

    public Odometry(HardwareMap hardwareMap) {
        initialize(hardwareMap);
    }

    public Odometry() {

    }

    public static void initialize(HardwareMap hardwareMap) {
        leftEncoderMotor = hardwareMap.get(DcMotor.class, "left_back");
        rightEncoderMotor = hardwareMap.get(DcMotor.class, "right_back");
        centerEncoderMotor = hardwareMap.get(DcMotor.class, "left_front");
        // Initialize OTOS sensor
        otosSensor = hardwareMap.get(OTOSSensor.class, "otos_sensor");

        resetEncoders();
        x = 0.0;
        y = 0.0;
        theta = 0.0;
    }

    public static void resetEncoders() {
        if (leftEncoderMotor != null && rightEncoderMotor != null && centerEncoderMotor != null) {
            lastLeftPos = leftEncoderMotor.getCurrentPosition();
            lastRightPos = rightEncoderMotor.getCurrentPosition();
            lastCenterPos = centerEncoderMotor.getCurrentPosition();
        }
        // Reset OTOS sensor if needed
    }

    public static void setCurrentPosition(double x, double y, double theta) {
        Odometry.x = x;
        Odometry.y = y;
        Odometry.theta = theta;
    }

    public static void setCurrentPositionOTOS(double x, double y, double theta) {
        Odometry.otosX = x;
        Odometry.otosY = y;
        Odometry.otosTheta = theta;
    }

    public static void update() {
        if (leftEncoderMotor != null && rightEncoderMotor != null && centerEncoderMotor != null) {
            int currentLeftPos = leftEncoderMotor.getCurrentPosition();
            int currentRightPos = rightEncoderMotor.getCurrentPosition();
            int currentCenterPos = centerEncoderMotor.getCurrentPosition();

            int deltaLeft = currentLeftPos - lastLeftPos;
            int deltaRight = currentRightPos - lastRightPos;
            int deltaCenter = currentCenterPos - lastCenterPos;

            lastLeftPos = currentLeftPos;
            lastRightPos = currentRightPos;
            lastCenterPos = currentCenterPos;

            double distanceLeft = (deltaLeft / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;
            double distanceRight = (deltaRight / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;
            double distanceCenter = (deltaCenter / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;

            double deltaTheta = (distanceRight - distanceLeft) / TRACK_WIDTH;

            double deltaX = (distanceCenter * Math.sin(Math.toRadians(theta))) + ((distanceRight + distanceLeft) / 2 * Math.cos(Math.toRadians(theta)));
            double deltaY = (distanceCenter * Math.cos(Math.toRadians(theta))) - ((distanceRight + distanceLeft) / 2 * Math.sin(Math.toRadians(theta)));

            theta += Math.toDegrees(deltaTheta);
            theta = (theta + 360) % 360; // Normalize theta to [0, 360)
            theta -= 180; // Normalize to [-180, 180)

            x += deltaX;
            y += deltaY;
        }
        // Update OTOS sensor values
        updateOTOS();
    }

    public static void updateOTOS() {
        if (otosSensor != null) {
            // Assuming the OTOS sensor provides x, y, and theta directly
            otosX = otosSensor.getX();
            otosY = otosSensor.getY();
            otosTheta = otosSensor.getTheta();
        }
    }

    public static double getX() {
        return x;
    }

    public static double getY() {
        return y;
    }

    public static double getTheta() {
        return theta;
    }

    public static double getOTOSX() {
        return otosX;
    }

    public static double getOTOSY() {
        return otosY;
    }

    public static double getOTOSTheta() {
        return otosTheta;
    }
}
*/
