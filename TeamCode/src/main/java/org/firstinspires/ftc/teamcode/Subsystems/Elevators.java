package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.PID;

public class Elevators extends Subsystems{
    public static DcMotor ArmL;
    public static DcMotor ArmR;

    public static String name = "Elevators";
    public static PID  PID1 = new PID(0.003, 0, 0.001);
    public static PID PID2 = new PID(0.003, 0, 0.001);
    public static void initialize(HardwareMap hardwareMap){
        ArmL = hardwareMap.dcMotor.get("Arm_Left");
        ArmR = hardwareMap.dcMotor.get("Arm_Right");
    }

    public static void moveElevatorsUsingPID(double targetPosition){
        PID1.setMaxOutput(1);
        PID1.setMinOutput(-1);
        PID2.setMaxOutput(1);
        PID2.setMinOutput(-1);

        PID1.setSetPoint(targetPosition);
        PID2.setSetPoint(-targetPosition);

        PID1.updatePID(ArmL.getCurrentPosition());
        PID2.updatePID(ArmR.getCurrentPosition());

        ArmL.setPower(PID1.getResult());
        ArmR.setPower(PID2.getResult());
    }
    public static double getArmLPosition(){
        return ArmL.getCurrentPosition();
    }
    public static double getArmRPosition(){
        return 0;
    }
    public static void moveElevatorsUsingPower(double power){
        ArmL.setPower(power);
        ArmR.setPower(-power);
    }
}
