package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class armtest extends LinearOpMode {
    public DcMotor Arm_Motor;
    public void runOpMode() throws InterruptedException {
        Arm_Motor = hardwareMap.dcMotor.get("Arm_Motor");
        waitForStart();

        while(opModeIsActive()){
            Arm_Motor.setPower(1);
        }
    }
}
