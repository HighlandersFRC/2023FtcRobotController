package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Odometry;

@TeleOp
public class Printouts extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DriveSubsystem.initialize(hardwareMap);
        Peripherals.resetYaw();
        waitForStart();

        while (opModeIsActive()) {
/*            telemetry.addData("Right Wheel Encoder", DriveSubsystem.rightMotor.getCurrentPosition());
            telemetry.addData("Left Wheel Encoder", DriveSubsystem.leftMotor.getCurrentPosition());*/
            telemetry.update();
        }
    }
}
