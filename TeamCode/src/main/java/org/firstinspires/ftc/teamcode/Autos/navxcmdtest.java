package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Commands.Arm;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Commands.Wait;
import org.firstinspires.ftc.teamcode.Commands.navxzero;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.strafe;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Constants;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous


public class navxcmdtest extends LinearOpMode {
    Scheduler scheduler = new Scheduler();
    @Override
    public void runOpMode() {
        Peripherals.initialize(hardwareMap);
        waitForStart();

        scheduler.add(new CommandGroup(scheduler,
                new Arm(hardwareMap, Constants.armPlace),
                new Wait(1000),
                new Arm(hardwareMap, Constants.armIntake)
        ));
        while (opModeIsActive())
        {
            telemetry.addData("Arm", org.firstinspires.ftc.teamcode.Subsystems.Arm.getRawPosition());
            telemetry.addData("NavX Yaw", Peripherals.getYaw());
            telemetry.update();
            scheduler.update();
        }
    }
}