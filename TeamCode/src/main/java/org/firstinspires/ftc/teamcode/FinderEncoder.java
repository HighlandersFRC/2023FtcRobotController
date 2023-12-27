package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class FinderEncoder extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        // Find a motor in the hardware map named "Arm Motor"
        DcMotor armMotor = hardwareMap.dcMotor.get("Arm Motor");
        DcMotor motor = hardwareMap.dcMotor.get("Arm Motor");
        int position = motor.getCurrentPosition();
        double CPR = 288;

        position = motor.getCurrentPosition();
        double revolutions = position/CPR;

        double angle = revolutions * 360;
        double angleNormalized = angle % 360;

        // Reset the motor encoder so that it reads zero ticks

        waitForStart();

        while (opModeIsActive()) {

            // Get the current position of the armMotor
            position = armMotor.getCurrentPosition();

            // Get the target position of the armMotor
            double desiredPosition = armMotor.getTargetPosition();

            // Show the position of the armMotor on telemetry
            telemetry.addData("Encoder Position", position);

            // Show the target position of the armMotor on telemetry
            telemetry.addData("Desired Position", desiredPosition);
            telemetry.addData("angle", angle);
            telemetry.addData("angleNormalized", angleNormalized);

            telemetry.update();
        }
    }
}