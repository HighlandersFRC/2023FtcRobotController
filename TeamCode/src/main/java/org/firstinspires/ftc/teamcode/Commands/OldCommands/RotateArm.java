package org.firstinspires.ftc.teamcode.Commands.OldCommands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;

public class RotateArm extends Command {
    public double targetPosition;

    public RotateArm(HardwareMap hardwareMap, double targetPosition) {
        Arm.initialize(hardwareMap);
        this.targetPosition = targetPosition;
    }

    public void start() {
            }
    public String getSubsystem() {
        return "OldArm";
    }

    public void execute() {
        Arm.rotateArm(targetPosition);
    }

    public void end() {
        Arm.brakeMotors();
    }

    public boolean isFinished() {
        return false;
    }
}

