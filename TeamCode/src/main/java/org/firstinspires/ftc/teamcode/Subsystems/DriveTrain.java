package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveTrain extends Subsystems{
    String name = "DriveTrain";

    public static DcMotor Left_Front;
    public static DcMotor Right_Front;
    public static DcMotor Left_Back;
    public static DcMotor Right_Back;
    public static void Drive(double RightFrontPower, double LeftFrontPower, double RightBackPower, double LeftBackPower){

        //new comp bot
        Right_Front.setPower(RightFrontPower);
        Right_Back.setPower(RightBackPower);
        Left_Front.setPower(LeftFrontPower);
        Left_Back.setPower(-LeftBackPower);

        //old comp bot
/*        Right_Front.setPower(-RightFrontPower);
        Right_Back.setPower(-RightBackPower);
        Left_Front.setPower(LeftFrontPower);
        Left_Back.setPower(LeftBackPower);*/
    }
    public static void initialize(HardwareMap hardwareMap){
        Right_Front = hardwareMap.dcMotor.get("Right_Front");
        Left_Front = hardwareMap.dcMotor.get("Left_Front");
        Right_Back = hardwareMap.dcMotor.get("Right_Back");
        Left_Back = hardwareMap.dcMotor.get("Left_Back");

        Right_Front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Left_Front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Right_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public static void brakeMotors(){
        Right_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Left_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Right_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Left_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public static DcMotor getRightFront(){
        return Right_Front;
    }
    public static DcMotor getLeftFront(){
        return Left_Front;
    }
    public static DcMotor getRightBack(){
        return Right_Back;
    }
    public static DcMotor getLeftBack(){
        return Left_Back;
    }
/*    public static double getRightFrontEncoder(){
        return -Right_Front.getCurrentPosition();
    }
    public static double getLeftFrontEncoder(){
        return Left_Front.getCurrentPosition();
    }
    public static double getRightBackEncoder(){
        return -Right_Back.getCurrentPosition();
    }
    public static double getLeftBackEncoder(){
        return Left_Back.getCurrentPosition();
    }
    public static double getRightBackEncoder(){
        return Right_Back.getCurrentPosition();
    }*/
    //new bot
    public static double getRightFrontEncoder(){
        return Right_Front.getCurrentPosition();
    }
    public static double getLeftFrontEncoder(){
        return -Left_Front.getCurrentPosition();
    }
    public static double getLeftBackEncoder(){
        return Left_Back.getCurrentPosition();
    }
    public static double getRightBackEncoder(){
        return -Right_Back.getCurrentPosition();
    }
}