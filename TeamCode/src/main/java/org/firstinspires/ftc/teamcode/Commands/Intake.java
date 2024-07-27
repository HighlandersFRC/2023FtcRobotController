package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

public class Intake implements Command  {
    @Override
    public void start() {
        System.out.println("Intake started");
    }

    @Override
    public void execute() throws InterruptedException {
        System.out.println("Intake executing");
        IntakeSubsystem.start(1);
    }

    @Override
    public void end() {
        IntakeSubsystem.start(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
