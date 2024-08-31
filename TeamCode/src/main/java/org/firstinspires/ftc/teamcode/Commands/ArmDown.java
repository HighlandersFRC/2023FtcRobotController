package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.PID;

public class ArmDown implements Command {
    public static String Subsystem = "Arm";

    PID PID = new PID (0.0015, 0.0, 0.0018);

    public String getSubsystem() {
        return "Arm";
    }

    public void start(){
        PID.setSetPoint(Constants.ArmDownPosition);
        PID.setMaxOutput(0.8);
        PID.setMinOutput(-0.8);
        PID.setContinuous(false);
    }
    public void execute(){
        PID.updatePID(ArmSubsystem.getArmEncoder());
        ArmSubsystem.start(PID.getResult());
    }
    public void end(){
        ArmSubsystem.stop();
    }

    public boolean isFinished() {
        if (!(PID.getError() == 0)) {
            if ((Math.abs(PID.getError())) <= 100) {
                ArmSubsystem.brakeMotors();
                return true;
            }
        }
        return false;
    }
}