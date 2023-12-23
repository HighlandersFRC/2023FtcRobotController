package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Subsystems;

import java.util.ArrayList;

public abstract class Command {
    ArrayList<Subsystems> subsystems = new ArrayList<>();
    public boolean commandCompleted = false;
    public abstract void start();
    public abstract void execute();
    public abstract void end();
    public abstract boolean isFinished();
}
