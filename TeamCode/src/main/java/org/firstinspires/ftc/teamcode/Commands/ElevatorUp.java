package org.firstinspires.ftc.teamcode.Commands;


import org.firstinspires.ftc.teamcode.Subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.PID;

public class ElevatorUp implements Command {


    public String getSubsystem() {
        return "Elevator";
    }
    org.firstinspires.ftc.teamcode.Tools.PID PID = new PID(0.1, 0, 0);

    public void start(){
        PID.setSetPoint(Constants.ElevatorsUpPosition);
    }
    public void execute(){
        PID.updatePID((ElevatorSubsystem.getArmLPosition() + ElevatorSubsystem.getArmRPosition()) / 2);
    }
    public void end(){
        ElevatorSubsystem.moveElevatorsR(0);
        ElevatorSubsystem.moveElevatorsL(0);
    }

    public boolean isFinished() {
        if (ElevatorSubsystem.getArmLPosition() >= Constants.ElevatorsUpPosition + 15||  ElevatorSubsystem.getArmRPosition() >= Constants.ElevatorsUpPosition - 15) {
            return true;
        }
        return false;
    }
}