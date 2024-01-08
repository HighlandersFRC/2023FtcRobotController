package org.firstinspires.ftc.teamcode.Autos;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Commands.Drive;


import org.firstinspires.ftc.teamcode.Commands.strafeRight;


import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous


public class navxcmdtest extends LinearOpMode {
    Scheduler scheduler = new Scheduler();
    AHRS navX;
    @Override
    public void runOpMode() {
        navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), com.kauailabs.navx.ftc.AHRS.DeviceDataType.kProcessedData);
        waitForStart();
        scheduler.add(new CommandGroup(scheduler,
               /* new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.2, 1), new CommandGroup(scheduler, new Wait(1000), new DeployIntake(hardwareMap, "Deploy"))),
                new MoveWrist(hardwareMap, Constants.wristDown),*/
                //new turn3(hardwareMap, 270)
                //new strafeLeft(hardwareMap,.2,1)
                //new strafeRight(hardwareMap,.2,10)
                //Add your code here
                new strafeRight(hardwareMap,0.5,1.2)

        //new drive2(hardwareMap, -0.2, 0.05)
               /* new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.3, -0.1), new PixelTray(hardwareMap, 3000, -1, "R"), new CommandGroup(scheduler, new Wait(1000), new Intake(hardwareMap, 1000, -0.25))),
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