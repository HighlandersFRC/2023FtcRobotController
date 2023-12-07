package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.AprilTagDetection;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;

@Autonomous
public class moveToVisionTest extends LinearOpMode {
    Scheduler scheduler = new Scheduler();

    public void runOpMode() throws InterruptedException {
        waitForStart();

    scheduler.add(new AprilTagDetection(hardwareMap, 0.2));
        while (opModeIsActive())
        {
scheduler.update();
        }
    }
}
