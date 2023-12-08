package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ElvatorsPid extends LinearOpMode {
    private Servo WristServo;
    @Override
    public void runOpMode() throws InterruptedException {
        org.firstinspires.ftc.teamcode.PID PID = new PID(0.003, 0.000005, 0.00);
        org.firstinspires.ftc.teamcode.PID PID2 = new PID(0.003, 0.000005, 0.00);
        DcMotor Arm_left = hardwareMap.dcMotor.get("Arm_Left");
        DcMotor Arm_Right = hardwareMap.dcMotor.get("Arm_Right");
        WristServo = hardwareMap.servo.get("WristServo");
        waitForStart();
        while (opModeIsActive()) {
            PID.updatePID(Arm_left.getCurrentPosition());
            PID2.updatePID(Arm_Right.getCurrentPosition());
            Arm_left.setPower(PID.getResult());
            Arm_Right.setPower(PID2.getResult());
            Arm_Right.setDirection(DcMotorSimple.Direction.REVERSE);
            if (gamepad1.a) {
                WristServo.setPosition(0);
                sleep(20);
                PID2.setSetPoint(1500);
                PID.setSetPoint(1500);
                WristServo.setPosition(0.5);
            }
            if (gamepad1.b){
                WristServo.setPosition(1);
                sleep(20);
                PID2.setSetPoint(0);
                PID.setSetPoint(0);
                WristServo.setPosition(0);
                sleep(1000);
                WristServo.setPosition(1);
            }
        if (gamepad1.y)   {

        }
        if (gamepad1.x){

        }
        telemetry.addData("right arm encoder position",Arm_Right.getCurrentPosition());
        telemetry.addData("encoder position",Arm_left.getCurrentPosition());
        telemetry.update();
        }
    }
}