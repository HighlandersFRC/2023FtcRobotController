package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Wrist extends Subsystems {
    String name = "WristServo";
    public static Servo WristServo;
    public static void Wrist(HardwareMap hardwareMap,double TargetPos){
        WristServo = hardwareMap.servo.get("WristServo");
        WristServo.setPosition(TargetPos);
    }
}
