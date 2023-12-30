package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Commands.Drive;

public class DriveTrain extends Subsystems{
    String name = "DriveTrain";
    Command DefaultCommand = new Drive(hardwareMap, 0, 0);

    public static DcMotor Left_Front;
    public static DcMotor Right_Front;
    public static DcMotor Left_Back;
    public static DcMotor Right_Back;
    public static void Drive(double RightFrontPower, double LeftFrontPower, double RightBackPower, double LeftBackPower){
        Right_Front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Left_Front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Right_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);

        Right_Front.setPower(RightFrontPower);
        Right_Back.setPower(RightBackPower);
        Left_Front.setPower(LeftFrontPower);
        Left_Back.setPower(LeftBackPower);

        if (RightBackPower == 0 && RightFrontPower == 0 && LeftFrontPower == 0 && LeftBackPower == 0){
            Left_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Left_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Right_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Right_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }
    public static DcMotor getRightFront(){
        Right_Front = hardwareMap.dcMotor.get("Right_Front");
        return Right_Front;
    }
    public static DcMotor getLeftFront(){
        Left_Front = hardwareMap.dcMotor.get("Left_Front");
        return Left_Front;
    }
    public static DcMotor getRightBack(){
        Right_Back = hardwareMap.dcMotor.get("Right_Back");
        return Right_Back;
    }
    public static DcMotor getLeftBack(){
        Left_Back = hardwareMap.dcMotor.get("Left_Back");
        return Left_Back;
    }
    public static double getRightFrontEncoder(){
        return Right_Front.getCurrentPosition();
    }
    public static double getLeftFrontEncoder(){
        return Left_Front.getCurrentPosition();
    }
    public static double getRightBackEncoder(){
        return Right_Back.getCurrentPosition();
    }
    public static double getLeftBackEncoder(){
        return Left_Back.getCurrentPosition();
    }
}