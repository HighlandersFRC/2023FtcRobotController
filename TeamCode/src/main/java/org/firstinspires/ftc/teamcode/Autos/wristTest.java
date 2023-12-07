package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.MoveWrist;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Wait;

@Autonomous
public class wristTest extends LinearOpMode {

//    private Servo wristServo;
    @Override
    public void runOpMode() throws InterruptedException {

//        wristServo = hardwareMap.servo.get("WristServo");
        Scheduler scheduler = new Scheduler();
        waitForStart();
        scheduler.add(new CommandGroup(scheduler, new MoveWrist(hardwareMap, 0.3), new Wait(2000), new MoveWrist(hardwareMap, 0.7)));
        while (opModeIsActive()) {
scheduler.update();
//            wristServo.setPosition(0.5);

        }
    }
}
