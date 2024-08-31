package org.firstinspires.ftc.teamcode.Commands;

public class TestCommand implements Command{
    @Override
    public void start() {
        System.out.println("Start");
    }

    @Override
    public void execute() throws InterruptedException {
        System.out.println("Execute");
        Thread.sleep(30);
    }

    @Override
    public void end() {
        System.out.println("End");
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public String getSubsystem() {
        return "";
    }
}
