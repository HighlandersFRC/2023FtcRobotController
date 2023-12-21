package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class ElvatorsPid extends LinearOpMode {
    private DcMotor Left_Front;
    private DcMotor Right_Front;
    private DcMotor Left_Back;
    private DcMotor Right_Back;
    private Servo WristServo;
    private DcMotor Right_Intake;
    private CRServo HolderServo_Left;
    private CRServo HolderServo_Right;
    @Override
    public void runOpMode() throws InterruptedException {
        Left_Front = hardwareMap.dcMotor.get("Left_Front");
        Right_Front = hardwareMap.dcMotor.get("Right_Front");
        Left_Back = hardwareMap.dcMotor.get("Left_Back");
        Right_Back = hardwareMap.dcMotor.get("Right_Back");
        Right_Intake = hardwareMap.dcMotor.get("Right_Intake");
        HolderServo_Left =  hardwareMap.crservo.get("HolderServo_Left");
        HolderServo_Right= hardwareMap.crservo.get("HolderServo_Right");
        //IMU imu = hardwareMap.get(IMU.class, "imu");
        //IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                //RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                //RevHubOrientationOnRobot.UsbFacingDirection.UP));
        //imu.initialize(parameters);
        org.firstinspires.ftc.teamcode.PID PID = new PID(0.003, 0.000005, 0.00);
        org.firstinspires.ftc.teamcode.PID PID2 = new PID(0.003, 0.000005, 0.00);
        DcMotor Arm_left = hardwareMap.dcMotor.get("Arm_Left");
        DcMotor Arm_Right = hardwareMap.dcMotor.get("Arm_Right");
        WristServo = hardwareMap.servo.get("WristServo");
        waitForStart();
        while (opModeIsActive()) {
            double pi = 3.1415926;
            //double gyro_degrees =imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            //double gyro_radians = gyro_degrees * pi / 180;
            double rightTrigger=gamepad1.right_trigger;
            double leftTrigger =gamepad1.left_trigger;
            double drive = gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x*1.1;
            double twist = gamepad1.right_stick_x;
            double intakePower = (rightTrigger - leftTrigger);
            PID.updatePID(Arm_left.getCurrentPosition());
            PID2.updatePID(Arm_Right.getCurrentPosition());
            Arm_left.setPower(PID.getResult());
            Arm_Right.setPower(PID2.getResult());
            Arm_Right.setDirection(DcMotorSimple.Direction.REVERSE);
            HolderServo_Right.setDirection(DcMotorSimple.Direction.REVERSE);
            double[] speeds = {
                    (drive + strafe + twist),
                    (drive - strafe - twist),
                    (drive - strafe + twist),
                    (drive + strafe - twist)
            };
            if (gamepad1.a) {
                sleep(30);
                PID2.setSetPoint(1550);
                PID.setSetPoint(1550);
                WristServo.setPosition(0.4);
            }
            if (gamepad1.b){
                WristServo.setPosition(0.4);
                sleep(20);
                PID2.setSetPoint(0);
                PID.setSetPoint(0);
            }
            if(gamepad1.x){
            WristServo.setPosition(.9);
            }

        telemetry.addData("right arm encoder position",Arm_Right.getCurrentPosition());
        telemetry.addData("left arm encoder position",Arm_left.getCurrentPosition());
        telemetry.addData("gamepad1.left_stick_y",gamepad1.left_stick_y);
        telemetry.addData("gamepad1.left_stick_x",gamepad1.left_stick_x);
        telemetry.addData("gamepad1.right_stick_x",gamepad1.right_stick_x);
        telemetry.addData("Left_Front_power",Left_Front.getPower());
        telemetry.addData("Right_Front_power",Right_Front.getPower());
        telemetry.addData("left_Back_power",Left_Back.getPower());
        telemetry.addData("Right_back_power",Right_Back.getPower());
        telemetry.addData("holder_servo_left_power",HolderServo_Left.getPower());
        telemetry.addData("holder_servo_right_power",HolderServo_Right.getPower());
        telemetry.addData("wrist_servo_position",WristServo.getPosition());
        telemetry.addData("climb phase 1 true or false",gamepad1.a);
        telemetry.addData("climb phase 2 true or false",gamepad1.b);
        telemetry.addData("intake power",Right_Intake.getPower());
        telemetry.addData("intake in true or false",gamepad1.left_bumper);
        telemetry.addData("intake out true or false",gamepad1.right_bumper);
        telemetry.update();
        Right_Intake.setPower(intakePower);
        HolderServo_Right.setPower(-intakePower);
        HolderServo_Left.setPower(intakePower);
        //strafe = strafe * Math.cos(gyro_radians) - drive * Math.sin(gyro_radians);
        //drive = strafe * Math.sin(gyro_radians) + drive * Math.cos(gyro_radians);
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
        Left_Front.setPower(speeds[0]);
        Right_Front.setPower(speeds[1]);
        Left_Back.setPower(speeds[2]);
        Right_Back.setPower(speeds[3]);
        }
    }
}