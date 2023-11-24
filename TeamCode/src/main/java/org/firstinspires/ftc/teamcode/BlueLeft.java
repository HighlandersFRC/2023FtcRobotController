//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.IMU;
//
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
//import org.firstinspires.ftc.teamcode.Commands.Drive;
//import org.firstinspires.ftc.teamcode.Commands.DeployIntake;
//import org.firstinspires.ftc.teamcode.Commands.RotateArm;
//import org.firstinspires.ftc.teamcode.Commands.Scheduler;
//import org.firstinspires.ftc.teamcode.Commands.Turn;
//import org.firstinspires.ftc.teamcode.Commands.PixelTray;
//import org.firstinspires.ftc.teamcode.Commands.wristDown;
//
//@com.qualcomm.robotcore.eventloop.opmode.Autonomous
//
//public class BlueLeft extends LinearOpMode {
//
//    Scheduler scheduler = new Scheduler();
//    DcMotor Arm1;
//    PID PID = new PID(0.03, 0.0, 0.0);
//
//    @Override
//    public void runOpMode() {
//
//        DcMotor Left_Front = hardwareMap.dcMotor.get("Left_Front");
//        DcMotor Right_Front = hardwareMap.dcMotor.get("Right_Front");
//        DcMotor Left_Back = hardwareMap.dcMotor.get("Left_Back");
//        DcMotor Right_Back = hardwareMap.dcMotor.get("Right_Back");
//        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
//        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
//        Left_Back.setDirection(DcMotorSimple.Direction.REVERSE);
//        IMU imu = hardwareMap.get(IMU.class, "imu");
//        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.UP,
//                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
//
//        imu.initialize(parameters);
//
//        waitForStart();
//
//        scheduler.add(new CommandGroup(scheduler,
//        new DeployIntake(hardwareMap),
//        new Drive(hardwareMap, 0.2, 0.75),
//        new Turn(hardwareMap, 90),
//        new RotateArm(hardwareMap, ArmConstants.armSpike),
//                new Drive(hardwareMap, -0.1, 0.1),
//                new wristDown(hardwareMap, 0.38),
//
//                new PixelTray(hardwareMap, 3000, -1, "L")
//
//        ));
//        while (opModeIsActive())
//        {
//            PID.setSetPoint(-180);
//            PID.updatePID(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
//            PID.setMaxInput(180);
//            PID.setMinInput(-180);
//            PID.setContinuous(true);
//
//
//            telemetry.addData("IMU", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
//            telemetry.addData("PID result", PID.getResult());
//            telemetry.addData("Front-Left Position", Left_Front.getCurrentPosition());
//            telemetry.addData("Front-Right Position", Right_Front.getCurrentPosition());
//            telemetry.addData("Back-Left Position", Left_Back.getCurrentPosition());
//            telemetry.addData("Back-Right Position", Right_Back.getCurrentPosition());
//            telemetry.update();
//            scheduler.update();
//        }
//    }
//}
