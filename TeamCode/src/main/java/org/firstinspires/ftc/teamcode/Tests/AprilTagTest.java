package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;

@TeleOp
public class AprilTagTest extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        waitForStart();
        Peripherals.initialize(hardwareMap);
/*        while (opModeIsActive()){
            telemetry.addData("Yaw 1", Peripherals.getAprilTagYaw(1));
            telemetry.addData("Yaw 2", Peripherals.getAprilTagYaw(2));
            telemetry.addData("Yaw 3", Peripherals.getAprilTagYaw(3));
            telemetry.addData("Yaw 4", Peripherals.getAprilTagYaw(4));
            telemetry.addData("Yaw 5", Peripherals.getAprilTagYaw(5));
            telemetry.addData("Yaw 6", Peripherals.getAprilTagYaw(6));
            telemetry.update();
        }*/
    }
}
