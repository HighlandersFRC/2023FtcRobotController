package org.firstinspires.ftc.teamcode.Commands;

public class TestCommand2 implements Command{
    @Override
    public void start() {
        System.out.println("SecondStart");
    }

    @Override
    public void execute() {
        System.out.println("SecondExecute");
    }

    @Override
    public void end() {
        System.out.println("SecondEnd");
    }

    @Override
    public boolean isFinished() {
        return true;
    }


    public String getSubsystem() {
        return "";
    }
}
