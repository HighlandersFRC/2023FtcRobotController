package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public  class newDriveTest extends LinearOpMode {
    private DcMotor Left_Front;
    private DcMotor Right_Front;
    private DcMotor Left_Back;
    private DcMotor Right_Back;
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double twist = gamepad1.right_stick_x;
            double[] speeds = {
                    (drive + strafe + twist),
                    (drive - strafe - twist),
                    (drive - strafe + twist),
                    (drive + strafe - twist)
            };
            Left_Front.setPower(speeds[0]);
            Right_Front.setPower(-speeds[1]);
            Left_Back.setPower(speeds[2]);
            Right_Back.setPower(-speeds[3]);
        }
    }
}
