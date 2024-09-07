package org.firstinspires.ftc.teamcode.Commands;

import org.json.JSONException;

public interface Command {
    void start() throws JSONException;
    void execute() throws InterruptedException, JSONException;
    void end();
    boolean isFinished();
}