package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TankDrive extends LinearOpMode {
    public DcMotor motor_0;
    public DcMotor motor_1;
    @Override
    public void runOpMode() throws InterruptedException {

        motor_0 = hardwareMap.dcMotor.get("motor_1");
        motor_1 = hardwareMap.dcMotor.get("motor_2");

        waitForStart();
        while (opModeIsActive()){
            motor_0.setPower(gamepad1.left_stick_y);
            motor_1.setPower(gamepad1.right_stick_y);
        }
    }
}
