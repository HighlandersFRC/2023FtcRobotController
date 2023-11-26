package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.DeployIntake;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.Intake;
import org.firstinspires.ftc.teamcode.Commands.MoveWrist;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.PixelTray;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Commands.Wait;
import org.firstinspires.ftc.teamcode.Constants;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class BlueFarMiddle extends LinearOpMode {

    Scheduler scheduler = new Scheduler();

    @Override
    public void runOpMode() {

        waitForStart();

        scheduler.add(new CommandGroup(scheduler,
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.2, 0.42), new CommandGroup(scheduler, new Wait(1000), new DeployIntake(hardwareMap, "Deploy"))),
                new MoveWrist(hardwareMap, Constants.wristDown),
                new Turn(hardwareMap, 180),
                new Drive(hardwareMap, -0.2, 0.539),
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.15, -0.1), new PixelTray(hardwareMap, 3000, -1, "L"), new CommandGroup(scheduler, new Wait(1000), new Intake(hardwareMap, 1000, -0.25)))
        ));
        while (opModeIsActive()) {
            scheduler.update();
        }
    }
}