package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.I2cDevice;

public class navX2 {
    private AHRS navx_device;


    I2cDevice navx = hardwareMap.i2cDevice.get("NavX");

    AHRS ahrs = new AHRS();


    private AHRS hardwareMap(String deviceName) {
        return null;
    }

    public AHRS getAhrs() {
        return ahrs;
    }

    I2cDevice getNavx() {
        return navx;
    }
}
