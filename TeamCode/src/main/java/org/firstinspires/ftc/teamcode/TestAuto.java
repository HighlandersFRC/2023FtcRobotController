package org.firstinspires.ftc.teamcode;

import android.content.Context;

import com.qualcomm.ftccommon.CommandList;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.PathingTool.PathLoading;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Parameters;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Autonomous
public class TestAuto extends LinearOpMode {



    @Override
    public void runOpMode() throws InterruptedException {

        HashMap<String, Supplier<Command>> commandMap = new HashMap<>();

        HashMap<String, BooleanSupplier> conditionMap = new HashMap<>();

        // Initialize hardware
        Drive.initialize(hardwareMap);
        Drive driveSubsystem;
        driveSubsystem = new Drive();
        Peripherals peripherals = new Peripherals("Peripherals");
        Peripherals.initialize(hardwareMap);
        PathLoading pathLoading = new PathLoading(hardwareMap.appContext, "OneMeter.polarpath");
        CommandScheduler scheduler = new CommandScheduler();

        Command polarFollow;
        try {
/*
            polarFollow = new PolarPathFollower(driveSubsystem, peripherals, pathLoading.getJsonPathData(), commandMap, conditionMap, scheduler);;
*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        waitForStart();

        // Start the path following

/*
        scheduler.schedule(polarFollow);
*/

        // Main loop
        while (opModeIsActive()) {
            try {
                scheduler.run();
                Thread.sleep(100);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            telemetry.addData("X", DriveSubsystem.getOdometryX());
            telemetry.addData("Y", DriveSubsystem.getOdometryY());
            telemetry.addData("Theta", DriveSubsystem.getOdometryTheta());
            telemetry.update();
        }
    }
}
