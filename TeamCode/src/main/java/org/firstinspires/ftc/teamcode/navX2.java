package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.hardware.I2cDevice;

public class navX2 {
    private AHRS navx_device;
    I2cDevice navx = hardwareMap.i2cDevice.get("NavX");
    navx_device = AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navx"),
    AHRS.DeviceDataType.kProcessedData,
    NAVX_DEVICE_UPDATE_RATE_HZ);
    AHRS ahrs = new AHRS();
    navx_device.getYaw()
    private AHRS hardwareMap(String deviceName) {
        return null;
    }
 AHRS getAhrs() {
        return ahrs;
    }
    I2cDevice getNavx() {
        return navx;
    }
    telemetry.addData("Yaw", navx_device.getYaw());

}
