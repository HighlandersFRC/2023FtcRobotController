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
            if (gamepad1.dpad_down){
                DriveSubsystem.drive(1,1,1,1);
            }
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            DriveSubsystem.drive(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            telemetry.addData("X", Odometry.getX());
            telemetry.addData("Y", Odometry.getY());
            telemetry.addData("Theta", Odometry.getTheta());
            telemetry.addData("IMU Yaw", Peripherals.getYawDegrees());
            telemetry.addData("y", y);
            telemetry.addData("rx", rx);
            telemetry.update();
            Odometry.update();
        }
    }
}
