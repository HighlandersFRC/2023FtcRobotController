package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Tools.PID;

public class Elevators extends Subsystems{
    public static DcMotor ArmL;
    public static DcMotor ArmR;

    public static String name = "Elevators";
    public static void initialize(HardwareMap hardwareMap){
        ArmL = hardwareMap.dcMotor.get("Arm_Left");
        ArmR = hardwareMap.dcMotor.get("Arm_Right");

        ArmL.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public static void resetEncoders(){
        ArmL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ArmL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ArmR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public static double getArmLPosition(){
        return ArmL.getCurrentPosition();
    }
    public static double getArmRPosition(){
        return ArmR.getCurrentPosition();
    }
    public static void moveElevatorsL(double power){
        ArmL.setPower(power);
    }
    public static void moveElevatorsR(double power){
        ArmR.setPower(power);
    }
}