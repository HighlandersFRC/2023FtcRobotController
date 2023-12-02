package org.firstinspires.ftc.teamcode;

import android.os.Handler;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.NextLock;
import com.qualcomm.robotcore.wifi.WifiDirectAssistantAndroid10Extensions;

import org.firstinspires.ftc.robotcore.internal.network.ControlHubDeviceNameManager;
import org.firstinspires.ftc.robotcore.internal.network.WifiDirectDeviceNameManager;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.MoveWrist;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.RotateArm;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Wait;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

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
    private boolean armCurrentlyRetracting = false;
    private boolean wristCurrentlyGoingDown = false;

    org.firstinspires.ftc.teamcode.PID PID = new PID(0.03, 0.0, 0.0);
    org.firstinspires.ftc.teamcode.PID PID2 = new PID(0.03, 0.0, 0.0);
    PID ArmPID = new PID(0.001, 0.0, 0.0);

    @Override
    public void runOpMode() throws InterruptedException {


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
        Arm_Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm_Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

/*        double voltageDelta = Constants.absoluteArmZero - armEncoder.getVoltage();
        double ticksOffset = voltageDelta / Constants.voltsPer1000Ticks * 1000;*/
        Constants.armOffset = -Constants.getOffsetFromVoltage(Constants.absoluteArmZero - armEncoder.getVoltage());
//arm 1 is positive 600 max extension
        //arm 2 is -510 max extension
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //arm2 is reversed
            Scheduler scheduler = new Scheduler();


            Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
            Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
/*            Left_Back.setDirection(DcMotorSimple.Direction.REVERSE);*///comment for comp bot

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
            PID.updatePID(Arm1.getCurrentPosition());
            PID2.updatePID(Arm2.getCurrentPosition());
            ArmPID.setMaxOutput(0.75);
            ArmPID.setMinOutput(-0.75);
            ArmPID.updatePID(Arm_Motor.getCurrentPosition() - Constants.armOffset);

            Arm_Motor.setPower(-ArmPID.getResult());
            Arm2.setDirection(DcMotorSimple.Direction.REVERSE);

            holderservo_left.setPower(-intakePower);
            holderservo_right.setPower(intakePower);

            if (gamepad1.a){
                PID.setSetPoint(0);
                PID2.setSetPoint(0);
            }
            if (gamepad1.b){
                PID.setSetPoint(1650);
                PID2.setSetPoint(1650);

            }
            if (gamepad1.y){
                Arm1.setPower(-0.75);
                Arm2.setPower(-0.75);
                PID.setSetPoint(0);
                PID2.setSetPoint(0);
                WristServo.setPosition(Constants.wristUp);
                LServo.setPosition(0.2);
                RServo.setPosition(0.8);
            }else {
                Arm1.setPower(PID.getResult());
                Arm2.setPower(PID2.getResult());
            }

/*            if (gamepad2.x){
                ArmPID.setSetPoint(Constants.armIntake);
            }

            else

            if (gamepad2.y){
                ArmPID.setSetPoint(Constants.armPlace);
            }*/

            if(gamepad2.a && !armCurrentlyRetracting) {
                armCurrentlyRetracting = true;
                /*scheduler.add(new ParallelCommandGroup(scheduler, new MoveWrist(hardwareMap, Constants.wristDown), new Wait(500), new RotateArm(hardwareMap, Constants.armIntake)));
                 */

                ArmPID.setSetPoint(Constants.armIntake);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        WristServo.setPosition(Constants.wristDown);
                        armCurrentlyRetracting = false;
                    }
                }, 750);

            }
            else
                if(gamepad2.b && !wristCurrentlyGoingDown){
                    wristCurrentlyGoingDown = true;
                    WristServo.setPosition(Constants.wristUp);
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            ArmPID.setSetPoint(Constants.armPlace);
                            wristCurrentlyGoingDown = false;
                        }
                    }, 750);
                   /* scheduler.add(new CommandGroup(scheduler,new MoveWrist(hardwareMap, Constants.wristUp), new Wait(500), new RotateArm(hardwareMap, Constants.armPlace)));*/
                }
//For Competion Bot Use these values
            if (gamepad2.dpad_up) {
                LServo.setPosition(0.8);
                RServo.setPosition(0.2);
            }
            if (gamepad2.dpad_down) {
                LServo.setPosition(0.3);
                RServo.setPosition(0.8);
            }
//end
            Right_Intake.setPower(intakePower);

            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x * 1.1;
            double rx = -gamepad1.right_stick_x;

            if (Math.abs(gamepad1.left_stick_x) < 0.2){
                Left_Front.setPower(0);
                Left_Back.setPower(0);
                Right_Front.setPower(0);
                Right_Back.setPower(0);
            }
            //3304, 2.91
            //0 is 2.91V and 3304 as
            //0.5V as back
            if (Math.abs(gamepad1.left_stick_y) < 0.2){
                Left_Front.setPower(0);
                Left_Back.setPower(0);
                Right_Front.setPower(0);
                Right_Back.setPower(0);
            }

            if (Math.abs(Arm_Motor.getCurrentPosition() - Constants.getOffsetFromVoltage(armEncoder.getVoltage())) <= 100) {
                Constants.armIntake = Arm_Motor.getCurrentPosition() - Constants.getOffsetFromVoltage(armEncoder.getVoltage());
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
            telemetry.addData("TicksOffset", Constants.armOffset);
            telemetry.addData("Front-Left Position", Left_Front.getCurrentPosition());
            telemetry.addData("Front-Right Position", Right_Front.getCurrentPosition());
            telemetry.addData("Back-Left Position", Left_Back.getCurrentPosition());
            telemetry.addData("Back-Right Position", Right_Back.getCurrentPosition());
            telemetry.addData("Arm One Encoder", Arm1.getCurrentPosition());
            telemetry.addData("Arm Two Encoder", Arm2.getCurrentPosition());
            telemetry.addData("Arm Encoder", Arm_Motor.getCurrentPosition() - Constants.armOffset);
            telemetry.addData("Wrist Servo Position", WristServo.getPosition());
            telemetry.addData("Arm Voltage", armEncoder.getVoltage());

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