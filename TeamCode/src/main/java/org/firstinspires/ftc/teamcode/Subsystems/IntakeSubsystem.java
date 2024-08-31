package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem extends Subsystem  {
    public static DcMotor intakeMotor;
    public IntakeSubsystem(String name) {
        super();
    }
    public static void initialize(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.dcMotor.get("intake");
    }
    public static void start(double power){
        intakeMotor.setPower(power);
    }
}
