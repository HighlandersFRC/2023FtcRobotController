package org.firstinspires.ftc.teamcode.Commands;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Tools.PID;

public class Turn extends Command{
    org.firstinspires.ftc.teamcode.Tools.PID PID = new PID(0.06, 0.0001, 0.0);
    public String getSubsystem() {
        return "DriveTrain";
    }
    public double targetAngle;
    public AHRS navX;
    public double currentPos;
    public double PIDOutput;
    public Turn(HardwareMap hardwareMap, double targetAngle){
        this.targetAngle = -targetAngle;
        PID.setSetPoint(-targetAngle);
        DriveTrain.initialize(hardwareMap);
        navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), com.kauailabs.navx.ftc.AHRS.DeviceDataType.kProcessedData);
        PID.setMaxInput(180);
        PID.setMinInput(-180);
        PID.setContinuous(true);
        PID.setMinOutput(-0.3);
        PID.setMaxOutput(0.3);
        navX.zeroYaw();
    }
    public void start() {

    }
    public void execute() {
        currentPos = navX.getYaw();

        double power = PID.updatePID(-navX.getYaw());
        this.PIDOutput = power;

        DriveTrain.Drive(power, power, power, power);
    }

    public void end() {
        DriveTrain.Drive(0, 0, 0, 0);
    }

    public boolean isFinished() {
        if (!(PID.getError() == 0)) {
            return (Math.abs(PID.getError())) < .5;
        }
        return false;
    }
}