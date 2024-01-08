package org.firstinspires.ftc.teamcode.Autos.NewAutos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;

@TeleOp
public class NewRedNearMiddle extends LinearOpMode {
    Scheduler scheduler = new Scheduler();
    public void runOpMode() throws InterruptedException {
        waitForStart();
        scheduler.add(new CommandGroup(scheduler,
                new Drive(hardwareMap, -0.5, -1)
        ));
        while(opModeIsActive()){
            scheduler.update();
        }
    }
}
