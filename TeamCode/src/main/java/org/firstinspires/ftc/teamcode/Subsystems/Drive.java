package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.PID;
import org.firstinspires.ftc.teamcode.Tools.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Drive extends Subsystem {
    public static DcMotorEx frontLeftMotor;
    public static DcMotorEx backLeftMotor;
    public static DcMotorEx frontRightMotor;
    public static DcMotorEx backRightMotor;

    public static final double TICKS_PER_REV = 2000;
    public static final double WHEEL_DIAMETER = 0.048; // in meters
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;

    public static final double TRACK_WIDTH = 11 * 25.4 / 1000.0; // in meters
    public static final double CORRECTION_FACTOR = 1;

    public static double x = 0.0;
    public static double y = 0.0;
    public static double theta = 0.0; // in degrees

    public static int lastLeftPos = 0;
    public static int lastRightPos = 0;
    public static int lastCenterPos = 0;

    public static PID xPID = new PID(1, 0, 0);
    public static PID yPID = new PID(1, 0, 0);
    public static PID thetaPID = new PID(1, 0, 0);

    public static final double FORWARD_OFFSET = 0.22225; // Distance from the center to the center odometry pod in meters

    private static long lastUpdateTime = 0;

    public static double totalXTraveled = 0.0;
    public static double totalYTraveled = 0.0;
    public static double totalThetaTraveled = 0.0;

    public static void initialize(HardwareMap hardwareMap) {
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "left_front");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "left_back");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "right_front");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "right_back");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoder();
        lastUpdateTime = System.currentTimeMillis();
    }

    public static void drive(double leftFrontPower, double rightFrontPower, double leftBackPower, double rightBackPower) {
        frontLeftMotor.setPower(leftFrontPower);
        frontRightMotor.setPower(rightFrontPower);
        backLeftMotor.setPower(leftBackPower);
        backRightMotor.setPower(rightBackPower);

        System.out.println("Drive Powers: FL=" + leftFrontPower + ", FR=" + rightFrontPower +
                ", BL=" + leftBackPower + ", BR=" + rightBackPower);
    }

    public Number[] purePursuitController(double currentX, double currentY, double currentTheta, int currentIndex, JSONArray pathPoints) throws JSONException {
        JSONObject targetPoint = pathPoints.getJSONObject(pathPoints.length() - 1);
        int targetIndex = pathPoints.length() - 1;
        for (int i = currentIndex; i < pathPoints.length(); i++) {
            JSONObject point = pathPoints.getJSONObject(i);
            double velocityMag = Math
                    .sqrt(Constants.AUTONOMOUS_LOOKAHEAD_LINEAR_RADIUS
                            * (Math.pow(point.getDouble("x_velocity"), 2) + Math.pow(point.getDouble("y_velocity"), 2))
                            + Constants.AUTONOMOUS_LOOKAHEAD_ANGULAR_RADIUS
                            * Math.pow(point.getDouble("angular_velocity"), 2));
            if (!insideRadius(currentX - point.getDouble("x"), currentY - point.getDouble("y"),
                    currentTheta - point.getDouble("angle"),
                    Constants.AUTONOMOUS_LOOKAHEAD_DISTANCE * velocityMag + 0.01)) {
                targetIndex = i;
                targetPoint = pathPoints.getJSONObject(i);
                break;
            }
        }
        double targetX = targetPoint.getDouble("x"), targetY = targetPoint.getDouble("y"),
                targetTheta = targetPoint.getDouble("angle");

        xPID.setSetPoint(targetX);
        yPID.setSetPoint(targetY);
        thetaPID.setSetPoint(targetTheta);

        xPID.updatePID(currentX);
        yPID.updatePID(currentY);
        thetaPID.updatePID(currentTheta);

        double xVelNoFF = xPID.getResult();
        double yVelNoFF = yPID.getResult();
        double thetaVelNoFF = -thetaPID.getResult();

        double feedForwardX = targetPoint.getDouble("x_velocity");
        double feedForwardY = targetPoint.getDouble("y_velocity");
        double feedForwardTheta = -targetPoint.getDouble("angular_velocity") / 3;

        Number[] velocityArray = new Number[]{
                feedForwardX + xVelNoFF,
                -(feedForwardY + yVelNoFF),
                feedForwardTheta + thetaVelNoFF,
                targetIndex,
        };

        return velocityArray;
    }

    private boolean insideRadius(double deltaX, double deltaY, double deltaTheta, double radius) {
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaTheta, 2)) < radius;
    }

    public static double getVelocityBackLeft() {
        return backLeftMotor.getVelocity();
    }

    public static double getVelocityBackRight() {
        return backRightMotor.getVelocity();
    }

    public static double getVelocityFrontLeft() {
        return frontLeftMotor.getVelocity();
    }

    public static double getVelocityFrontRight() {
        return frontRightMotor.getVelocity();
    }

    public static void update() {
        double imuTheta = Peripherals.getYawDegrees();

        int currentLeftPos = getLeftEncoder();
        int currentRightPos = getRightEncoder();
        int currentCenterPos = getCenterEncoder();

        int deltaLeft = currentLeftPos - lastLeftPos;
        int deltaRight = currentRightPos - lastRightPos;
        int deltaCenter = currentCenterPos - lastCenterPos;

        lastLeftPos = currentLeftPos;
        lastRightPos = currentRightPos;
        lastCenterPos = currentCenterPos;

        double distanceLeft = (deltaLeft / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE;
        double distanceRight = (deltaRight / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE;
        double distanceCenter = (deltaCenter / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE;

        theta = imuTheta;

        double avgForwardMovement = (distanceLeft + distanceRight) / 2.0;

        double deltaTheta = Math.toRadians(theta);

        double deltaX = avgForwardMovement * Math.cos(deltaTheta) + distanceCenter * Math.sin(deltaTheta);
        double deltaY = -avgForwardMovement * Math.sin(deltaTheta) + distanceCenter * Math.cos(deltaTheta);

        x += deltaX;
        y += deltaY;

        totalXTraveled += Math.abs(deltaX);
        totalYTraveled += Math.abs(deltaY);
        totalThetaTraveled += Math.abs((distanceRight - distanceLeft) / TRACK_WIDTH);
    }

    private static double normalizeAngle(double angle) {
        angle %= 360;
        if (angle > 180) {
            angle -= 360;
        } else if (angle < -180) {
            angle += 360;
        }
        return angle;
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

    public static double getTotalXTraveled() {
        return totalXTraveled;
    }

    public static double getTotalYTraveled() {
        return totalYTraveled;
    }

    public static double getTotalThetaTraveled() {
        return totalThetaTraveled;
    }

    public static void setCurrentPositionAndResetEncoders(double fieldX, double fieldY, double theta) {
        x = fieldX;
        y = fieldY;
        Drive.theta = theta;
        resetEncoder();
    }

    public static void resetEncoder() {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        x = 0;
        y = 0;
        theta = 0;

        totalXTraveled = 0;
        totalYTraveled = 0;
        totalThetaTraveled = 0;

        lastLeftPos = 0;
        lastRightPos = 0;
        lastCenterPos = 0;
    }

    public static int getLeftEncoder() {
        return -frontLeftMotor.getCurrentPosition();
    }

    public static int getRightEncoder() {
        return -backRightMotor.getCurrentPosition();
    }

    public static int getCenterEncoder() {
        return frontRightMotor.getCurrentPosition();
    }


}