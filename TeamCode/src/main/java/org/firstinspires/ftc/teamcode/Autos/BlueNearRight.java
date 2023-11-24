package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ArmConstants;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.Intake;
import org.firstinspires.ftc.teamcode.Commands.DeployIntake;
import org.firstinspires.ftc.teamcode.Commands.RetractIntake;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.RotateArm;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Commands.Wait;
import org.firstinspires.ftc.teamcode.Commands.MoveWrist;
import org.firstinspires.ftc.teamcode.Commands.PixelTray;
import org.firstinspires.ftc.teamcode.Commands.WristUp;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
@Disabled

public class BlueNearRight extends LinearOpMode {
    Scheduler scheduler = new Scheduler();
    @Override
    public void runOpMode() {
        waitForStart();

        scheduler.add(new CommandGroup(scheduler,
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.2, 0.65), new CommandGroup(scheduler, new Wait(1000), new DeployIntake(hardwareMap, "Deploy"))),
                new MoveWrist(hardwareMap, 0.3),
                new Turn(hardwareMap, 90),
                new Drive(hardwareMap, -0.1, 0.28),
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.15, -0.1), new PixelTray(hardwareMap, 3000, -1, "L"), new CommandGroup(scheduler, new Wait(1000), new Intake(hardwareMap, 1000, -0.25))),
                new MoveWrist(hardwareMap, 0.7),
                new Wait(1000),
                new RetractIntake(hardwareMap),
                new Drive(hardwareMap, 0.2, 0.4),
              new DeployIntake(hardwareMap, "Deploy"),
                new Drive(hardwareMap, 0.2, 0.11),
                new RotateArm(hardwareMap, ArmConstants.armPlace),
                new Drive(hardwareMap, 0.1, 0.436),
                new ParallelCommandGroup(scheduler, new PixelTray(hardwareMap, 3000, -1, "R"), new RotateArm(hardwareMap, ArmConstants.armPlace))
        ));
        while (opModeIsActive())
        {
            scheduler.update();
        }
    }
}