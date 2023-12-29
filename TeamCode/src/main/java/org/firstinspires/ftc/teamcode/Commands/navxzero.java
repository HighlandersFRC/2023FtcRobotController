package org.firstinspires.ftc.teamcode.Commands;


import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class navxzero extends Command{
    private AHRS navX;
    public navxzero(HardwareMap hardwareMap){
        navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), com.kauailabs.navx.ftc.AHRS.DeviceDataType.kProcessedData);
    }

    public void start() {
    navX.zeroYaw();
    }

    public void execute() {

    }

    public void end() {

    }

    public boolean isFinished() {
        return true;
    }
}
