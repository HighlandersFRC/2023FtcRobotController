package org.firstinspires.ftc.teamcode.Autos.RedNear;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Commands.Arm;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.MainIntake;
import org.firstinspires.ftc.teamcode.Commands.MoveWrist;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Tools.Constants;

@Autonomous
public class RedNearRight extends LinearOpMode {

    Scheduler scheduler = new Scheduler();
    public void runOpMode() throws InterruptedException {
        waitForStart();
        scheduler.add(new CommandGroup(scheduler,
                new MoveWrist(hardwareMap,Constants.wristDown),
                new Drive(hardwareMap, -0.3, -0.13),
                new Turn(hardwareMap, -90),
                new MoveWrist(hardwareMap, Constants.wristDown),
                new MainIntake(hardwareMap, 1000, -0.2),
                new Turn(hardwareMap, -180),
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap,-0.5,0.71), new Arm(hardwareMap, Constants.armPlace)),
                new MoveWrist(hardwareMap,Constants.wristUp),
                new MainIntake(hardwareMap, 3000, -0.5)
        ));
        while(opModeIsActive()){
            scheduler.update();
        }
    }
}
///