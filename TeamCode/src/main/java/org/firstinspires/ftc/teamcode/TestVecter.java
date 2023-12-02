package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.internal.ftdi.eeprom.FT_EEPROM_232H;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Tools.Vector;

@TeleOp


public class TestVecter extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        PID1 PID = new PID1();
        // Declare our motors
        DcMotor Left_Front = hardwareMap.dcMotor.get("Left_Front");
        DcMotor Left_Back = hardwareMap.dcMotor.get("Left_Back");
        DcMotor Right_Front = hardwareMap.dcMotor.get("Right_Front");
        DcMotor Right_Back = hardwareMap.dcMotor.get("Right_Back");
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);


        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double ly = -gamepad1.left_stick_y;
            double lx = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double ry = gamepad1.right_stick_y;
            Vector r = new Vector(rx, ry);
            Vector l = new Vector(lx, ly);
            double botHeading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            double botHeadingRadian = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            Drive drive = new Drive(l, rx) {
                @Override
                public void execute() {

                }
            };
            drive.execute();

        }
    }
}