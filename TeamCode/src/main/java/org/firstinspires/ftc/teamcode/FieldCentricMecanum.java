package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class FieldCentricMecanum extends LinearOpMode {
    private DcMotor Left_Front;
    private DcMotor Right_Front;
    private DcMotor Left_Back;
    private DcMotor Right_Back;
    private DcMotor Arm_Motor;
    private DcMotor Right_Intake;
    private DcMotor Arm1;
    private DcMotor Arm2;
    private Servo LServo;
    private Servo RServo;
    private CRServo intakeServo;
    private CRServo intakeServo2;
    private CRServo WristServo;
    private long startTime = System.currentTimeMillis();
    private long endTime = startTime + 50;
    org.firstinspires.ftc.teamcode.PID PID = new PID(1, 0, 0);
    org.firstinspires.ftc.teamcode.PID PID2 = new PID(1, 0.0, 0.0);
    PID ArmPID = new PID(0.001, 0.0, 0.0);

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration, ok?
        DcMotor Left_Front = hardwareMap.dcMotor.get("Left_Front");
        DcMotor Left_Back = hardwareMap.dcMotor.get("Left_Back");
        DcMotor Right_Front = hardwareMap.dcMotor.get("Right_Front");
        DcMotor Right_Back = hardwareMap.dcMotor.get("Right_Back");
        Arm_Motor = hardwareMap.dcMotor.get("Arm_Motor");
        Right_Intake = hardwareMap.dcMotor.get("Right_Intake");
        LServo = hardwareMap.servo.get("LServo");
        RServo = hardwareMap.servo.get("RServo");
        Arm1 = hardwareMap.dcMotor.get("Arm1");
        Arm2 = hardwareMap.dcMotor.get("Arm2");
        intakeServo = hardwareMap.crservo.get("intakeServo");
        intakeServo2 = hardwareMap.crservo.get("intakeServo2");
        WristServo = hardwareMap.crservo.get("WristServo");
        if (System.currentTimeMillis() <= endTime && !gamepad1.x && !gamepad1.y){
            ArmPID.setSetPoint(Arm_Motor.getCurrentPosition());
        }
        double leftTrigger = gamepad1.left_trigger;
        double rightTrigger = gamepad1.right_trigger;
        double lstick2 = gamepad2.left_stick_y;
        LServo.scaleRange(-180, 180);
        RServo.scaleRange(-180, 180);

        double r2Trigger =  gamepad2.right_trigger;
        double l2Trigger =  gamepad2.left_trigger;
        double intakePower = (rightTrigger - leftTrigger);

        PID.setMaxOutput(1);
        PID.setMinOutput(-1);
        PID.setPID(0.003, 0, 0.001);
        PID2.setMaxOutput(1);
        PID2.setMinOutput(-1);
        PID2.setPID(0.003, 0, 0.001);
        ArmPID.setMaxOutput(0.75);
        ArmPID.setMinOutput(-0.75);
        ArmPID.updatePID(Arm_Motor.getCurrentPosition());
        /*    Arm1.setPower(PID.getResult() - 0.001);
            Arm2.setPower(PID.getResult() - 0.001);*/
        Arm1.setPower(gamepad2.left_stick_y);
        Arm2.setPower(-gamepad2.left_stick_y);

        Arm_Motor.setPower(-ArmPID.getResult());
        Arm2.setDirection(DcMotorSimple.Direction.REVERSE);
        Arm_Motor.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeServo.setPower(intakePower);
        intakeServo2.setPower(-intakePower);

        if (gamepad1.x){
            ArmPID.setSetPoint(4100);
        } else
        if (gamepad1.y){
            ArmPID.setSetPoint(-50
            );
        }
        if (gamepad2.a){
            ArmPID.setSetPoint(800);
        }
        if (gamepad2.b){
            ArmPID.setSetPoint(2000);
        }

        if(gamepad1.a){
            WristServo.setPower(1);
        }
        else
        if(gamepad1.b){
            WristServo.setPower(-1);
        }else {
            WristServo.setPower(0);
        }


        if (gamepad1.dpad_up) {
            LServo.setPosition(-120);
            RServo.setPosition(120);
        }
        if (gamepad1.dpad_down) {
            LServo.setPosition(1);
            RServo.setPosition(-1);
        }

        Right_Intake.setPower(intakePower);

        if (Math.abs(gamepad1.left_stick_x) < 0.00001){
            Left_Front.setPower(0);
            Left_Back.setPower(0);
            Right_Front.setPower(0);
            Right_Back.setPower(0);
        }
        if (Math.abs(gamepad1.left_stick_y) < 0.00001){
            Left_Front.setPower(0);
            Left_Back.setPower(0);
            Right_Front.setPower(0);
            Right_Back.setPower(0);
        }

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y * 0.75; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 0.75;
            double rx = -gamepad1.right_stick_x * 0.75;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.options) {
                imu.resetYaw();
            }


            double botHeading = Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            double botHeadingRadian = Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

            // Rotate the movement direction counter to the bot's rotation
            double rotX = -(x * Math.cos(-botHeadingRadian) - y * Math.sin(-botHeadingRadian)) / 2.0;
            double rotY =(x * Math.sin(-botHeadingRadian) + y * Math.cos(-botHeadingRadian)) / 2.0;

            // Counteract imperfect strafing
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            Left_Front.setPower(-frontLeftPower);
            Left_Back.setPower(-backLeftPower);
            Right_Front.setPower(frontRightPower);
            Right_Back.setPower(-backRightPower);

            telemetry.addLine("Motor Positions");
            telemetry.addData("Front-Left Position", Left_Front.getCurrentPosition());
            telemetry.addData("Front-Right Position", Right_Front.getCurrentPosition());
            telemetry.addData("Back-Left Position", Left_Back.getCurrentPosition());
            telemetry.addData("Back-Right Position", Right_Back.getCurrentPosition());
            telemetry.addData("Arm One Encoder", Arm1.getCurrentPosition());
            telemetry.addData("Arm Two Encoder", Arm2.getCurrentPosition());
            telemetry.addData("ArmRotateAbsEncoder", Arm_Motor.getCurrentPosition());

            telemetry.addLine("");
            telemetry.addLine("Controller Inputs");
            telemetry.addData("Left Trigger", leftTrigger);
            telemetry.addData("Real Left Trigger", gamepad1.left_trigger);
            telemetry.addData("Right Trigger", rightTrigger);
            telemetry.addData("Real Right Trigger", gamepad1.right_trigger);
            telemetry.addData("PID Result", PID.getResult());
            telemetry.addData("Arm_Motor PID", ArmPID.getResult());

            telemetry.addLine("");
            telemetry.update();
            telemetry.addData("y", y);
            telemetry.addData("x", x);
            telemetry.addData("rx", rx);
            telemetry.addData("rotY", rotY);
            telemetry.addData("rotX", rotX);
            telemetry.addData("parameters", parameters);
            telemetry.addData("IMU", imu);
            telemetry.addData("denominator", denominator);
            telemetry.addData("botHeading", botHeading);
            telemetry.addData("botHeadingRadian", botHeadingRadian);
            telemetry.addData("frontLeftPower", frontLeftPower);
            telemetry.addData("backLeftPower", backLeftPower);
            telemetry.addData("frontRightPower", frontRightPower);
            telemetry.addData("backRightPower", backRightPower);
            telemetry.addData("time", time);
            telemetry.update();



        }
    }
}