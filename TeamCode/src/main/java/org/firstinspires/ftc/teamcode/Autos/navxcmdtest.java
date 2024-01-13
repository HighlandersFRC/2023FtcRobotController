package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Commands.Arm;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Commands.strafeRight;
import org.firstinspires.ftc.teamcode.Tools.Constants;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous


public class navxcmdtest extends LinearOpMode {
    Scheduler scheduler = new Scheduler();
    @Override
    public void runOpMode() {
        waitForStart();

        scheduler.add(new CommandGroup(scheduler,
               /* new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.2, 1), new CommandGroup(scheduler, new Wait(1000), new DeployIntake(hardwareMap, "Deploy"))),
                new MoveWrist(hardwareMap, Constants.wristDown),*/

                new Arm(hardwareMap,Constants.armPlace)

              /*  new drive2(hardwareMap, -0.2, 0.05),
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.3, -0.1), new PixelTray(hardwareMap, 3000, -1, "R"), new CommandGroup(scheduler, new Wait(1000), new Intake(hardwareMap, 1000, -0.25))),
                new MoveWrist(hardwareMap, Constants.wristUp),
                new Wait(1000),
                new DeployIntake(hardwareMap, "Retract"),
                new drive2(hardwareMap, 0.3, 0.1),
                new DeployIntake(hardwareMap, "Deploy"),
                new Wait(1500),


                                new MoveWrist(hardwareMap, Constants.wristDown)*/
        ));
        while (opModeIsActive())
        {
            scheduler.update();
        }
    }
}