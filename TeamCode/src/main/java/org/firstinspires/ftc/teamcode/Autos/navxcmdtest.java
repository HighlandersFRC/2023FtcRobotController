package org.firstinspires.ftc.teamcode.Autos;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Commands.turn2;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous


public class navxcmdtest extends LinearOpMode {
    Scheduler scheduler = new Scheduler();
    AHRS navX;
    @Override
    public void runOpMode() {
        waitForStart();
        navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), com.kauailabs.navx.ftc.AHRS.DeviceDataType.kProcessedData);
        navX.zeroYaw();
        scheduler.add(new CommandGroup(scheduler,
               /* new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.2, 1), new CommandGroup(scheduler, new Wait(1000), new DeployIntake(hardwareMap, "Deploy"))),
                new MoveWrist(hardwareMap, Constants.wristDown),*/
                new turn2(hardwareMap, 45)

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