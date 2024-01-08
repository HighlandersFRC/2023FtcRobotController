package org.firstinspires.ftc.teamcode.Commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParallelCommandGroup extends Command {
    List<Command> commands = new ArrayList<>();

    Scheduler scheduler;
    public String getSubsystem() {
        return "ipfaujas;ldkfjaoidlfjkaepifjasdfpueowakdjaipfs[asasdfasdf asdf asd;flkasj goirejfdlvmfb soeuhijsafdj akjds;lkfjas;ieol jdlkf asljfkdslflkas;djf alksjdfl;kasjd;fl";
    }
    public ParallelCommandGroup(Scheduler scheduler, Command... commands) {
        this.scheduler = scheduler;
        Collections.addAll(this.commands, commands);
    }
    public void start() {
        for (int i = 0; i < commands.size(); i++){
            scheduler.add(commands.get(i));
        }
    }

    public void execute() {
    }


    public void end() {

    }

    public boolean isFinished() {
        for (int i = 0; i < commands.size(); i++){
            if(commands.get(i).isFinished() == false) {
                return false;
            }
        }
        return true;
    }
}