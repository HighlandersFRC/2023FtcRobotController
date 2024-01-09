package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Commands.MainIntake;

public class Intake extends Subsystems{
    public static String name = "Intake";
    public static AnalogInput IntakeEncoder;
    Command DefaultCommand = new MainIntake(hardwareMap, 0, 0);

    public static DcMotor Intake;

    public static void initialize(HardwareMap hardwareMap){
        Intake = hardwareMap.dcMotor.get("Right_Intake");
        IntakeEncoder = hardwareMap.analogInput.get("absEncoder");
    }
    public static void moveMotor(double Power){
        Intake.setPower(-Power);
    }
}
