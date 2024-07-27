package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.ArmDown;
import org.firstinspires.ftc.teamcode.Commands.ArmUp;
import org.firstinspires.ftc.teamcode.Commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.Commands.ElevatorDown;
import org.firstinspires.ftc.teamcode.Commands.ElevatorUp;
import org.firstinspires.ftc.teamcode.Commands.Intake;
import org.firstinspires.ftc.teamcode.Commands.Outtake;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Odometry;
import org.firstinspires.ftc.teamcode.Tools.Parameters;
import org.firstinspires.ftc.teamcode.Tools.Robot;

@TeleOp(group = "OpMode", name = "FieldCentric")
public class FieldCentric extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.initialize(hardwareMap);
        Odometry.initialize(hardwareMap);

        CommandScheduler scheduler = new CommandScheduler();

        ParallelCommandGroup parallelCommands = new ParallelCommandGroup(scheduler, Parameters.NEVER);
        waitForStart();

        while (opModeIsActive()) {
            DriveSubsystem.MecanumDrive(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

            if (gamepad1.right_bumper){
                Peripherals.resetYaw();
            }
/*            while (gamepad1.a) {
                parallelCommands.addCommand(new ArmDown());
            }
            while (gamepad1.b) {
                parallelCommands.addCommand(new ArmUp());
            }
            while (gamepad1.y) {
                parallelCommands.addCommand(new ElevatorUp());
            }
            while (gamepad1.x) {
                parallelCommands.addCommand(new ElevatorDown());
            }
            while (gamepad1.right_trigger > 0) {
                parallelCommands.addCommand(new Intake());
            }
            while (gamepad1.left_trigger > 0) {
                parallelCommands.addCommand(new Outtake());
            }*/

            telemetry.addData("X", Odometry.getX());
            telemetry.addData("Y", Odometry.getY());
            telemetry.addData("Theta", Odometry.getTheta());
            telemetry.addData("IMU Yaw", Peripherals.getYawDegrees());

            scheduler.cancelAll();
            scheduler.schedule(parallelCommands);
            scheduler.run();
            Odometry.update();
            telemetry.update();
        }
    }
}
