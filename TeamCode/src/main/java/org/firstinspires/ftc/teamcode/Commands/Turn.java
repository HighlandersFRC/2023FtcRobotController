package org.firstinspires.ftc.teamcode.Commands;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.PID;

public class Turn extends Command{
    org.firstinspires.ftc.teamcode.Tools.PID PID = new PID(0.5, 0.0, 0.0);
    public String getSubsystem() {
        return "DriveTrain";
    }
    public double targetAngle;
    public double currentPos;
    public double PIDOutput;
    public Turn(HardwareMap hardwareMap, double targetAngle){
        this.targetAngle = targetAngle;
        PID.setSetPoint(targetAngle);
        DriveTrain.initialize(hardwareMap);
        PID.setMaxInput(180);
        PID.setMinInput(-180);
        PID.setMinOutput(-0.3);
        PID.setMaxOutput(0.3);
        PID.setContinuous(false);
        Peripherals.initialize(hardwareMap);
        Peripherals.resetYaw();
    }
    public void start() {

    }
    public void execute() {
        currentPos = Peripherals.getYaw();

        double power = PID.updatePID(-currentPos);
        this.PIDOutput = power;

        DriveTrain.Drive(power, -power, power, -power);
    }

    public void end() {
        DriveTrain.Drive(0, 0, 0, 0);
    }

    public boolean isFinished() {
        if (!(PID.getError() == 0)) {
            if (Math.abs(PID.getError()) < 2){
                return true;
            }
        }
        return false;
    }
}