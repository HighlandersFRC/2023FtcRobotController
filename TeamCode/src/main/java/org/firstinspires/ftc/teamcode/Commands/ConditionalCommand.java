package org.firstinspires.ftc.teamcode.Commands;

public class ConditionalCommand implements Command {

    private Command onTrue;
    private Command onFalse;
    private Command commandToRun;
    private boolean condition;

    public ConditionalCommand(boolean condition, Command onTrue, Command onFalse) {
        this.condition = condition;
        this.onTrue = onTrue;
        this.onFalse = onFalse;
        if (condition){
            commandToRun = onTrue;
        }else{
            commandToRun = onFalse;
        }
    }

    @Override
    public void start() {
        if (condition) {
            commandToRun = onTrue;
        } else {
            commandToRun = onFalse;
        }

        if (commandToRun != null) {
            commandToRun.start();
        }
    }

    @Override
    public void execute() throws InterruptedException {
        if (commandToRun != null) {
            commandToRun.execute();
        }
    }

    @Override
    public void end() {
        if (commandToRun != null) {
            commandToRun.end();
        }
    }

    @Override
    public boolean isFinished() {
        return commandToRun != null && commandToRun.isFinished();
    }

    public String getSubsystem() {
        if (commandToRun != null) {
            return "hi";
        }
        return "";
    }
}
