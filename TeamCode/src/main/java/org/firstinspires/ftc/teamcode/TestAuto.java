//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.Commands.CommandScheduler;
//import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
//import org.firstinspires.ftc.teamcode.Commands.SequentialCommandGroup;
//import org.firstinspires.ftc.teamcode.Commands.TestCommand;
//import org.firstinspires.ftc.teamcode.Commands.Wait;
//import org.firstinspires.ftc.teamcode.Tools.Parameters;
//
//@Autonomous(name="TestAuto", group="Linear Opmode")
//public class TestAuto extends LinearOpMode {
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        CommandScheduler scheduler = CommandScheduler.getInstance();
//        scheduler.schedule(
//                new SequentialCommandGroup(
//                        new ParallelCommandGroup(Parameters.ANY, new Wait(100000000), new Wait(10000)),
//                        new TestCommand()
//                )
//        );
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//            scheduler.run();
//        }
//    }
//}