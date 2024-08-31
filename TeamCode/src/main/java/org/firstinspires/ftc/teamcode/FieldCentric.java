package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.FieldOfMerit;
import org.firstinspires.ftc.teamcode.Tools.FinalPose;
import org.firstinspires.ftc.teamcode.Tools.Logger;
import org.firstinspires.ftc.teamcode.Tools.Robot;

@TeleOp
public class FieldCentric extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.initialize(hardwareMap);

        Logger logger = new Logger();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            FinalPose.poseUpdate();

            double y = -gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;

            if (gamepad1.right_bumper) {
                Peripherals.resetYaw();
                Drive.resetEncoder();
            }
            if (gamepad1.a) {
                Drive.moveToAprilTag(7);
            }

            double botHeading = -Peripherals.getYaw();
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            Drive.drive(frontLeftPower, frontRightPower, -backLeftPower, backRightPower);

            telemetry.addData("IMU Yaw", Peripherals.getYawDegrees());

            telemetry.addData("Odometry X", Drive.getOdometryX());
            telemetry.addData("Odometry Y", Drive.getOdometryY());
            telemetry.addData("Odometry Theta", Drive.getOdometryTheta());
            telemetry.addData("Total X", Drive.getTotalXTraveled());
            telemetry.addData("Total Y", Drive.getTotalYTraveled());
            telemetry.addData("Total Theta", Drive.totalThetaTraveled);
            telemetry.addData("Final X", FinalPose.x);
            telemetry.addData("Final Y", FinalPose.y);
            telemetry.addData("Final Theta", FinalPose.Yaw);
            telemetry.addData("Current Sensor", FieldOfMerit.currentState);
            telemetry.addData("Left Encoder", Drive.getLeftEncoder());
            telemetry.addData("Right Encoder", Drive.getRightEncoder());
            telemetry.addData("Center Encoder", Drive.getCenterEncoder());
            telemetry.update();

            logger.log("Odometry X", Drive.getOdometryX());
            logger.log("Odometry Y", Drive.getOdometryY());
            logger.log("Odometry Theta", Drive.getOdometryTheta());
        }
    }
}