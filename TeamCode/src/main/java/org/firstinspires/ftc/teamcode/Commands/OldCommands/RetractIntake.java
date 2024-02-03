package org.firstinspires.ftc.teamcode.Commands.OldCommands;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Commands.Command;

public class RetractIntake extends Command {
    public Servo LServo;
    public Servo RServo;
    Boolean Done = false;
    public String getSubsystem() {
        return "RetractIntake";
    }
    public RetractIntake(HardwareMap hardwareMap){
        LServo = hardwareMap.servo.get("LServo");
        RServo = hardwareMap.servo.get("RServo");
    }

    public void start(){
        LServo.setPosition(0.7);
    }

    public void execute(){
        Done = true;
    }

    public void end(){

    }

    public boolean isFinished(){
        if (Done){
            return true;
        }
        return false;
    }
}
