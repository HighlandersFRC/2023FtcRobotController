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

public class DriveSubsystem {

    public static DcMotor leftBack, leftFront, rightBack, rightFront, leftEncoderMotor, rightEncoderMotor, centerEncoderMotor;
    private PID turnPID = new PID(1, 0, 0);
    private PID drivePIDL = new PID(1, 0, 0);
    private PID drivePIDR = new PID(1, 0, 0);
    public static PID xPID = new PID(1, 0, 0);
    public static PID yPID = new PID(1, 0, 0);
    public static PID thetaPID = new PID(1, 0, 0);

    public static final double TICKS_PER_REV = 2000;
    public static final double WHEEL_DIAMETER = 48 / 1000.0;
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;

    public static final double TRACK_WIDTH = 12.5 * 25.4 / 1000.0;
    public static final double CORRECTION_FACTOR = 1;

    public static double x = 0.0;
    public static double y = 0.0;
    public static double theta = 0.0;

    public static int lastLeftPos = 0;
    public static int lastRightPos = 0;
    public static int lastCenterPos = 0;

    private static final double KP = 0.3;
    private static final double KA = 0.1;

    public DriveSubsystem(HardwareMap hardwareMap) {
        initialize(hardwareMap);
    }

    public static void initialize(HardwareMap hardwareMap) {
        leftBack = hardwareMap.get(DcMotor.class, "left_back");
        rightBack = hardwareMap.get(DcMotor.class, "right_back");
        leftFront = hardwareMap.get(DcMotor.class, "left_front");
        rightFront = hardwareMap.get(DcMotor.class, "right_front");


        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        x = 0.0;
        y = 0.0;
        theta = 0.0;

        resetEncoders();
    }

    public static int getLeftEncoder(){
        return -leftBack.getCurrentPosition();
    }
    public static int getRightEncoder(){
        return -rightBack.getCurrentPosition();
    }
    public static int getCenterEncoder(){
        return rightFront.getCurrentPosition();
    }

    public static void MecanumDrive(double x, double y, double rotation) {
        double heading = 0;
        double cosA = Math.cos(Math.toRadians(heading));
        double sinA = Math.sin(Math.toRadians(heading));
        double xAdjusted = x * cosA - y * sinA;
        double yAdjusted = x * sinA + y * cosA;

        double frontLeftPower = yAdjusted + xAdjusted + rotation;
        double frontRightPower = yAdjusted - xAdjusted - rotation;
        double backLeftPower = yAdjusted - xAdjusted + rotation;
        double backRightPower = yAdjusted + xAdjusted - rotation;

        double maxPower = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));
        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backLeftPower /= maxPower;
            backRightPower /= maxPower;
        }

        leftFront.setPower(frontLeftPower);
        rightFront.setPower(frontRightPower);
        leftBack.setPower(backLeftPower);
        rightBack.setPower(backRightPower);

        System.out.println("MecanumDrive Powers: FL=" + frontLeftPower + ", FR=" + frontRightPower +
                ", BL=" + backLeftPower + ", BR=" + backRightPower);
    }

    public static void drive(double leftFrontPower, double rightFrontPower, double leftBackPower, double rightBackPower) {
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftBack.setPower(leftBackPower);
        rightBack.setPower(rightBackPower);

        System.out.println("Drive Powers: FL=" + leftFrontPower + ", FR=" + rightFrontPower +
                ", BL=" + leftBackPower + ", BR=" + rightBackPower);
    }

    public void driveByVectors(double x, double y, double rotation) {
        double heading = DriveSubsystem.getOdometryTheta();
        double cosA = Math.cos(Math.toRadians(heading));
        double sinA = Math.sin(Math.toRadians(heading));

        // Adjust x and y for the current heading
        double xAdjusted = x * cosA - y * sinA;
        double yAdjusted = x * sinA + y * cosA;

        // Calculate power for each motor
        double frontLeftPower = yAdjusted + xAdjusted + rotation;
        double frontRightPower = yAdjusted - xAdjusted - rotation;
        double backLeftPower = yAdjusted - xAdjusted + rotation;
        double backRightPower = yAdjusted + xAdjusted - rotation;

        // Normalize power values to be within [-1, 1]
        double maxPower = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));
        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backLeftPower /= maxPower;
            backRightPower /= maxPower;
        }

        // Set motor powers
        leftFront.setPower(frontLeftPower);
        rightFront.setPower(frontRightPower);
        leftBack.setPower(backLeftPower);
        rightBack.setPower(backRightPower);

        // Log motor powers for debugging
        RobotLog.d("driveByVectors Powers: FL=" + frontLeftPower + ", FR=" + frontRightPower +
                ", BL=" + backLeftPower + ", BR=" + backRightPower);
    }


    public void moveToPosition(double targetX, double targetY, double targetAngle) {
        double currentX = getOdometryX();
        double currentY = getOdometryY();
        double currentAngle = getOdometryTheta();

        double errorX = targetX - currentX;
        double errorY = targetY - currentY;
        double errorAngle = targetAngle - currentAngle;

        errorAngle = (errorAngle + 180) % 360;
        if (errorAngle < 0) {
            errorAngle += 360;
        }
        errorAngle -= 180;

        double powerX = KP * errorX;
        double powerY = KP * errorY;
        double powerAngle = KA * errorAngle;

        driveByVectors(powerX, powerY, powerAngle);

        System.out.println("moveToPosition Powers: FL=" + leftFront.getPower() + ", FR=" + rightFront.getPower() +
                ", BL=" + leftBack.getPower() + ", BR=" + rightBack.getPower());
    }

    public void stop() {
        drive(0, 0, 0, 0);
        System.out.println("Stopping Motors: FL=0, FR=0, BL=0, BR=0");
    }

    public static void resetEncoders() {
        leftFront.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightFront.setMode(DcMotor.RunMode.RESET_ENCODERS);
        leftBack.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightBack.setMode(DcMotor.RunMode.RESET_ENCODERS);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
    }
    public static double[] pidController(double currentX, double currentY, double currentTheta, double time, JSONArray pathPoints) throws JSONException {
        if(time < pathPoints.getJSONArray(pathPoints.length() - 1).getDouble(0)) {
            JSONArray currentPoint = pathPoints.getJSONArray(0);
            JSONArray targetPoint = pathPoints.getJSONArray(0);
            for(int i = 0; i < pathPoints.length(); i ++) {
                int lookAheadDistance = 3;
                if(i == pathPoints.length() - lookAheadDistance) {
                    currentPoint = pathPoints.getJSONArray(i + 1);
                    targetPoint = pathPoints.getJSONArray((i + (lookAheadDistance - 1)));
                    break;
                }

                currentPoint = pathPoints.getJSONArray(i + 1);
                JSONArray previousPoint = pathPoints.getJSONArray(i);

                double currentPointTime = currentPoint.getDouble(0);
                double previousPointTime = previousPoint.getDouble(0);

                if(time >= previousPointTime && time < currentPointTime){
                    targetPoint = pathPoints.getJSONArray(i + (lookAheadDistance - 1));
                    break;
                }
            }

            double targetTime = targetPoint.getDouble(0);
            double targetX = targetPoint.getDouble(1);
            double targetY = targetPoint.getDouble(2);
            double targetTheta = targetPoint.getDouble(3);

            if (targetTheta - currentTheta > Math.PI){
                targetTheta -= 2 * Math.PI;
            } else if (targetTheta - currentTheta < -Math.PI){
                targetTheta += 2 * Math.PI;
            }

            double currentPointTime = currentPoint.getDouble(0);
            double currentPointX = currentPoint.getDouble(1);
            double currentPointY = currentPoint.getDouble(2);
            double currentPointTheta = currentPoint.getDouble(3);


            double feedForwardX = (targetX - currentPointX)/(targetTime - currentPointTime);
            double feedForwardY = (targetY - currentPointY)/(targetTime - currentPointTime);
            // double feedForwardTheta = -(targetTheta - currentPointTheta)/(targetTime - currentPointTime);
            double feedForwardTheta = 0;

            xPID.setSetPoint(targetX);
            yPID.setSetPoint(targetY);
            thetaPID.setSetPoint(targetTheta);

            xPID.updatePID(currentX);
            yPID.updatePID(currentY);
            thetaPID.updatePID(currentTheta);

            double xVelNoFF = xPID.getResult();
            double yVelNoFF = yPID.getResult();
            double thetaVelNoFF = -thetaPID.getResult();

            double xVel = feedForwardX + xVelNoFF;
            double yVel = feedForwardY + yVelNoFF;
            double thetaVel = feedForwardTheta + thetaVelNoFF;

            double[] velocityArray = new double[3];

            velocityArray[0] = xVel;
            velocityArray[1] = -yVel;
            velocityArray[2] = thetaVel;

            // System.out.println("Targ - X: " + targetX + " Y: " + targetY + " Theta: " + targetTheta);
            // System.out.println("PID side: " + this.fieldSide);

            return velocityArray;
        }
        else {
            double[] velocityArray = new double[3];

            velocityArray[0] = 0;
            velocityArray[1] = 0;
            velocityArray[2] = 0;

            return velocityArray;
        }
    }

    public static void autoDrive(Vector vector, double turnRadiansPerSec) {
        // Update odometry to get the latest robot pose
        update();

        // Get the current heading from the IMU or similar sensor, converting to radians
        double heading = Math.toRadians(Peripherals.getYawDegrees());

        // Adjust vector based on the current heading
        double cosHeading = Math.cos(heading);
        double sinHeading = Math.sin(heading);
        double xAdjusted = vector.getI() * cosHeading - vector.getJ() * sinHeading;
        double yAdjusted = vector.getI() * sinHeading + vector.getJ() * cosHeading;

        // Calculate the power for each wheel
        double frontLeftPower = yAdjusted + xAdjusted + turnRadiansPerSec;
        double frontRightPower = yAdjusted - xAdjusted - turnRadiansPerSec;
        double backLeftPower = yAdjusted - xAdjusted + turnRadiansPerSec;
        double backRightPower = yAdjusted + xAdjusted - turnRadiansPerSec;

        // Normalize the powers if necessary
        double maxPower = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));
        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backLeftPower /= maxPower;
            backRightPower /= maxPower;
        }


        drive(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

        // Optional: Print motor powers for debugging
        RobotLog.d("autoDrive Powers: FL=" + frontLeftPower + ", FR=" + frontRightPower +
                ", BL=" + backLeftPower + ", BR=" + backRightPower);
    }

    public Number[] purePursuitController(double currentX, double currentY, double currentTheta, int currentIndex,
                                          JSONArray pathPoints) throws JSONException {
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
        double feedForwardTheta = -targetPoint.getDouble("angular_velocity")/3;

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
    public static void setCurrentPosition(double x, double y, double theta) {
        x = x;
        y = y;
        theta = theta;
    }

    public static void update() {
        int currentLeftPos = getLeftEncoder();
        int currentRightPos = getRightEncoder();
        int currentCenterPos = getCenterEncoder();

        // Handle encoder overflow
        if (Math.abs(currentLeftPos - lastLeftPos) > TICKS_PER_REV / 2) {
            if (currentLeftPos > lastLeftPos) {
                lastLeftPos += TICKS_PER_REV;
            } else {
                lastLeftPos -= TICKS_PER_REV;
            }
        }
        if (Math.abs(currentRightPos - lastRightPos) > TICKS_PER_REV / 2) {
            if (currentRightPos > lastRightPos) {
                lastRightPos += TICKS_PER_REV;
            } else {
                lastRightPos -= TICKS_PER_REV;
            }
        }
        if (Math.abs(currentCenterPos - lastCenterPos) > TICKS_PER_REV / 2) {
            if (currentCenterPos > lastCenterPos) {
                lastCenterPos += TICKS_PER_REV;
            } else {
                lastCenterPos -= TICKS_PER_REV;
            }
        }

        // Calculate encoder deltas
        int deltaLeft = currentLeftPos - lastLeftPos;
        int deltaRight = currentRightPos - lastRightPos;
        int deltaCenter = currentCenterPos - lastCenterPos;

        // Update last encoder positions
        lastLeftPos = currentLeftPos;
        lastRightPos = currentRightPos;
        lastCenterPos = currentCenterPos;

        // Calculate distances
        double distanceLeft = (deltaLeft / (double) TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;
        double distanceRight = (deltaRight / (double) TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;
        double distanceCenter = (deltaCenter / (double) TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * CORRECTION_FACTOR;

        // Sanity check for distance
        if (Math.abs(distanceLeft) > WHEEL_CIRCUMFERENCE || Math.abs(distanceRight) > WHEEL_CIRCUMFERENCE || Math.abs(distanceCenter) > WHEEL_CIRCUMFERENCE) {
            RobotLog.e("Invalid encoder readings: L=" + distanceLeft + ", R=" + distanceRight + ", C=" + distanceCenter);
            return;
        }

        // Calculate the change in heading
        double deltaTheta = (distanceRight - distanceLeft) / TRACK_WIDTH;

        // Update heading
        theta += Math.toDegrees(deltaTheta);
        theta = (theta + 360) % 360;

        // Calculate the average distance traveled forward
        double averageDistance = (distanceLeft + distanceRight) / 2.0;

        // Calculate change in position
        double deltaX = averageDistance * Math.cos(Math.toRadians(theta)) + distanceCenter * Math.sin(Math.toRadians(theta));
        double deltaY = averageDistance * Math.sin(Math.toRadians(theta)) - distanceCenter * Math.cos(Math.toRadians(theta));

        // Update position
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

    public static void setCurrentPositionAndResetEncoders(double fieldX, double fieldY, double theta) {
    }
}

