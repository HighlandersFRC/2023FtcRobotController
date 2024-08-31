package org.firstinspires.ftc.teamcode.Commands;

public class TestCommand3 implements Command{
    @Override
    public void start() {
        System.out.println("ThirdStart");
    }

    @Override
    public void execute() {
        System.out.println("ThirdExecute");
    }

    @Override
    public void end() {
        System.out.println("ThirdEnd");
    }

    @Override
    public boolean isFinished() {
        return false;
    }


    public String getSubsystem() {
        return "";
    }
}
