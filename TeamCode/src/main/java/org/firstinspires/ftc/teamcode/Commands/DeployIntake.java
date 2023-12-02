package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DeployIntake extends Command{
    public Servo LServo;
    public Servo RServo;
    Boolean Done = false;
    public String Deploy;
    public DeployIntake(HardwareMap hardwareMap, String DeployOrRetract){
        LServo = hardwareMap.servo.get("LServo");
        RServo = hardwareMap.servo.get("RServo");
        this.Deploy = DeployOrRetract;
    }

    public void start(){
        if(Deploy == "Deploy"){
            LServo.setPosition(0.3);
            RServo.setPosition(0.8);
        }else
        if (Deploy == "Retract"){
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
