package org.firstinspires.ftc.teamcode.Autos.BlueNear;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.MainIntake;
import org.firstinspires.ftc.teamcode.Commands.DeployIntake;
import org.firstinspires.ftc.teamcode.Commands.RetractIntake;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.RotateArm;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Commands.Wait;
import org.firstinspires.ftc.teamcode.Commands.MoveWrist;
import org.firstinspires.ftc.teamcode.Commands.PixelTray;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
@Disabled
public class BlueNearRight extends LinearOpMode {
    Scheduler scheduler = new Scheduler();
    @Override
    public void runOpMode() {
        waitForStart();

        scheduler.add(new CommandGroup(scheduler,
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.2, 0.65), new CommandGroup(scheduler, new Wait(1000), new DeployIntake(hardwareMap, "Deploy"))),
                new MoveWrist(hardwareMap, Constants.wristDown),
                new Turn(hardwareMap, 90),
                new Drive(hardwareMap, -0.2, 0.28),
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.3, -0.1), new PixelTray(hardwareMap, 3000, -1, "R"), new CommandGroup(scheduler, new Wait(1000), new MainIntake(hardwareMap, 1000, -0.25))),
                new MoveWrist(hardwareMap, Constants.wristUp),
                new Wait(1000),
                new RetractIntake(hardwareMap),
                new Drive(hardwareMap, 0.2, 0.4),
              new DeployIntake(hardwareMap, "Deploy"),
                new Drive(hardwareMap, 0.2, 0.11),
                new Drive(hardwareMap, 0.2, 0.436),
                new RotateArm(hardwareMap, Constants.armPlace),
                new ParallelCommandGroup(scheduler, new PixelTray(hardwareMap, 3000, -1, "L"), new RotateArm(hardwareMap, Constants.armPlace))
        ));
        while (opModeIsActive())
        {
            scheduler.update();
        }
    }
}