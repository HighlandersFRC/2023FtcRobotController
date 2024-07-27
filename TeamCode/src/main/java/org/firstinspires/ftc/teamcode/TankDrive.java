package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Odometry;


@TeleOp
public class TankDrive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        DriveSubsystem.initialize(hardwareMap);
        Odometry.initialize(hardwareMap);
        Peripherals.initialize(hardwareMap);

        Peripherals.resetYaw();

        while (opModeIsActive()){

            double y = gamepad1.left_stick_y;
            double rx = -gamepad1.right_stick_x;
            double leftTrigger = gamepad1.left_trigger;
            double rightTrigger = gamepad1.right_trigger;

            double intakePower = leftTrigger - rightTrigger ;

            double leftPower = y + rx;
            double rightPower = y - rx;

            /*DriveSubsystem.drive(leftPower, rightPower);*/

            telemetry.addData("X", Odometry.getX());
            telemetry.addData("Y", Odometry.getY());
            telemetry.addData("Theta", Odometry.getTheta());
            telemetry.addData("IMU Yaw", Peripherals.getYawDegrees());
            telemetry.addData("y", y);
            telemetry.addData("rx", rx);
            Odometry.update();
            telemetry.update();
            
        }
    }
}