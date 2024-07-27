package org.firstinspires.ftc.teamcode.Tools;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Odometry {
    public static DcMotor leftEncoderMotor, rightEncoderMotor, centerEncoderMotor;

    public static final double TICKS_PER_REV = 2000;
    public static final double WHEEL_DIAMETER = 48 / 1000.0; // in meters
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;

    public static final double TRACK_WIDTH = 12 * 25.4 / 1000.0; // 12 inches to meters
    public static final double CENTER_WHEEL_OFFSET = 0;

    public static double x = 0.0;
    public static double y = 0.0;
    public static double theta = 0.0;

    public static int lastLeftPos = 0;
    public static int lastRightPos = 0;
    public static int lastCenterPos = 0;

    public static void initialize(HardwareMap hardwareMap) {
        leftEncoderMotor = hardwareMap.get(DcMotor.class, "right_front");//0
        rightEncoderMotor = hardwareMap.get(DcMotor.class, "left_front");//1
        centerEncoderMotor = hardwareMap.get(DcMotor.class, "right_back");//2

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

        double distanceLeft = (deltaLeft / (double) TICKS_PER_REV) * WHEEL_CIRCUMFERENCE;
        double distanceRight = (deltaRight / (double) TICKS_PER_REV) * WHEEL_CIRCUMFERENCE;
        double distanceCenter = (deltaCenter / (double) TICKS_PER_REV) * WHEEL_CIRCUMFERENCE;

        double deltaTheta = (distanceRight - distanceLeft) / TRACK_WIDTH;

        double deltaX = distanceCenter * Math.cos(Math.toRadians(theta)) - (distanceRight + distanceLeft) / 2 * Math.sin(Math.toRadians(theta));
        double deltaY = distanceCenter * Math.sin(Math.toRadians(theta)) + (distanceRight + distanceLeft) / 2 * Math.cos(Math.toRadians(theta));

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
        return x;
    }

    public static double getY() {
        return y;
    }

    public static double getTheta() {
        return theta;
    }
}
