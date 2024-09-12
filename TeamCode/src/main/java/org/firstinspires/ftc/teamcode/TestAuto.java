package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.PathingTool.PathLoading;
import org.firstinspires.ftc.teamcode.PathingTool.PolarPathFollower;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.json.JSONException;

import java.util.HashMap;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Autonomous
public class TestAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        HashMap<String, Supplier<Command>> commandMap = new HashMap<>();
        HashMap<String, BooleanSupplier> conditionMap = new HashMap<>();

        Drive.initialize(hardwareMap);
        Drive driveSubsystem = new Drive();
        Peripherals peripherals = new Peripherals("Peripherals");
        Peripherals.initialize(hardwareMap);

        PathLoading pathLoading = new PathLoading(hardwareMap.appContext, "OneMeter.polarpath");
        CommandScheduler scheduler = new CommandScheduler();

        Command polarFollow;
        try {
            polarFollow = new PolarPathFollower(driveSubsystem, peripherals, pathLoading.getJsonPathData(), commandMap, conditionMap, scheduler);
        } catch (Exception e) {
            telemetry.addData("Error", "Failed to initialize PolarPathFollower: " + e.getMessage());
            telemetry.update();
            return;
        }

        waitForStart();

        try {
            scheduler.schedule(polarFollow);
        } catch (JSONException e) {
            telemetry.addData("Error", "Failed to schedule PolarPathFollower: " + e.getMessage());
            telemetry.update();
            return;
        }

        while (opModeIsActive()) {
            try {
                scheduler.run();
                Thread.sleep(100);
            } catch (Exception e) {
                telemetry.addData("Error", "Runtime exception: " + e.getMessage());
                telemetry.update();
            }

            telemetry.addData("X", driveSubsystem.getOdometryX());
            telemetry.addData("Y", driveSubsystem.getOdometryY());
            telemetry.addData("Theta", driveSubsystem.getOdometryTheta());
            telemetry.update();
        }
    }
}
