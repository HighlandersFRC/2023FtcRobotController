package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class MoveWrist extends Command{
    public Servo WristServo;
    public double target;

    public MoveWrist(HardwareMap hardwareMap, double targetPos){
    WristServo = hardwareMap.servo.get("WristServo");
    this.target = targetPos;

    }
    public void start() {
//        WristServo.scaleRange(-1, 1);
        WristServo.setPosition(target);
    }

    public void execute() {

    }

    public void end() {

    }

    public boolean isFinished() {
        return true;
    }
}
