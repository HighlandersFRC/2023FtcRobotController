package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class SetEncoder extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Position of the arm when it's lifted
        int armUpPosition = 2333;

        // Position of the arm when it's down
        int armDownPosition = -2333;

        // Find a motor in the hardware map named "Arm Motor"
        // Make sure your ID's match your configuration, ok?
        DcMotor Left_Front = hardwareMap.dcMotor.get("Left_Front");
        DcMotor Left_Back = hardwareMap.dcMotor.get("Left_Back");
        DcMotor Right_Front = hardwareMap.dcMotor.get("Right_Front");
        DcMotor Right_Back = hardwareMap.dcMotor.get("Right_Back");
        // Find a motor in the hardware map named "Arm Motor"
        double CPR = 288;

        int positionLeft_Front = Left_Front.getCurrentPosition();
        positionLeft_Front = Left_Front.getCurrentPosition();
        double revolutionsLeft_Front = positionLeft_Front/CPR;

        int positionLeft_Back = Left_Back.getCurrentPosition();
        positionLeft_Back = Left_Back.getCurrentPosition();
        double revolutionsLeft_Back = positionLeft_Back/CPR;

        int positionRight_Front = Right_Front.getCurrentPosition();
        positionRight_Front = Right_Front.getCurrentPosition();
        double revolutions = positionRight_Front/CPR;

        int positionRight_Back = Right_Back.getCurrentPosition();
        positionLeft_Back = Right_Back.getCurrentPosition();
        double revolutionsRight_Back = positionRight_Back/CPR;

        double angle = revolutions * 360;
        double angleNormalized = angle % 360;

        // Reset the motor encoder so that it reads zero ticks


        waitForStart();

        while (opModeIsActive()) {

            positionLeft_Front = Left_Front.getCurrentPosition();
            positionLeft_Back = Left_Back.getCurrentPosition();
            positionRight_Front = Right_Front.getCurrentPosition();
            positionRight_Back = Right_Back.getCurrentPosition();

            double desiredPositionLeft_Front = Left_Front.getTargetPosition();
            double desiredPositionLeft_Back = Left_Back.getTargetPosition();
            double desiredPositionRight_Front = Right_Front.getTargetPosition();
            double desiredPositionRight_Back = Right_Back.getTargetPosition();

            // If the A button is pressed, raise the arm
            if (gamepad1.y) {
                positionLeft_Front = positionLeft_Front+100;
                positionRight_Front = positionRight_Front+100;
            }

            // If the B button is pressed, lower the arm
            if (gamepad1.a) {
                positionLeft_Back = positionLeft_Back+100;
                positionRight_Back = positionRight_Back+100;

            }
            if (gamepad1.x) {
                positionLeft_Back = positionLeft_Back+100;
                positionLeft_Front = positionLeft_Front-100;

            }
            if (gamepad1.b) {
                positionRight_Back = positionRight_Back+100;
                positionRight_Front = positionRight_Front-100;

            }




            telemetry.addData("angle", angle);
            telemetry.addData("angleNormalized", angleNormalized);

            telemetry.update();

            telemetry.addData("Encoder Position Left_Front", Left_Front);
            telemetry.addData("Encoder Position Left_Back", Left_Back);
            telemetry.addData("Encoder Position Right_Front", Right_Front);
            telemetry.addData("Encoder Position Right_Back", Right_Back);

            telemetry.addData("Desired Position Left_Front", desiredPositionLeft_Front);
            telemetry.addData("Desired Position Left_Back", desiredPositionLeft_Back);
            telemetry.addData("Desired Position Right_Front", desiredPositionRight_Front);
            telemetry.addData("Desired Position Right_Back", desiredPositionRight_Back);

            telemetry.addData("position Left_Front", positionLeft_Front);
            telemetry.addData("position Left_Back", positionLeft_Back);
            telemetry.addData("position Right_Front", positionRight_Front);
            telemetry.addData("position Right_Back", positionRight_Back);

            telemetry.addData("angle", angle);
            telemetry.addData("angleNormalized", angleNormalized);
            telemetry.update();
        }
    }
}
