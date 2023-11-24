package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
//23477
public class Mecanum extends LinearOpMode {
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
    private CRServo holderservo_left;
    private CRServo holderservo_right;
    private Servo WristServo;
    private long startTime = System.currentTimeMillis();
    private long endTime = startTime + 50;
    private AnalogInput armEncoder;

    org.firstinspires.ftc.teamcode.PID PID = new PID(0.03, 0.0, 0.0);
    org.firstinspires.ftc.teamcode.PID PID2 = new PID(0.03, 0.0, 0.0);
    PID ArmPID = new PID(0.001, 0.0, 0.0);

    @Override
    public void runOpMode() {


        Left_Front = hardwareMap.dcMotor.get("Left_Front");
        Right_Front = hardwareMap.dcMotor.get("Right_Front");
        Left_Back = hardwareMap.dcMotor.get("Left_Back");
        Right_Back = hardwareMap.dcMotor.get("Right_Back");
        Arm_Motor = hardwareMap.dcMotor.get("Arm_Motor");
        Right_Intake = hardwareMap.dcMotor.get("Right_Intake");
        LServo = hardwareMap.servo.get("LServo");
        RServo = hardwareMap.servo.get("RServo");
        Arm1 = hardwareMap.dcMotor.get("Arm_Left");
        Arm2 = hardwareMap.dcMotor.get("Arm_Right");
        holderservo_left = hardwareMap.crservo.get("HolderServo_Left");
        holderservo_right = hardwareMap.crservo.get("HolderServo_Right");
        WristServo = hardwareMap.servo.get("WristServo");
        armEncoder = hardwareMap.analogInput.get("absEncoder");


        waitForStart();
/*        Arm_Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm_Motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
//arm 1 is positive 600 max extension
        //arm 2 is -510 max extension
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //arm2 is reversed

            if (System.currentTimeMillis() <= endTime + 5 && !gamepad1.x && !gamepad1.y){
                ArmPID.setSetPoint(ArmConstants.armIntake);
            }

            Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
            Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
            Left_Back.setDirection(DcMotorSimple.Direction.REVERSE);

            double leftTrigger = gamepad1.left_trigger;
            double rightTrigger = gamepad1.right_trigger;
            double lstick2 = gamepad2.left_stick_y;
            LServo.scaleRange(-180, 180);
            RServo.scaleRange(-180, 180);
            WristServo.scaleRange(-150, 150);

            double r2Trigger =  gamepad2.right_trigger;
            double l2Trigger =  gamepad2.left_trigger;
            double intakePower = (rightTrigger - leftTrigger);

            PID.setMaxOutput(1);
            PID.setMinOutput(-1);
            PID.setPID(0.003, 0, 0.001);
            PID2.setMaxOutput(1);
            PID2.setMinOutput(-1);
            PID2.setPID(0.003, 0, 0.001);
            PID.updatePID(Arm1.getCurrentPosition());
            PID2.updatePID(Arm2.getCurrentPosition());
            ArmPID.setMaxOutput(0.75);
            ArmPID.setMinOutput(-0.75);
            ArmPID.updatePID(Arm_Motor.getCurrentPosition());
            Arm1.setPower(PID.getResult());
            Arm2.setPower(PID2.getResult());

            Arm_Motor.setPower(-ArmPID.getResult());
            Arm2.setDirection(DcMotorSimple.Direction.REVERSE);

            holderservo_left.setPower(intakePower);
            holderservo_right.setPower(-intakePower);

            if (gamepad1.a){
                PID.setSetPoint(-230);
                PID2.setSetPoint(38);
            }

            if (gamepad1.b){
                PID.setSetPoint(-1931);
                PID2.setSetPoint(-1644);
            }

            if (gamepad2.x){
                ArmPID.setSetPoint(ArmConstants.armIntake);
            }

            else

            if (gamepad2.y){
                ArmPID.setSetPoint(ArmConstants.armPlace);
            }
            /*if (gamepad2.a){
                PID.setSetPoint(0);
                PID2.setSetPoint(0);
            }
            if (gamepad2.b){
                PID.setSetPoint(-1622);
                PID2.setSetPoint(-1637);
            }
*/
            if(gamepad2.a){
                WristServo.setPosition(0.3);
                WristServo.setDirection(Servo.Direction.REVERSE);
            }
            else
                if(gamepad2.b){
                    WristServo.setPosition(0.7);
                    WristServo.setDirection(Servo.Direction.REVERSE);
                }

            if (gamepad2.dpad_up) {
                LServo.setPosition(-80);
                RServo.setPosition(80);
            }
            if (gamepad2.dpad_down) {
                LServo.setPosition(-1);
                RServo.setPosition(1);
            }

            Right_Intake.setPower(intakePower);

            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = -gamepad1.right_stick_x;

            if (Math.abs(gamepad1.left_stick_x) < 0.00001){
                Left_Front.setPower(0);
                Left_Back.setPower(0);
                Right_Front.setPower(0);
                Right_Back.setPower(0);
            }
            //3304, 2.91
            //0 is 2.91V and 3304 as
            //0.5V as back
            if (Math.abs(gamepad1.left_stick_y) < 0.00001){
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
            Right_Front.setPower(frontRightPower);
            Right_Back.setPower(backRightPower);

            telemetry.addLine("Motor Positions");
            telemetry.addData("Front-Left Position", Left_Front.getCurrentPosition());
            telemetry.addData("Front-Right Position", Right_Front.getCurrentPosition());
            telemetry.addData("Back-Left Position", Left_Back.getCurrentPosition());
            telemetry.addData("Back-Right Position", Right_Back.getCurrentPosition());
            telemetry.addData("Arm One Encoder", Arm1.getCurrentPosition());
            telemetry.addData("Arm Two Encoder", Arm2.getCurrentPosition());
            telemetry.addData("ArmRotateAbsEncoder", Arm_Motor.getCurrentPosition());
            telemetry.addData("Wrist Servo Position", WristServo.getPosition());
            telemetry.addData("Arm Encoder", armEncoder.getVoltage());

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
        }
    }
}