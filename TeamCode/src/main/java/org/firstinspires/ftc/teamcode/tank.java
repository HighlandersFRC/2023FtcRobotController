//imports
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.arm;
import org.firstinspires.ftc.teamcode.Subsystems.wrist;

@TeleOp
public class tank extends LinearOpMode {
    @Override
    public void runOpMode() {

        // Waits for start

        waitForStart();

        //initializes motor
        DriveTrain.initialize(hardwareMap);
        arm.initialize(hardwareMap);
        Intake.initialize(hardwareMap);
        wrist.initialize(hardwareMap);

        //runs while op mode is active
        while (opModeIsActive()) {

            //variables
            double y = gamepad1.left_stick_y;
            double x = -gamepad1.right_stick_x;
            double LeftPower = (y + x);
            double RightPower = (y - x);


            //drive
            DriveTrain.Drive(RightPower,LeftPower);

            // move arm

            while (gamepad1.right_trigger != 0){
                Intake.moveIntakeMotor();

            }
            if(gamepad1.left_trigger != 0){
                Intake.moveOutTakeMotor();

            }
            else {
               Intake.stop();
            }
            while (gamepad1.left_bumper){
                arm.constantPowerArm(1);

            }
            while (gamepad1.right_bumper){
                arm.constantPowerArm(-1);
            }
            while (gamepad1.a){
                wrist.moveIntakeMotor(0.8);
            }
            while (gamepad1.b){
                wrist.moveIntakeMotor(0.3);
            }

            arm.breakModeArm();

            //Intake.systemOutPrintln();
            wrist.systemOutPrintln();
           System.out.println("armPos"+arm.getArmPos());
        }
    }
}
