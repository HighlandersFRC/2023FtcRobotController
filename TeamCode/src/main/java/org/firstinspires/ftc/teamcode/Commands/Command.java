package org.firstinspires.ftc.teamcode.Commands;

public abstract class Command {
    public boolean commandCompleted = false;
    public abstract void start();
<<<<<<< Updated upstream
    public abstract void execute();
=======
    public abstract boolean execute();
>>>>>>> Stashed changes
    public abstract void end();
    public abstract boolean isFinished();
}
