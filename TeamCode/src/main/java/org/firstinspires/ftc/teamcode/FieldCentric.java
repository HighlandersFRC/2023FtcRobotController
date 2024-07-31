/*
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Odometry;
import org.firstinspires.ftc.teamcode.Tools.PID;
import org.firstinspires.ftc.teamcode.Tools.Robot;

@TeleOp
public class FieldCentric extends LinearOpMode {
    PID ElevatorPIDL = new PID(0.0007, 0.0, 0.0007);
    PID ElevatorPIDR = new PID(0.0007, 0.0, 0.0007);
    PID ArmPID = new PID(0.0015, 0.0, 0.0018);

    @Override
    public void runOpMode() {
        Robot.initialize(hardwareMap);
        Odometry.initialize(hardwareMap);
        Peripherals.initialize(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                DriveSubsystem.drive(1, 0, 0, 0);
            }
            if (gamepad1.b) {
                DriveSubsystem.drive(0, 1, 0, 0);
            }
            if (gamepad1.y) {
                DriveSubsystem.drive(0, 0, 1, 0);
            }
            if (gamepad1.x) {
                DriveSubsystem.drive(0, 0, 0, 1);
            }
            if (gamepad1.dpad_down) {
                DriveSubsystem.drive(1, 1, 1, 1);
            }
            if (gamepad1.dpad_up) {
                Peripherals.resetYaw(); // Correct method call here
            }

            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            DriveSubsystem.drive(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            telemetry.addData("X", Odometry.getOdometryX());
            telemetry.addData("Y", Odometry.getOdometryY());
            telemetry.addData("Theta", Odometry.getOdometryTheta());
            telemetry.addData("IMU Yaw", Peripherals.getYawDegrees());
            telemetry.addData("y", y);
            telemetry.addData("rx", rx);
            telemetry.update();
            Odometry.update();
        }
    }
}
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Odometry;
/*import org.firstinspires.ftc.teamcode.odometryP2;*/
import org.firstinspires.ftc.teamcode.Tools.PID;
import org.firstinspires.ftc.teamcode.Tools.Robot;

@TeleOp
public class FieldCentric extends LinearOpMode {
    PID ElevatorPIDL = new PID(0.0007, 0.0, 0.0007);
    PID ElevatorPIDR = new PID(0.0007, 0.0, 0.0007);
    PID ArmPID = new PID(0.0015, 0.0, 0.0018);

    @Override
    public void runOpMode() {
        Robot.initialize(hardwareMap);
   /*     odometryP2.initialize(hardwareMap);*/
        Peripherals.initialize(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                DriveSubsystem.drive(1, 0, 0, 0);
            }
            if (gamepad1.b) {
                DriveSubsystem.drive(0, 1, 0, 0);
            }
            if (gamepad1.y) {
                DriveSubsystem.drive(0, 0, 1, 0);
            }
            if (gamepad1.x) {
                DriveSubsystem.drive(0, 0, 0, 1);
            }
            if (gamepad1.dpad_down) {
                DriveSubsystem.drive(1, 1, 1, 1);
            }
            if (gamepad1.dpad_up) {
                Peripherals.resetYaw(); // Correct method call here
            }

            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            DriveSubsystem.drive(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
Odometry.update();
            // Update odometry
        /*    odometryP2.update();*/

            // Retrieve odometry position data
          /*  double robotX = odometryP2.pos.x;
            double robotY = odometryP2.pos.y;
            double robotHeading = odometryP2.pos.h;*/

            // Display telemetry data
          /*  telemetry.addData("X", robotX);
            telemetry.addData("Y", robotY);
            telemetry.addData("Heading", Math.toDegrees(robotHeading));*/ // Convert radians to degrees for readability
            telemetry.addData("odometryx", Odometry.getOdometryX());
            telemetry.addData("odometry y", Odometry.getOdometryY());
            telemetry.addData("odometrytheta", Odometry.getOdometryTheta());
            telemetry.addData("IMU Yaw", Peripherals.getYawDegrees());
            telemetry.addData("y", y);
            telemetry.addData("rx", rx);
            telemetry.update();
        }
    }
}
