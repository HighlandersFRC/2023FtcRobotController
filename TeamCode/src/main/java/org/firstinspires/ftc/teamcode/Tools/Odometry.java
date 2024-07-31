package org.firstinspires.ftc.teamcode.Tools;

/*
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

    public Odometry(HardwareMap hardwareMap) {
    }

    public static void initialize(HardwareMap hardwareMap) {
        leftEncoderMotor = hardwareMap.get(DcMotor.class, "left_back");
        rightEncoderMotor = hardwareMap.get(DcMotor.class, "left_front");  // Center encoder
        centerEncoderMotor = hardwareMap.get(DcMotor.class, "right_back");  // Right encoder

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

    public static double getOdometryX() {
        return y;
    }

    public static double getOdometryY() {
        return x;
    }

    public static double getOdometryTheta() {
        return theta;
    }
}*//*
*/
/*
*//*

*/
/*
*//*
*/
/*

*//*

*/
/*
*//*
*/
/*
*//*

*/
/*

*//*
*/
/*

*//*

*/
/*

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

        double distanceLeft = (deltaLeft / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;
        double distanceRight = (deltaRight / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;
        double distanceCenter = (deltaCenter / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;

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

    public static double getOdometryX() {
        return x;  // Corrected to return x
    }

    public static double getOdometryY() {
        return y;  // Corrected to return y
    }

    public static double getOdometryTheta() {
        return theta;
    }
}
*//*
*/
/*
*//*

*/
/*
*//*
*/
/*


*//*

*/
/*
package org.firstinspires.ftc.teamcode.Tools;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Odometry {
    public static DcMotor leftEncoderMotor, rightEncoderMotor, centerEncoderMotor;

    public static final double TICKS_PER_REV = 2000;
    public static final double WHEEL_DIAMETER = 48 / 1000.0; // Diameter in meters
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER; // Circumference in meters

    public static final double TRACK_WIDTH = 0.3175; // Width in meters
    public static final double CORRECTION_FACTOR = 1; // Correction factor

    public static double x = 0.0; // x position in meters
    public static double y = 0.0; // y position in meters
    public static double theta = 0.0; // Orientation in degrees

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

    public static double getOdometryX() {
        return x;  // Corrected to return x
    }

    public static double getOdometryY() {
        return y;  // Corrected to return y
    }

    public static double getOdometryTheta() {
        return theta;
    }
}
*//*
*/
/*

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
        leftEncoderMotor = hardwareMap.get(DcMotor.class, "right_front");
        rightEncoderMotor = hardwareMap.get(DcMotor.class, "left_front");
        centerEncoderMotor = hardwareMap.get(DcMotor.class, "right_back" );

        resetEncoders();
        x = 0.0;
        y = 0.0;
        theta = 0.0;
    }

    public static void resetEncoders() {
        lastLeftPos = 0;
        lastRightPos = 0;
        lastCenterPos = 0;
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

        double deltaX = (distanceLeft + distanceRight) / 2 * Math.cos(Math.toRadians(theta)) + distanceCenter * Math.sin(Math.toRadians(theta));
        double deltaY = (distanceLeft + distanceRight) / 2 * Math.sin(Math.toRadians(theta)) + distanceCenter * Math.cos(Math.toRadians(theta));

        theta += Math.toDegrees(deltaTheta);
        theta = (theta + 180) % 360;
        if (theta < 0) {
            theta += 360;
        }
        theta -= 180;

        x += deltaX;
        y += deltaY;
    }

    public static double getOdometryX() {
        return x;
    }

    public static double getOdometryY() {
        return y;
    }

    public static double getOdometryTheta() {
        return theta;
    }
}
*/
/*import com.qualcomm.robotcore.hardware.DcMotor;
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

    public Odometry(HardwareMap hardwareMap) {
    }

    public static void initialize(HardwareMap hardwareMap) {
        leftEncoderMotor = hardwareMap.get(DcMotor.class, "right_front");
        rightEncoderMotor = hardwareMap.get(DcMotor.class, "left_front");
        centerEncoderMotor = hardwareMap.get(DcMotor.class, "right_back" );

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

    public static double getOdometryX() {
        return -x;
    }

    public static double getOdometryY() {
        return y;
    }

    public static double getOdometryTheta() {
        return theta;
    }
}*/
/*
public class Odometry {
    private double x = 0;
    private double y = 0;
    private double theta = 0;

    // Method to update pose based on AprilTag data
    public void updatePose(double newX, double newY, double newTheta) {
        this.x = newX;
        this.y = newY;
        this.theta = newTheta;
    }

    // Method to reset odometry
    public void resetPose() {
        this.x = 0;
        this.y = 0;
        this.theta = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getTheta() {
        return theta;
    }
}
*/


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Odometry {
    public static DcMotor leftEncoderMotor, rightEncoderMotor, centerEncoderMotor;

    // Constants
    private static final double TICKS_PER_REV = 8192; // Encoder ticks per revolution (may vary by motor)
    private static final double WHEEL_DIAMETER = 96 / 1000.0; // Wheel diameter in meters
    private static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER; // Wheel circumference in meters
    private static final double TRACK_WIDTH = 12.5 * 25.4 / 1000.0; // Track width in meters
    private static final double CORRECTION_FACTOR = 1.0; // Correction factor for encoder measurement

    // Robot state
    private static double x = 0.0; // X position in meters
    private static double y = 0.0; // Y position in meters
    private static double theta = 0.0; // Orientation in radians

    private static int lastLeftPos = 0;
    private static int lastRightPos = 0;
    private static int lastCenterPos = 0;

    public Odometry(HardwareMap hardwareMap) {
        initialize(hardwareMap);
    }

    public static void initialize(HardwareMap hardwareMap) {
        leftEncoderMotor = hardwareMap.get(DcMotor.class, "right_front");
        rightEncoderMotor = hardwareMap.get(DcMotor.class, "left_front");
        centerEncoderMotor = hardwareMap.get(DcMotor.class, "right_back");

        // Set encoder modes
        leftEncoderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        centerEncoderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftEncoderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightEncoderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        centerEncoderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        resetEncoders();
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

    public static void setCurrentPositionAndResetEncoders(double x, double y, double theta) {
        Odometry.x = x;
        Odometry.y = y;
        Odometry.theta = theta;
        resetEncoders();
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

        double distanceLeft = (deltaLeft / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;
        double distanceRight = (deltaRight / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;
        double distanceCenter = (deltaCenter / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;

        double deltaTheta = (distanceRight - distanceLeft) / TRACK_WIDTH;

        double deltaX = distanceCenter * Math.sin(theta) - (distanceRight + distanceLeft) / 2 * Math.cos(theta);
        double deltaY = distanceCenter * Math.cos(theta) + (distanceRight + distanceLeft) / 2 * Math.sin(theta);

        theta += deltaTheta;
        theta = (theta + 2 * Math.PI) % (2 * Math.PI); // Normalize theta to [0, 2π)

        x += deltaX;
        y += deltaY;
    }

    public static double getOdometryX() {
        return x;
    }

    public static double getOdometryY() {
        return y;
    }

    public static double getOdometryTheta() {
        return theta;
    }
}
