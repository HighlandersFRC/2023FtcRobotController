package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.RobotLog;

import org.json.JSONException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class SequentialCommandGroup implements Command {
    private Queue<Command> commands = new LinkedList<>();
    private Command currentCommand;

    public SequentialCommandGroup(Command... commands) {
        Collections.addAll(this.commands, commands);
    }

    @Override
    public void start() {
        if (!commands.isEmpty()) {
            currentCommand = commands.poll();
            currentCommand.start();
            RobotLog.d("Sequential Command Group Started with " + currentCommand.getClass().getSimpleName());
        }
    }

    @Override
    public void execute() throws InterruptedException, JSONException {
        if (currentCommand != null) {
            if (currentCommand.isFinished()) {
                currentCommand.end();
                if (!commands.isEmpty()) {
                    currentCommand = commands.poll();
                    currentCommand.start();
                    RobotLog.d("Sequential Command Group Next Command Started: " + currentCommand.getClass().getSimpleName());
                } else {
                    currentCommand = null;
                }
            } else {
                currentCommand.execute();
            }
        }
    }

    @Override
    public void end() {
        if (currentCommand != null) {
            currentCommand.end();
            RobotLog.d("Sequential Command Group Ended with " + currentCommand.getClass().getSimpleName());
        }
        for (Command command : commands) {
            command.end();
        }
    }

    @Override
    public boolean isFinished() {
        return currentCommand == null;
    }
}
