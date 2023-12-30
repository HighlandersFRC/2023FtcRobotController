package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Commands.Arm;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.IntakeServo;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Commands.Wait;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous

public class Autonomous extends LinearOpMode {

    Scheduler scheduler = new Scheduler();
    DcMotor Arm1;
    PID1 PID = new PID1(0.03, 0.0, 0.0);

    @Override
    public void runOpMode() {
        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        waitForStart();
        scheduler.add(new CommandGroup(scheduler,
                new Drive(hardwareMap, 0.2, 1)
                /*new Drive(hardwareMap,0.75, 0.75),
                new Turn(hardwareMap, 45),
                new Wait(1000),
                new Drive(hardwareMap, 0.5, 0.2),
                new Arm(hardwareMap, 200),
        new Drive(hardwareMap, -1, 2),
                new Turn(hardwareMap, -90)*/
        ));
        while (opModeIsActive())
        {
            PID.setSetPoint(-180);
            PID.updatePID(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            PID.setMaxInput(180);
            PID.setMinInput(-180);
            PID.setContinuous(true);

            telemetry.addData("Current Command", scheduler.getActive());
            telemetry.addData("IMU", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.addData("PID result", PID.getResult());
            telemetry.update();
            scheduler.update();
        }
    }
}