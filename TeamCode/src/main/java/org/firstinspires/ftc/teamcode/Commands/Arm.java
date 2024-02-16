package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Commands.OldCommands.RotateArm;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystems;
import org.firstinspires.ftc.teamcode.Tools.PID;

public class Arm extends Command {
    double targetPosition;
    public static String Subsystem = "Arm";
/*    PID PID = new PID(0.003, 0.0, 0.002);*/ //old pid

    PID PID = new PID (0.0015, 0.0, 0.0018); //reference to new pid(check gear ratio) may need d value






    public Arm(HardwareMap hardwareMap, double targetPosition){
        org.firstinspires.ftc.teamcode.Subsystems.Arm.initialize(hardwareMap);
        this.targetPosition = targetPosition;
    }

    public String getSubsystem() {
        return "Arm";
    }

    public void start(){
        PID.setSetPoint(targetPosition);
        PID.setMaxOutput(0.8);
        PID.setMinOutput(-0.8);
        PID.setContinuous(false);
    }
    public void execute(){
        PID.updatePID(org.firstinspires.ftc.teamcode.Subsystems.Arm.getArmEncoder());
        org.firstinspires.ftc.teamcode.Subsystems.Arm.rotateArm(PID.getResult());
    }
    public void end(){
        org.firstinspires.ftc.teamcode.Subsystems.Arm.brakeMotors();
    }

    public boolean isFinished() {
        if (!(PID.getError() == 0)) {
            if ((Math.abs(PID.getError())) <= 100) {
                org.firstinspires.ftc.teamcode.Subsystems.Arm.brakeMotors();
                return true;
            }
        }
        return false;
    }
}
//s