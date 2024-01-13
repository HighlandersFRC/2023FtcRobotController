package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystems;
import org.firstinspires.ftc.teamcode.Tools.PID;

public class Arm extends Command {
    double targetPosition;
    public static String Subsystem = "Arm";
    PID PID = new PID(0.004, 0.0, 0.0);
    public Arm(HardwareMap hardwareMap, double targetPosition){
        org.firstinspires.ftc.teamcode.Subsystems.Arm.initialize(hardwareMap);
        this.targetPosition = targetPosition;
    }

    public String getSubsystem() {
        return "Arm";
    }

    public void start(){
        PID.setSetPoint(targetPosition);
    }
    public void execute(){
        PID.updatePID(org.firstinspires.ftc.teamcode.Subsystems.Arm.getArmEncoder());
        org.firstinspires.ftc.teamcode.Subsystems.Arm.rotateArm(PID.getResult());
    }
    public void end(){
    }

    public boolean isFinished() {
        if (!(PID.getError() == 0)) {
            if ((Math.abs(PID.getError())) <= 100) {
                return true;
            }
        }
        return false;
    }
}
