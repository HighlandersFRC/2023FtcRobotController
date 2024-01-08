package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Subsystems;

public abstract class Command {
    public boolean commandCompleted = false;
    public abstract String getSubsystem();
    public abstract void start();
    public abstract void execute();
    public abstract void end();
    public abstract boolean isFinished();
}
