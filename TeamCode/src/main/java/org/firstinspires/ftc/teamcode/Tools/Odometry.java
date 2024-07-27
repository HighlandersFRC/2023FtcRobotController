package org.firstinspires.ftc.teamcode.Tools;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Odometry {
    public static DcMotor leftEncoderMotor, rightEncoderMotor, centerEncoderMotor;

    public static final double TICKS_PER_REV = 2000;
    public static final double WHEEL_DIAMETER = 48 / 1000.0;
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;

    public static final double TRACK_WIDTH = 12.5 * 25.4 / 1000.0;
    public static final double CORRECTION_FACTOR = 0.97;

    public static double x = 0.0;
    public static double y = 0.0;
    public static double theta = 0.0;

    public static int lastLeftPos = 0;
    public static int lastRightPos = 0;
    public static int lastCenterPos = 0;

    public static void initialize(HardwareMap hardwareMap) {
        leftEncoderMotor = hardwareMap.get(DcMotor.class, "left_back");
        rightEncoderMotor = hardwareMap.get(DcMotor.class, "right_back");
        centerEncoderMotor = hardwareMap.get(DcMotor.class, "left_front");


        resetEncoders();
        x = 0.0;
        y = 0.0;
        theta = 0.0;
    }

    public static void resetEncoders() {
        lastLeftPos = leftEncoderMotor.getCurrentPosition();
        lastRightPos = rightEncoderMotor.getCurrentPosition();
        lastCenterPos = centerEncoderMotor.getCurrentPosition();
    }

    public static void setCurrentPosition(double x, double y, double theta) {
        Odometry.x = x;
        Odometry.y = y;
        Odometry.theta = theta;
    }

    public static void update() {
        int currentLeftPos = leftEncoderMotor.getCurrentPosition();
        int currentRightPos = rightEncoderMotor.getCurrentPosition();
        int currentCenterPos = centerEncoderMotor.getCurrentPosition();

        int deltaLeft = currentLeftPos - lastLeftPos;
        int deltaRight = currentRightPos - lastRightPos;
        int deltaCenter = currentCenterPos - lastCenterPos;

        lastLeftPos = currentLeftPos;
        lastRightPos = currentRightPos;
        lastCenterPos = currentCenterPos;

        double distanceLeft = (deltaLeft / (double) TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;
        double distanceRight = (deltaRight / (double) TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;
        double distanceCenter = (deltaCenter / (double) TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;

        double deltaTheta = (distanceRight - distanceLeft) / TRACK_WIDTH;

        double deltaX = distanceCenter * Math.sin(Math.toRadians(theta)) - (distanceRight + distanceLeft) / 2 * Math.cos(Math.toRadians(theta));
        double deltaY = distanceCenter * Math.cos(Math.toRadians(theta)) + (distanceRight + distanceLeft) / 2 * Math.sin(Math.toRadians(theta));

        theta += Math.toDegrees(deltaTheta);
        theta = (theta + 180) % 360;
        if (theta < 0) {
            theta += 360;
        }
        theta -= 180;

        x += deltaX;
        y += deltaY;
    }

    public static double getX() {
        return y;
    }

    public static double getY() {
        return x;
    }

    public static double getTheta() {
        return theta;
    }
}
