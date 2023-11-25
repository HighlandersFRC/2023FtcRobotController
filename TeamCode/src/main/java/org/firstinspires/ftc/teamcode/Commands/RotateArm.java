package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.PID;
public class RotateArm extends Command {
    public double targetPosition;
    public DcMotor Arm_Motor;
    PID ArmPID = new PID(0.001, 0.0, 0.0);

    public RotateArm(HardwareMap hardwareMap, double targetPosition) {
        Arm_Motor = hardwareMap.dcMotor.get("Arm_Motor");
        ArmPID.setSetPoint(targetPosition);
        targetPosition = this.targetPosition;
    }

    public void start() {

    }

    public void execute() {
        ArmPID.updatePID(Arm_Motor.getCurrentPosition());
        Arm_Motor.setPower(-ArmPID.getResult());
    }

    public void end() {
        Arm_Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public boolean isFinished() {
        if (!(ArmPID.getError() == 0)) {
            if ((Math.abs(ArmPID.getError())) <= 100) {
                return true;
            }
        }
        return false;
    }
}

