package org.firstinspires.ftc.teamcode.Autos.BlueNear;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.OldCommands.DeployIntake;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.MainIntake;
import org.firstinspires.ftc.teamcode.Commands.MoveWrist;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.PixelTray;
import org.firstinspires.ftc.teamcode.Commands.OldCommands.RotateArm;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Commands.Wait;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
@Disabled

public class BlueNearMiddle extends LinearOpMode {

    Scheduler scheduler = new Scheduler();
    public IMU imu;
    public AnalogInput armEncoder;
    public DcMotor Arm_Motor;

    @Override
    public void runOpMode() {
        armEncoder = hardwareMap.analogInput.get("absEncoder");
        Arm_Motor = hardwareMap.dcMotor.get("Arm_Motor");
        Arm_Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm_Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Constants.armOffset = -Constants.getOffsetFromVoltage(Constants.absoluteArmZero - armEncoder.getVoltage());

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);

        waitForStart();

        scheduler.add(new CommandGroup(scheduler,
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.2, 0.37), new CommandGroup(scheduler, new Wait(1000), new DeployIntake(hardwareMap, "Deploy"))),
                new MoveWrist(hardwareMap, Constants.wristDown),
                new Turn(hardwareMap, 179),
                new Drive(hardwareMap, -0.2, 0.4),
                new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.15, -0.1), new PixelTray(hardwareMap, 3000, -1, "R"), new CommandGroup(scheduler, new Wait(1000),  new MainIntake(hardwareMap, 1000, -0.25))),
                new MoveWrist(hardwareMap, Constants.wristUp),
                new Wait(1000),
                new DeployIntake(hardwareMap, "Retract"),
                new Drive(hardwareMap, 0.3, 0.05),
                new Turn(hardwareMap, 90),
                new Drive(hardwareMap,0.3, 0.81),
                new RotateArm(hardwareMap, Constants.armPlace),
                new ParallelCommandGroup(scheduler, new PixelTray(hardwareMap, 3000, -1, "L"), new RotateArm(hardwareMap, Constants.armPlace))
        ));
        while (opModeIsActive()) {
            telemetry.addData("IMU", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.update();
            scheduler.update();
        }
    }
}