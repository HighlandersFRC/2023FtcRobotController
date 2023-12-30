package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Commands.DeployIntake;

public class IntakeMover extends Subsystems{
    String name = "IntakeMover";
    Command DefaultCommand = new DeployIntake(hardwareMap, "Deploy");

}
