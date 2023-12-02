package org.firstinspires.ftc.teamcode.Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandGroup extends Command {
    List<Command> commands = new ArrayList<>();

    Scheduler scheduler;

    public CommandGroup(Scheduler scheduler, Command... commands) {
        this.scheduler = scheduler;
        for (Command c : commands) {
            this.commands.add(c);
        }
    }

    public void start() {
        scheduler.add(commands.get(0));
    }

<<<<<<< Updated upstream
    public void execute() {
=======
    public boolean execute() {
>>>>>>> Stashed changes
        if (commands.get(0).commandCompleted) {
            commands.remove(0);
            if (commands.size() > 0) {
                scheduler.add(commands.get(0));
            }


        }
        System.out.println(commands.size());
<<<<<<< Updated upstream
=======
        return false;
>>>>>>> Stashed changes
    }

    public void end() {
        
    }

    public boolean isFinished() {
        if (commands.size() == 0) {
            return true;
        }
        return false;
    }
}