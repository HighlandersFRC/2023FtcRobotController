package org.firstinspires.ftc.teamcode.Autos.NewAutos;
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
public class NewRedNearLeft extends LinearOpMode {
    Scheduler scheduler = new Scheduler();
    public void runOpMode() throws InterruptedException {
        waitForStart();
        scheduler.add(new CommandGroup(scheduler,
                new Drive(hardwareMap, -0.3, -0.1),
                new Turn(hardwareMap, -270),
                new MoveWrist(hardwareMap, Constants.wristDown),
                new MainIntake(hardwareMap, 1000, -0.3),
                new MoveWrist(hardwareMap,Constants.wristUp),
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap,-0.5,0.71), new Arm(hardwareMap, Constants.armPlace))

        ));
        while(opModeIsActive()){
            scheduler.update();
        }
    }
}
