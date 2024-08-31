package org.firstinspires.ftc.teamcode.Subsystems;

import  com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.FieldCentric;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.FieldOfMerit;
import org.firstinspires.ftc.teamcode.Tools.PID;

import org.firstinspires.ftc.teamcode.Tools.Vector;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Drive extends Subsystem {

    public static DcMotorEx frontLeftMotor;
    public static DcMotorEx backLeftMotor;
    public static DcMotorEx frontRightMotor;
    public static DcMotorEx backRightMotor;

    public static final double TICKS_PER_REV = 2000;
    public static final double WHEEL_DIAMETER = 0.048; // meters
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    public static final double CORRECTION_FACTOR = 1;

    public static double x = 0.0;
    public static double y = 0.0;
    public static double theta = 0.0;

    public static int lastLeftPos = 0;
    public static int lastRightPos = 0;
    public static int lastCenterPos = 0;

    public static PID xPID = new PID(1, 0, 0);
    public static PID yPID = new PID(1, 0, 0);
    public static PID thetaPID = new PID(1, 0, 0);

    public static final double FORWARD_OFFSET = 0.22225;

    private static long lastUpdateTime = 0;

    public static double totalXTraveled = 0.0;
    public static double totalYTraveled = 0.0;
    public static double totalThetaTraveled = 0.0;
    private static final double L = 0.2;
    private static final double W = 0.2;

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
    }

    public static void stop() {
    Drive.drive(0,0,0,0);

    backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public Number[] purePursuitController(double currentX, double currentY, double currentTheta, int currentIndex,
                                          JSONArray pathPoints) throws JSONException {
        JSONObject targetPoint = pathPoints.getJSONObject(pathPoints.length() - 1);
        int targetIndex = pathPoints.length() - 1;
        for (int i = currentIndex; i < pathPoints.length(); i++) {
            JSONObject point = pathPoints.getJSONObject(i);
            double velocityMag = Math
                    .sqrt((Math.pow(point.getDouble("x_velocity"), 2) + Math.pow(point.getDouble("y_velocity"), 2))
                            + Math.pow(point.getDouble("angular_velocity"), 2));
            double targetTheta = point.getDouble("angle");
            while (Math.abs(targetTheta - currentTheta) > Math.PI) {
                if (targetTheta - currentTheta > Math.PI) {
                    targetTheta -= 2 * Math.PI;
                } else if (targetTheta - currentTheta < -Math.PI) {
                    targetTheta += 2 * Math.PI;
                }
            }
            if (!insideRadius(currentX - point.getDouble("x") / Constants.AUTONOMOUS_LOOKAHEAD_LINEAR_RADIUS,
                    currentY - point.getDouble("y") / Constants.AUTONOMOUS_LOOKAHEAD_LINEAR_RADIUS,
                    (currentTheta - targetTheta) / Constants.AUTONOMOUS_LOOKAHEAD_ANGULAR_RADIUS,
                    Constants.AUTONOMOUS_LOOKAHEAD_DISTANCE /* * velocityMag */ + 0.01
            )) {
                targetIndex = i;
                targetPoint = pathPoints.getJSONObject(i);
                break;
            }
        }
        double targetX = targetPoint.getDouble("x"), targetY = targetPoint.getDouble("y"),
                targetTheta = targetPoint.getDouble("angle");
        while (Math.abs(targetTheta - currentTheta) > Math.PI) {
            if (targetTheta - currentTheta > Math.PI) {
                targetTheta -= 2 * Math.PI;
            } else if (targetTheta - currentTheta < -Math.PI) {
                targetTheta += 2 * Math.PI;
            }
        }
        xPID.setSetPoint(targetX);
        yPID.setSetPoint(targetY);
        thetaPID.setSetPoint(targetTheta);

        xPID.updatePID(currentX);
        yPID.updatePID(currentY);
        thetaPID.updatePID(currentTheta);

        double xVelNoFF = xPID.getResult();
        double yVelNoFF = yPID.getResult();
        double thetaVelNoFF = -thetaPID.getResult();

        double feedForwardX = targetPoint.getDouble("x_velocity")/2;
        double feedForwardY = targetPoint.getDouble("y_velocity")/2;
        double feedForwardTheta = -targetPoint.getDouble("angular_velocity")/2;

        Number[] velocityArray = new Number[] {
                feedForwardX + xVelNoFF,
                -(feedForwardY + yVelNoFF),
                feedForwardTheta + thetaVelNoFF,
                targetIndex,
        };

        double velocityMag = Math
                .sqrt(Math.pow(targetPoint.getDouble("x_velocity"), 2) + Math.pow(targetPoint.getDouble("y_velocity"), 2));

        return velocityArray;
    }


    private boolean insideRadius(double deltaX, double deltaY, double deltaTheta, double radius) {
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaTheta, 2)) < radius;
    }

    // Get velocity methods
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

    public static void setPosition(double fieldX, double fieldY, double fieldTheta) {
        // Update the robot's internal position and orientation
        x = fieldX;
        y = fieldY;
        theta = fieldTheta;

        // Optionally: Update encoders to reflect this change (if required)
        // If you want to adjust the encoder counts to match the new position,
        // you would have to calculate the corresponding encoder ticks here.
        lastLeftPos = getLeftEncoder();
        lastRightPos = getRightEncoder();
        lastCenterPos = getCenterEncoder();
    }

    public static void resetEncoder() {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Reset odometry values
        x = 0.0;
        y = 0.0;
        theta = 0.0;

        // Reset encoder position tracking
        lastLeftPos = 0;
        lastRightPos = 0;
        lastCenterPos = 0;
    }


    public static int getLeftEncoder() {
        return backRightMotor.getCurrentPosition();
    }

    public static int getRightEncoder() {
        return frontLeftMotor.getCurrentPosition();
    }

    public static void moveToAprilTag(int ID){
            double tagX = FieldOfMerit.x;
            double tagY = FieldOfMerit.y;
            double tagTheta = FieldOfMerit.tagyaw;
            PID turnPID = new PID(1, 0, 0);
            turnPID.setSetPoint(Peripherals.getYawDegrees() -Math.toDegrees(tagTheta));
            turnPID.updatePID(Peripherals.getYawDegrees());
            if (!(turnPID.getError() == 0)) {
                if (Math.abs(turnPID.getError()) < 2) {
                    double distance = Math.sqrt(x * x + y * y);
                    if ((distance < 0.1)){
                        Drive.drive(0.5,0.5,0.5,0.5);
                    }else {
                        Drive.stop();
                    }
                }else {
                    double power = turnPID.getResult();
                    Drive.drive(power, -power, power, -power);
                }

        }
    }
    public static int getCenterEncoder() {
        return frontRightMotor.getCurrentPosition();
    }
    public static void autoDrive(Vector vector, double omega) {
        double vx = vector.getJ();
        double vy = -vector.getI();
        double frontLeftPower = vx + vy + omega * (L + W);
        double frontRightPower = vx - vy - omega * (L + W);
        double backLeftPower = vx - vy + omega * (L + W);
        double backRightPower = vx + vy - omega * (L + W);

        // Normalize the powers to ensure they are within [-1, 1]
        double maxMagnitude = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));

        if (maxMagnitude > 1) {
            frontLeftPower /= maxMagnitude;
            frontRightPower /= maxMagnitude;
            backLeftPower /= maxMagnitude;
            backRightPower /= maxMagnitude;
        }

        // Set motor powers
        frontLeftMotor.setPower(frontLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backLeftMotor.setPower(backLeftPower);
        backRightMotor.setPower(backRightPower);
    }
}