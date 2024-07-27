package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.PathingTool.PathEngine;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Odometry;
@Autonomous
public class TestAuto extends LinearOpMode {

    private Odometry odometry;
    private DriveSubsystem driveSubsystem;
    private PathEngine pathEngine;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize hardware
        odometry = new Odometry();
        driveSubsystem = new DriveSubsystem(hardwareMap);
        Peripherals.initialize(hardwareMap);
        pathEngine = new PathEngine(this, "OneMeter.polarpath", odometry, driveSubsystem);

        // Initialize Odometry
        odometry.initialize(hardwareMap);

        // Wait for the start button to be pressed
        waitForStart();

        // Start the path following
        pathEngine.startPath(getRuntime());

        // Main loop
        while (opModeIsActive()) {
            double currentTime = getRuntime();
            pathEngine.update(currentTime);

            // Telemetry for debugging
            telemetry.addData("X", Odometry.getX());
            telemetry.addData("Y", Odometry.getY());
            telemetry.addData("Theta", Odometry.getTheta());
            telemetry.update();
        }
    }
}
