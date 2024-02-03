//package org.firstinspires.ftc.teamcode.Commands;
//
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
//
//public class WristUp extends Command{
//    public Servo WristServo;
//
//    public WristUp(HardwareMap hardwareMap){
//
//        WristServo = hardwareMap.servo.get("WristServo");
//    }
//    public void start() {
//        WristServo.setDirection(Servo.Direction.REVERSE);
//        WristServo.setPosition(0);
//    }
//
//    public void execute() {
//    }
//
//    public void end() {
//
//    }
//
//    public boolean isFinished() {
//        return true;
//    }
//}
