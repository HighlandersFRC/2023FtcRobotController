/*
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.PathingTool.AutonomousFollower;
import org.firstinspires.ftc.teamcode.PathingTool.PathLoading;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;

import org.firstinspires.ftc.teamcode.Tools.Robot;
import org.json.JSONException;

@Autonomous
public class PathFollowingTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.initialize(hardwareMap);
        CommandScheduler commandScheduler = new CommandScheduler();

        PathLoading pathLoading = new PathLoading(hardwareMap.appContext, "OneMeter.polarpath");

        try {
            commandScheduler.schedule(new AutonomousFollower(commandScheduler, pathLoading.getJsonPathData()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        waitForStart();

        while (opModeIsActive()) {

            try {
                commandScheduler.run();
            } catch (JSONException e) {

            }
            DriveSubsystem.update();
                telemetry.addData("X", DriveSubsystem.getOdometryX());
                telemetry.addData("Y", DriveSubsystem.getOdometryY());
                telemetry.addData("Theta", DriveSubsystem.getOdometryTheta());
                telemetry.update();

        }
    }
}
*/
