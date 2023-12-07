package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.teamcode.PID1;

@TeleOp

public class Mecanum extends LinearOpMode {
    private DcMotor Left_Front;
    private DcMotor Right_Front;
    private DcMotor Left_Back;
    private DcMotor Right_Back;
    private DcMotor Left_Intake;
    private DcMotor Right_Intake;
    private DcMotor Arm;
    private OpticalDistanceSensor distance_Sensor;

    PID1 PID = new PID1();

    @Override
    public void runOpMode() {

        distance_Sensor = hardwareMap.opticalDistanceSensor.get("distanceSensor");
        Left_Front = hardwareMap.dcMotor.get("Left_Front");
        Right_Front = hardwareMap.dcMotor.get("Right_Front");
        Left_Back = hardwareMap.dcMotor.get("Left_Back");
        Right_Back = hardwareMap.dcMotor.get("Right_Back");
        Left_Intake = hardwareMap.dcMotor.get("Left_Intake");
        Right_Intake = hardwareMap.dcMotor.get("Right_Intake");
        Arm = hardwareMap.dcMotor.get("Arm");
        //IMU imu = hardwareMap.get(IMU.class, "imu");

// Wait for the game to start (driver presses PLAY)

        waitForStart();

        if (isStopRequested()) return;

// run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            PID.setPID(0.75,0.2 ,0.3);
                if (gamepad1.a) {
                    PID.setSetPoint(-329);
                    Arm.setPower(1);
                    Arm.setTargetPosition(PID.getSetPoint());
                }
                if (gamepad1.b) {
                    PID.setSetPoint(0);
                    Arm.setPower(1);
                    Arm.setTargetPosition(PID.getSetPoint());
                }
                else {
                    Arm.setPower(0);
                }
            if (gamepad1.x){
                Left_Intake.setPower(-1);
                Right_Intake.setPower(1);
            }
            else {
                Right_Intake.setPower(0);
                Left_Intake.setPower(0);
            }
            if (gamepad1.y){
                Left_Intake.setPower(0.5);
                Right_Intake.setPower(-0.5);
            }
            //double directionFacing =
            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x * 1;
            double rx = -gamepad1.right_stick_x;

          if (Math.abs(gamepad1.left_stick_x) < 0.01){
              Left_Front.setPower(0);
              Left_Back.setPower(0);
              Right_Front.setPower(0);
              Right_Back.setPower(0);

          }
            if (Math.abs(gamepad1.left_stick_y) < 0.01){
                Left_Front.setPower(0);
                Left_Back.setPower(0);
                Right_Front.setPower(0);
                Right_Back.setPower(0);

            }
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            Left_Front.setPower(frontLeftPower);
            Left_Back.setPower(backLeftPower);
            Right_Front.setPower(-frontRightPower);
            Right_Back.setPower(-backRightPower);

            telemetry.addLine("Motor Positions");
            telemetry.addData("Front-Left Position", Left_Front.getCurrentPosition());
            telemetry.addData("Front-Right Position", Right_Front.getCurrentPosition());
            telemetry.addData("Back-Left Position", Left_Back.getCurrentPosition());
            telemetry.addData("Back-Right Position", Right_Back.getCurrentPosition());
            telemetry.addData("Arm Encoder", Arm.getCurrentPosition());

            telemetry.addLine("");
            telemetry.addLine("Controller Inputs");
            telemetry.addData("Stick X", gamepad1.right_stick_x);
            telemetry.addData("Trigger", gamepad1.right_trigger);
            telemetry.addData("Bumper", gamepad1.right_bumper);
            telemetry.addData("Stick Y", gamepad1.right_stick_y);

            telemetry.addLine("");
            telemetry.addData("Distance Sensor", distance_Sensor);

            telemetry.update();
}

         //   IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
           //         RevHubOrientationOnRobot.LogoFacingDirection.UP,
             //       RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
            //imu.initialize(parameters);
        }
}