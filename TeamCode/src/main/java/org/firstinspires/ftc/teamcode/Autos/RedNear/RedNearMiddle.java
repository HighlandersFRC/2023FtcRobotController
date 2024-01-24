package org.firstinspires.ftc.teamcode.Autos.RedNear;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.Commands.Arm;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
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
                new Drive(hardwareMap, -0.4, 0.135),
                new MoveWrist(hardwareMap, Constants.wristDown),
                new Turn(hardwareMap, 180),
                new MainIntake(hardwareMap,750,-.2),
                new MoveWrist(hardwareMap, Constants.wristUp),
                new Turn(hardwareMap, 87),
                new Drive(hardwareMap,-0.3, 0.76),
                new Arm(hardwareMap,Constants.armHigh),
                new ParallelCommandGroup(scheduler, new Arm(hardwareMap, Constants.armHigh), new MainIntake(hardwareMap,1000,-0.25)),
                new Arm(hardwareMap,Constants.armIntake)
        ));
        while (opModeIsActive()) {
            scheduler.update();
        }
    }
}