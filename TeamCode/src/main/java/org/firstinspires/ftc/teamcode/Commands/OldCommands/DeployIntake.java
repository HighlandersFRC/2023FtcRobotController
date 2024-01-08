package org.firstinspires.ftc.teamcode.Commands.OldCommands;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Commands.Command;

import java.util.Objects;

public class DeployIntake extends Command {
    public Servo LServo;
    public Servo RServo;
    Boolean Done = false;
    public String Deploy;
    public DeployIntake(HardwareMap hardwareMap, String DeployOrRetract){
        LServo = hardwareMap.servo.get("LServo");
        RServo = hardwareMap.servo.get("RServo");
        this.Deploy = DeployOrRetract;
    }
    public String getSubsystem() {
        return "IntakeServo";
    }

    public void start(){
        if(Objects.equals(Deploy, "Deploy")){
            LServo.setPosition(0.3);
            RServo.setPosition(0.8);
        }else
        if (Objects.equals(Deploy, "Retract")){
            LServo.setPosition(0.8);
            RServo.setPosition(0.2);
        }
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
