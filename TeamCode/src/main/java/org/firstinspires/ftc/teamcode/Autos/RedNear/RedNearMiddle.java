package org.firstinspires.ftc.teamcode.Autos.RedNear;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Commands.Arm;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.MainIntake;
import org.firstinspires.ftc.teamcode.Commands.MoveWrist;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
/*@Disabled*/
public class RedNearMiddle extends LinearOpMode {

    Scheduler scheduler = new Scheduler();

    @Override
    public void runOpMode() {

        waitForStart();

        scheduler.add(new CommandGroup(scheduler,
                new Drive(hardwareMap, -0.4, 0.12),
                new MoveWrist(hardwareMap, Constants.wristDown),
                new Turn(hardwareMap, 180),
                new MainIntake(hardwareMap,1000,-.3),
                new MoveWrist(hardwareMap, Constants.wristUp),
                new Turn(hardwareMap, 95),
                new Drive(hardwareMap,-0.3, 0.725),
                new Arm(hardwareMap,Constants.armPlace),
                new MainIntake(hardwareMap,1000,-3)
        ));
        while (opModeIsActive()) {
            scheduler.update();
        }
    }
}