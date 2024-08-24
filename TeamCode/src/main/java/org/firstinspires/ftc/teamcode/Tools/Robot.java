package org.firstinspires.ftc.teamcode.Tools;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;

public class Robot {
    public static void initialize(HardwareMap hardwareMap) {
        Peripherals.initialize(hardwareMap);
        DriveSubsystem.initialize(hardwareMap);
        FieldOfMerit.initialize(hardwareMap);
    }
}
