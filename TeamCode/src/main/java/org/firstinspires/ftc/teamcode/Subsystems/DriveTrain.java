package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveTrain extends Subsystems{
    String name = "DriveTrain";

    public static DcMotor left_motor;
    public static DcMotor right_motor;
    public static void Drive(double LeftMotorPower, double RightMotorPower){

        //new comp bot
        left_motor.setPower(LeftMotorPower);
        right_motor.setPower(RightMotorPower);

    }
    public static void initialize(HardwareMap hardwareMap){
        left_motor = hardwareMap.dcMotor.get("left_motor");
        right_motor = hardwareMap.dcMotor.get("right_motor");

        left_motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public static void resetEncoders(){
        left_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public static void brakeMotors(){
        left_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public static void floatMotors(){
        left_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        right_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public static DcMotor getRight(){
        return right_motor;
    }
    public static DcMotor getLeft(){
        return left_motor;
    }
    public static double getRightEncoder(){
        return right_motor.getCurrentPosition();
    }
    public static double getLeftEncoder(){
        return left_motor.getCurrentPosition();
    }
}