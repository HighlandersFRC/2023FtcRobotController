package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.RobotLog;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandScheduler {
    private static CommandScheduler instance;
    private static List<Command> scheduledCommands = new ArrayList<>();

    public static CommandScheduler getInstance() {
        if (instance == null) {
            instance = new CommandScheduler();
        }
        return instance;
    }

    public static void add(CommandScheduler scheduler, Command... commands) {
        scheduledCommands.addAll(Arrays.asList(commands));
    }

    public void schedule(Command command) throws JSONException {
        command.start();
        scheduledCommands.add(command);
        RobotLog.d("Command Scheduled: " + command.getClass().getSimpleName());
    }

    public void run() throws InterruptedException, JSONException {
        List<Command> finishedCommands = new ArrayList<>();
        for (Command command : new ArrayList<>(scheduledCommands)) {
            if (command.isFinished()) {
                command.end();
                finishedCommands.add(command);
                RobotLog.d("Command Finished and Ended: " + command.getClass().getSimpleName());
            } else {
                command.execute();
            }
        }
        scheduledCommands.removeAll(finishedCommands);
    }

    public void cancel(Command command) {
        command.end();
        scheduledCommands.remove(command);
        RobotLog.d("Command Cancelled: " + command.getClass().getSimpleName());
    }

    public void cancelAll() {
        for (Command command : new ArrayList<>(scheduledCommands)) {
            command.end();
            RobotLog.d("Command Cancelled: " + command.getClass().getSimpleName());
        }
        scheduledCommands.clear();
    }
}
