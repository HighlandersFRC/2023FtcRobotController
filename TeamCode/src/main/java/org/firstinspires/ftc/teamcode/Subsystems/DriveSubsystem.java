package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Tools.Odometry;
import org.firstinspires.ftc.teamcode.Tools.PID;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Vector;
import org.json.JSONArray;
import org.json.JSONException;

public class DriveSubsystem {

    public static DcMotor leftBack, leftFront, rightBack, rightFront;
    private PID turnPID = new PID(1, 0, 0);
    private PID drivePIDL = new PID(1, 0, 0);
    private PID drivePIDR = new PID(1, 0, 0);
    public static PID xPID = new PID(1, 0, 0);
    public static PID yPID = new PID(1, 0, 0);
    public static PID thetaPID = new PID(1, 0, 0);

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

        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();
    }

    public static void MecanumDrive(double x, double y, double rotation) {
        double heading = Peripherals.getYawDegrees();
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

    public static void driveByVectors(double x, double y, double rotation) {
        double heading = Peripherals.getYawDegrees();
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

        drive(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

        System.out.println("driveByVectors Powers: FL=" + frontLeftPower + ", FR=" + frontRightPower +
                ", BL=" + backLeftPower + ", BR=" + backRightPower);
    }

    public void moveToPosition(double targetX, double targetY, double targetAngle) {
        double currentX = Odometry.getX();
        double currentY = Odometry.getY();
        double currentAngle = Odometry.getTheta();

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

    private static void resetEncoders() {
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

    public void autoDrive(Vector velocityVector, double desiredThetaChange) {
        double x = velocityVector.getI();
        double y = velocityVector.getJ();
        double rotation = desiredThetaChange;

        MecanumDrive(x, y, rotation);
    }
}
