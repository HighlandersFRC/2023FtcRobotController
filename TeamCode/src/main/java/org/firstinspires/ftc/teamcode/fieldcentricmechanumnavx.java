package org.firstinspires.ftc.teamcode;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@TeleOp

public class fieldcentricmechanumnavx extends LinearOpMode {
private AHRS navX;
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

        waitForStart();
        DriveTrain.initializeMotors(hardwareMap);


        navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), AHRS.DeviceDataType.kProcessedData);
        // Adjust the orientation parameters to match your robot
       /* navX.Orientation parameters = new navX.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);*/
        // Find a motor in the hardware map named "Arm Motor"
        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double Left_Front_position = DriveTrain.getLeftFrontEncoder();
            double Left_Back_position = DriveTrain.getLeftBackEncoder();
            double Right_Front_position = DriveTrain.getRightFrontEncoder();
            double Right_Back_position = DriveTrain.getRightBackEncoder();
            double botHeading = -navX.getYaw();
            double pi = 3.1415926;
            double botHeadingRadian = -botHeading * pi/180;
            if (botHeadingRadian != 0) {

                double rotX = (x * Math.cos(botHeadingRadian) - y * Math.sin(botHeadingRadian));
                double rotY = (x * Math.sin(botHeadingRadian) + y * Math.cos(botHeadingRadian));

                x = x *1.1;

                double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);
                double frontLeftPower = (rotY + rotX + rx) / denominator;
                double backLeftPower = (rotY - rotX + rx) / denominator;
                double frontRightPower = (rotY - rotX - rx) / denominator;
                double backRightPower = (rotY + rotX - rx) / denominator;

                DriveTrain.Drive(hardwareMap, frontRightPower, frontLeftPower, backRightPower, backLeftPower);

                if (gamepad1.right_bumper) {
                    navX.zeroYaw();
                }



                telemetry.addData("y", y);
                telemetry.addData("x", x);
                telemetry.addData("rx", rx);
                telemetry.addData("rotY", rotY);
                telemetry.addData("rotX", rotX);
                telemetry.addData("navX", navX);

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

                telemetry.update();

            }
        }
    }
}
                  //W BOSS LEVEL ===============
///IT WORKS -Written by Advik Sanghi and Ethan LULULULULULU

//lf   -48437    -18617             2982   velocity 10 sec
//LB   33772         53502           1973 |
//RB   31422             51420      1999.8|
//RF   -45571        -15240        3033.1|