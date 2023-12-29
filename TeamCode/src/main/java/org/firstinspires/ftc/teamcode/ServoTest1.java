package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
@TeleOp

public class ServoTest1 extends LinearOpMode {

    private Object robot;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor Left_Front = null;
        DcMotor Left_Back = null;
        DcMotor Right_Front = null;
        DcMotor Right_Back = null;
        Servo arm = null;
        //Servo claw = null;

        final double ARM_HOME = 0.0;
        final double ARM_MIN_RANGE = 0.2;
        final double ARM_MAX_RANGE = 0.7;


    }

    public void init(HardwareMap ahwmap) {
        final double ARM_HOME = 0.0;
        final double ARM_MIN_RANGE = 0.2;
        final double ARM_MAX_RANGE = 0.7;

        DcMotor Left_Front = hardwareMap.dcMotor.get("Left_Front");
        DcMotor Left_Back = hardwareMap.dcMotor.get("Left_Back");
        DcMotor Right_Front = hardwareMap.dcMotor.get("Right_Front");
        DcMotor Right_Back = hardwareMap.dcMotor.get("Right_Back");
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);

        Servo arm = ahwmap.servo.get("arm");
        arm.setPosition(ARM_HOME);
        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;
            if (gamepad1.options) {
                imu.resetYaw();
            }


            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            double botHeadingRadian = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double rotX = x * Math.cos(-botHeadingRadian) - y * Math.sin(-botHeadingRadian);
            double rotY = x * Math.sin(-botHeadingRadian) + y * Math.cos(-botHeadingRadian);

            rotX = rotX * 1.1;  // Counteract imperfect strafing
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            Left_Front.setPower(frontLeftPower);
            Left_Back.setPower(backLeftPower);
            Right_Front.setPower(frontRightPower);
            Right_Back.setPower(backRightPower);
            telemetry.addData("y", y);
            telemetry.addData("x", x);
            telemetry.addData("rx", rx);
            telemetry.addData("rotY", rotY);
            telemetry.addData("rotX", rotX);
            telemetry.addData("parameters", parameters);
            telemetry.addData("IMU", imu);
            telemetry.addData("denominator", denominator);
            telemetry.addData("botHeading", botHeading);
            telemetry.addData("botHeadingRadian", botHeadingRadian);
            telemetry.addData("frontLeftPower", frontLeftPower);
            telemetry.addData("backLeftPower", backLeftPower);
            telemetry.addData("frontRightPower", frontRightPower);
            telemetry.addData("backRightPower", backRightPower);
            telemetry.addData("time", time);
            telemetry.update();


        }
    }
}