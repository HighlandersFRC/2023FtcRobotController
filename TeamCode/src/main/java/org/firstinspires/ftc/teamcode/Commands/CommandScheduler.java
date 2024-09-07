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
        // Use a new list to collect commands to remove
        List<Command> finishedCommands = new ArrayList<>();

        // Iterate over a copy of scheduledCommands to avoid concurrent modification
        for (Command command : new ArrayList<>(scheduledCommands)) {
            if (command.isFinished()) {
                command.end();
                finishedCommands.add(command);  // Collect finished commands for removal
                RobotLog.d("Command Finished and Ended: " + command.getClass().getSimpleName());
            } else {
                command.execute();
            }
        }

        // Remove finished commands after iteration
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
