package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ElevatorSubsystem extends Subsystem{
    public static DcMotor ElevatorL;
    public static DcMotor ElevatorR;

    public static String name = "Elevators";

    public ElevatorSubsystem(String name) {
        super();
    }

    public static void initialize(HardwareMap hardwareMap){
        ElevatorL = hardwareMap.dcMotor.get("Elevator_Left");
        ElevatorR = hardwareMap.dcMotor.get("Elevator_Right");

        ElevatorL.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public static void resetEncoders(){
        ElevatorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ElevatorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ElevatorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ElevatorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public static double getArmLPosition(){
        return ElevatorL.getCurrentPosition();
    }
    public static double getArmRPosition(){
        return ElevatorR.getCurrentPosition();
    }
    public static void moveElevatorsL(double power){
        ElevatorL.setPower(power);
    }
    public static void moveElevatorsR(double power){
        ElevatorR.setPower(power);
    }
    public static void brakeMotors(){
        ElevatorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ElevatorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}