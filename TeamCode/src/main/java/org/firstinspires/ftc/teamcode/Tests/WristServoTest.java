package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Tools.Constants;

@TeleOp
@Disabled
public class WristServoTest extends LinearOpMode {
    public Servo WristServo;
    public void runOpMode() throws InterruptedException {
        WristServo = hardwareMap.servo.get("WristServo");
        waitForStart();

        while (opModeIsActive()){
            if (gamepad1.a){
                WristServo.setPosition(Constants.wristDown);
            }
            if (gamepad1.b){
                WristServo.setPosition(Constants.wristUp);
            }
        }
    }
}
