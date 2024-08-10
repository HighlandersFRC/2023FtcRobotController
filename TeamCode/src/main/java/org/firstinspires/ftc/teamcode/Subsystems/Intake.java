package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends Subsystems{
    public static String name = "Intake";

    public static CRServo Intake;
    public static Servo Shooter;

    public static void initialize(HardwareMap hardwareMap){
        Intake = hardwareMap.crservo.get("Intake");

    }
    public static void moveIntakeMotor(){
        Intake.setDirection(DcMotorSimple.Direction.FORWARD);
        Intake.setPower(1);
    }
    public static void moveOutTakeMotor(){
        Intake.setDirection(DcMotorSimple.Direction.FORWARD);
        Intake.setPower(-1);
    }
    public static void stop(){
        Intake.setDirection(DcMotorSimple.Direction.FORWARD);
        Intake.setPower(0);
    }

    public static void systemOutPrintln (){
        System.out.println(Intake.getPower());
    }

}
