package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Commands.MainIntake;

public class Intake extends Subsystems{
    String name = "Intake";
    Command DefaultCommand = new MainIntake(hardwareMap, 0, 0);

    public DcMotor intake;
    public static void Intake(String DeployOrRetract){

    }
}
