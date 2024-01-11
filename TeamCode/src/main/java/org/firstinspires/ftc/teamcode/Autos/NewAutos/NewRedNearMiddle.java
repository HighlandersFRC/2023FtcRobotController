package org.firstinspires.ftc.teamcode.Autos.NewAutos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.MainIntake;
import org.firstinspires.ftc.teamcode.Commands.MoveWrist;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Commands.Wait;
import org.firstinspires.ftc.teamcode.Commands.strafeRight;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Tools.Constants;

@Autonomous
public class NewRedNearMiddle extends LinearOpMode {
    Scheduler scheduler = new Scheduler();
    public void runOpMode() throws InterruptedException {
        waitForStart();
        scheduler.add(new CommandGroup(scheduler,
                new MoveWrist(hardwareMap,Constants.wristDown),
                new Drive(hardwareMap, -0.3, -0.13),
                new Turn(hardwareMap, -180),
                new MoveWrist(hardwareMap, Constants.wristDown),
                new MoveWrist(hardwareMap, Constants.wristDown),
                new MainIntake(hardwareMap, 1000, -0.2),
                new Turn(hardwareMap, 80),
                new Drive(hardwareMap, -0.5, 0.5),
                new Drive(hardwareMap,-0.5,0.71)


        ));
        while(opModeIsActive()){
            //telemetry.addData("Encoders", DriveTrain.getLeftBackEncoder() + " " + DriveTrain.getLeftFrontEncoder() + " " + DriveTrain.getRightFrontEncoder() + " " + DriveTrain.getRightBackEncoder());
            telemetry.update();
            scheduler.update();
        }
    }
}
