package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Commands.Elevator;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.PID;

public class Elevators extends Subsystems{
    public static DcMotor ArmL;
    public static DcMotor ArmR;

    public static String name = "Elevators";
    public static Command DefaultCommand = new org.firstinspires.ftc.teamcode.Commands.Elevators(hardwareMap, Constants.lastSetElevatorPosition);
    public static PID  PID1 = new PID(0.003, 0, 0.001);
    public static PID PID2 = new PID(0.003, 0, 0.001);

    public static void moveElevatorsUsingPID(HardwareMap hardwareMap, double targetPosition){
        PID1.setMaxOutput(1);
        PID1.setMinOutput(-1);
        PID2.setMaxOutput(1);
        PID2.setMinOutput(-1);

        PID1.setSetPoint(targetPosition);
        PID2.setSetPoint(-targetPosition);

        ArmL = hardwareMap.dcMotor.get("Arm_Left");
        ArmR = hardwareMap.dcMotor.get("Arm_Right");

        PID1.updatePID(ArmL.getCurrentPosition());
        PID2.updatePID(ArmR.getCurrentPosition());

        ArmL.setPower(PID1.getResult());
        ArmR.setPower(PID2.getResult());
    }
    public static double getArmLPosition(){
        return ArmL.getCurrentPosition();
    }
    public static double getArmRPosition(){
        return ArmR.getCurrentPosition();
    }
    public static void moveElevatorsUsingPower(HardwareMap hardwareMap, double power){
        ArmL.setPower(power);
        ArmR.setPower(-power);
    }
}
