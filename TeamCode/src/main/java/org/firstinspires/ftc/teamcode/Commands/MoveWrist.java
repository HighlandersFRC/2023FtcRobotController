package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.Wrist;

public class MoveWrist extends Command{
    public Servo WristServo;
    public double target;
    public String getSubsystem() {
        return "Wrist";
    }
    public MoveWrist(HardwareMap hardwareMap, double targetPos){
    this.target = targetPos;
    Wrist.initialize(hardwareMap);

    }
    public void start() {
//        WristServo.scaleRange(-1, 1);
        Wrist.Wrist(target);
    }

    public void execute() {

    }

    public void end() {

    }

    public boolean isFinished() {
        return true;
    }
}
