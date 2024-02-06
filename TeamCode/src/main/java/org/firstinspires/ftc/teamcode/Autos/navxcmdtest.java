package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Commands.navxzero;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.strafe;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous


public class navxcmdtest extends LinearOpMode {
    Scheduler scheduler = new Scheduler();
    @Override
    public void runOpMode() {
        Peripherals.initialize(hardwareMap);
        waitForStart();

        scheduler.add(new CommandGroup(scheduler,
                new strafe(hardwareMap, -0.5, 1)
        ));
        while (opModeIsActive())
        {
            telemetry.addData("NavX Yaw", Peripherals.getYaw());
            telemetry.update();
            scheduler.update();
        }
    }
}