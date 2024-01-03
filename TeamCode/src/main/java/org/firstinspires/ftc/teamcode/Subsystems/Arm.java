package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.PID;

public class Arm extends Subsystems{
    String name = "Arm";
    public static DcMotor Arm;
    public static PID ArmPID = new PID(0.001, 0.0, 0.0);
public static void Arm1(HardwareMap hardwareMap,PID pid) {
        Arm = hardwareMap.dcMotor.get("Arm");
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pid.setPID(0.001, 0.0, 0.0);
        pid.setMaxOutput(1);
        pid.setMinInput(-1);
        pid.setSetPoint(Constants.armPlace);
        pid.setSetPoint(Constants.armIntake);
        Arm.setPower(pid.getResult());
        pid.updatePID(Arm.getCurrentPosition() - Constants.armOffset);
    }
}
