//package org.firstinspires.ftc.teamcode;
//
//import android.content.Context;
//
//import com.qualcomm.ftccommon.CommandList;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.Commands.Command;
//import org.firstinspires.ftc.teamcode.Commands.CommandScheduler;
//import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
//import org.firstinspires.ftc.teamcode.PathingTool.PathLoading;
//import org.firstinspires.ftc.teamcode.PathingTool.PolarPathFollower;
//import org.firstinspires.ftc.teamcode.Subsystems.Drive;
//import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
//import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
//import org.firstinspires.ftc.teamcode.Tools.Parameters;
//import org.json.JSONException;
//
//@Autonomous
//public class TestAuto extends LinearOpMode {
//
//
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        // Initialize hardware
//        Drive.initialize(hardwareMap);
//        Drive driveSubsystem;
//        driveSubsystem = new Drive();
//        Peripherals peripherals = new Peripherals("Peripherals");
//        Peripherals.initialize(hardwareMap);
//        PathLoading pathLoading = new PathLoading(hardwareMap.appContext, "Simple.polarpath");
//        CommandScheduler scheduler = new CommandScheduler();
//
//
//        waitForStart();
//
//        // Start the path following
//        ParallelCommandGroup polarFollow = null;
//        try {
//            polarFollow = new PolarPathFollower(scheduler, Parameters.ALL,driveSubsystem, peripherals, pathLoading.getJsonPathData());
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        scheduler.schedule(polarFollow);
//        // Main loop
//        while (opModeIsActive()) {
//            try {
//                scheduler.run();
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//
//            telemetry.addData("X", DriveSubsystem.getOdometryX());
//            telemetry.addData("Y", DriveSubsystem.getOdometryY());
//            telemetry.addData("Theta", DriveSubsystem.getOdometryTheta());
//            telemetry.update();
//        }
//    }
//}
