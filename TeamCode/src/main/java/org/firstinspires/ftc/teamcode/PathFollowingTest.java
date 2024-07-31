package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.PathingTool.AutonomousFollower;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Tools.Odometry;
import org.firstinspires.ftc.teamcode.Tools.Robot;
import org.json.JSONException;

@Autonomous
public class PathFollowingTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.initialize(hardwareMap);
        Odometry.initialize(hardwareMap);
        CommandScheduler commandScheduler = new CommandScheduler();

        commandScheduler.schedule(new AutonomousFollower(new DriveSubsystem(hardwareMap), "OneMeter.polarpath", 0, 9999999, this));
        waitForStart();

        while (opModeIsActive()) {

            try {
                commandScheduler.run();
            } catch (JSONException e) {

            }
            Odometry.update();
                telemetry.addData("X", Odometry.getOdometryX());
                telemetry.addData("Y", Odometry.getOdometryY());
                telemetry.addData("Theta", Odometry.getOdometryTheta());
                telemetry.update();

        }
    }
}
