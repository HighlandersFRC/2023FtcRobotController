package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

@TeleOp
public class DriveTrainMotorTest extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        DriveTrain.initialize(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            if(gamepad1.dpad_up){
                DriveTrain.Drive(0, 1, 0, 0);
                //back left
            }else {
                DriveTrain.Drive(0,0,0,0);
            }
            if(gamepad1.dpad_down){
                DriveTrain.Drive(0, 0, 0, 1);
                //
            }else {
                DriveTrain.Drive(0,0,0,0);
            }
            if(gamepad1.y){
                DriveTrain.Drive(1, 0, 0, 0);
                //
            }else {
                DriveTrain.Drive(0,0,0,0);
            }
            if(gamepad1.a){
                DriveTrain.Drive(0, 0, 1, 0);
                //
            }else {
                DriveTrain.Drive(0,0,0,0);
            }
        }
    }
}
