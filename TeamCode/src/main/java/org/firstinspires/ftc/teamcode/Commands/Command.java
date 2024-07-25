package org.firstinspires.ftc.teamcode.Commands;

public interface Command {
    void start();
    void execute() throws InterruptedException;
    void end();
    boolean isFinished();
}
