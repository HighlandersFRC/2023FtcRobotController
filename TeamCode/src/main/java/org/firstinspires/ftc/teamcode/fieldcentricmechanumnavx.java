package org.firstinspires.ftc.teamcode;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
@TeleOp
public class fieldcentricmechanumnavx extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        final double COUNTS_PER_MOTOR_REV = 1685;    // eg: TETRIX Motor Encoder
        final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
        final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
        final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                (WHEEL_DIAMETER_INCHES * 3.1415926);
        final double DRIVE_SPEED = 0.6;
        final double TURN_SPEED = 0.5;


        // Declare our motors
        DcMotor Left_Front = hardwareMap.dcMotor.get("Left_Front");
        DcMotor Left_Back = hardwareMap.dcMotor.get("Left_Back");
        DcMotor Right_Front = hardwareMap.dcMotor.get("Right_Front");
        DcMotor Right_Back = hardwareMap.dcMotor.get("Right_Back");

        waitForStart();

        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);

        AHRS navX = AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), AHRS.DeviceDataType.kProcessedData);
        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double Left_Front_position = Left_Front.getCurrentPosition();
            double Left_Back_position = Left_Back.getCurrentPosition();
            double Right_Front_position = Right_Front.getCurrentPosition();
            double Right_Back_position = Right_Back.getCurrentPosition();
            double botHeading = -navX.getYaw();
            double pi = 3.1415926;
            double botHeadingRadian = -botHeading * pi/180;

                // Rotate the movement direction counter to the bot's rotation
                double rotX = (x * Math.cos(botHeadingRadian) - y * Math.sin(botHeadingRadian));// Changed to positive due to things(change back when need)
                double rotY = (x * Math.sin(botHeadingRadian) + y * Math.cos(botHeadingRadian));//Changed to positive due to things(change back when need)

                x = x *1.1;  // Counteract imperfect strafing
                // Denominator is the largest motor power (absolute value) or 1rmn
                // This ensures all the powers maintain the same ratio,
                // but only if at least one is out of the range [-1, 1]
                double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);
                double frontLeftPower = (rotY + rotX + rx) / denominator;
                double backLeftPower = (rotY - rotX + rx) / denominator;
                double frontRightPower = (rotY - rotX - rx) / denominator;
                double backRightPower = (rotY + rotX - rx) / denominator;
                final double[] result = new double[1];
/*                class VelocityPID {
                    double leftBackResult(double encoderPos, double oldPos) {
                        result[0] = (encoderPos - oldPos);
                        return result[0];
                    }
                    double rightBackResult(double encoderPos, double oldPos) {
                        result[0] = (encoderPos - oldPos);
                        return result[0];
                    }
                    double leftFrontResult(double encoderPos, double oldPos) {
                        result[0] = (encoderPos - oldPos);
                        return result[0];
                    }
                    double rightFrontResult(double encoderPos, double oldPos) {
                        result[0] = (encoderPos - oldPos);
                        return result[0];
                    }

                }
*/
                if (gamepad1.right_bumper) {
                    navX.zeroYaw();
                }
                Left_Front.setPower(-frontLeftPower);
                Left_Back.setPower(-backLeftPower);
                Right_Front.setPower(-frontRightPower);
                Right_Back.setPower(-backRightPower);
                telemetry.addData("y", y);
                telemetry.addData("x", x);
                telemetry.addData("rx", rx);
                telemetry.addData("rotY", rotY);
                telemetry.addData("rotX", rotX);
          /*      telemetry.addData("parameters", parameters);*/
                telemetry.addData("navX", navX);
                telemetry.addData("YAW", navX.getYaw());
                telemetry.addData("pitch", navX.getPitch());
                telemetry.addData("roll", navX.getRoll());
                telemetry.addData("denominator", denominator);
                telemetry.addData("botHeading", botHeading);
                telemetry.addData("botHeadingRadian", botHeadingRadian);
                telemetry.addData("frontLeftPower", frontLeftPower);
                telemetry.addData("backLeftPower", backLeftPower);
                telemetry.addData("frontRightPower", frontRightPower);
                telemetry.addData("backRightPower", backRightPower);
                telemetry.addData("time", time);
                telemetry.addData("Left front position", Left_Front_position);
                telemetry.addData("Left back position", Left_Back_position);
                telemetry.addData("Right front position", Right_Front_position);
                telemetry.addData("Right front position", Right_Back_position);
                telemetry.addData("result", result[0]);
                telemetry.addData("mag", navX.getRawMagZ());
                telemetry.update();


        }
    }
}
                  //W BOSS LEVEL ===============
///IT WORKS -Written by Advik Sanghi and Ethan LULULULULULU

//lf   -48437    -18617             2982   velocity 10 sec
//LB   33772         53502           1973 |
//RB   31422             51420      1999.8|
//RF   -45571        -15240        3033.1|