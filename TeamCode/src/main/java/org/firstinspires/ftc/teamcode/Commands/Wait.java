package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.RobotLog;

public class Wait implements Command {
    private long waitTime;
    private long startTime;
    private long elapsedTime;
    private boolean isStarted;

    public Wait(long waitTime) {
        this.waitTime = waitTime;
        this.elapsedTime = 0;
        this.isStarted = false;
    }

    @Override
    public void start() {
        RobotLog.d("Wait Command Started: " + waitTime + "ms");
        startTime = System.currentTimeMillis();
        isStarted = true;
    }

    @Override
    public void execute() {
        if (isStarted) {
            elapsedTime += System.currentTimeMillis() - startTime;
            startTime = System.currentTimeMillis();
            RobotLog.d("Wait Command Executing: " + waitTime + "ms, Elapsed: " + elapsedTime + "ms");
        }
    }

    @Override
    public void end() {
        isStarted = false;
        RobotLog.d("Wait Command Ended: " + waitTime + "ms, Total Elapsed: " + elapsedTime + "ms");
    }

    @Override
    public boolean isFinished() {
        return elapsedTime >= waitTime;
    }


    public void reset() {
        elapsedTime = 0;
        isStarted = false;
    }
}
