package org.firstinspires.ftc.teamcode.Autos.RedFar;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.MainIntake;
import org.firstinspires.ftc.teamcode.Commands.OldCommands.DeployIntake;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Commands.Wait;
import org.firstinspires.ftc.teamcode.Commands.MoveWrist;
import org.firstinspires.ftc.teamcode.Commands.PixelTray;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
@Disabled
public class RedFarRight extends LinearOpMode {
    Scheduler scheduler = new Scheduler();
    @Override
    public void runOpMode() {
        waitForStart();

        scheduler.add(new CommandGroup(scheduler,
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.2, 0.675), new CommandGroup(scheduler, new Wait(1000), new DeployIntake(hardwareMap, "Deploy"))),
   new MoveWrist(hardwareMap, Constants.wristDown),
                new Turn(hardwareMap, -90),
                new Drive(hardwareMap, 0.3, 0.45),
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.15, -0.1), new PixelTray(hardwareMap, 3000, -1, "R"), new CommandGroup(scheduler, new Wait(1000),  new MainIntake(hardwareMap, 1000, -0.25)))        ));
        while (opModeIsActive())
        {
            scheduler.update();
        }
    }
}