package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeServo extends Command{
    public Servo LServo;
    public Servo RServo;
    Boolean Done = false;
    public IntakeServo(HardwareMap hardwareMap){
        LServo = hardwareMap.servo.get("LServo");
        RServo = hardwareMap.servo.get("RServo");
    }

    public void start(){
        LServo.scaleRange(-180, 180);
        LServo.setPosition(180);
        RServo.scaleRange(-180, 180);
        RServo.setPosition(-180);
    }

<<<<<<< Updated upstream
    public void execute(){
        Done = true;
=======
    public boolean execute(){
        Done = true;
        return false;
>>>>>>> Stashed changes
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
