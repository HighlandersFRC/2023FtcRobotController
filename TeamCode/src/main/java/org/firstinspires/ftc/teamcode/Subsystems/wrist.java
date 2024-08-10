package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class wrist extends Subsystems{
    public static String name = "wrist";

    public static Servo wrist;


    public static void initialize(HardwareMap hardwareMap){

        wrist = hardwareMap.servo.get("wrist");

    }
    public static void moveIntakeMotor(double wristPos){
        wrist.setPosition(wristPos);

    }
    public static void systemOutPrintln (){
        System.out.println(wrist.getPosition());
    }

}
