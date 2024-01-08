package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
public class Wrist extends Subsystems {
    public static String name = "WristServo";
    public static Servo WristServo;
    public static void initialize(HardwareMap hardwareMap){
        WristServo = hardwareMap.servo.get("WristServo");
    }
    public static void Wrist(double TargetPos){
        WristServo.setPosition(TargetPos);
    }
}
