package org.firstinspires.ftc.teamcode.Tools;

import com.kauailabs.navx.ftc.AHRS;

public class NavX {
    private double originalAngle;
    private double originalYaw;
    private double originalPitch;
    private AHRS imu;
    public void Navx(AHRS navx) {
        imu = navx;
        originalAngle = imu.getYaw();
        originalYaw = imu.getYaw();
    }

    public void Navx(AHRS navx, Double startAngle) {
        imu = navx;
        originalAngle = startAngle;
        originalYaw = imu.getYaw();
    }

    public double currentAngle() {
        return -(imu.getYaw() - originalAngle);
    }

    public double getRawAngle() {
        return -(imu.getYaw());
    }

    public double getRawPitch() {
        return imu.getPitch();
    }

    public double getRawYaw() {
        return imu.getYaw();
    }

    public double getRawRoll() {
        return imu.getRoll();
    }

    public double currentPitch() {
        return imu.getPitch() - originalPitch;
    }

    public double currentRoll() {
        return imu.getRoll();
    }

    public double currentYaw() {
        return -((imu.getYaw()) - originalYaw);
    }

    public boolean isMoving() {
        return imu.isMoving();
    }

    public double currentAccelerometerX() {
        return imu.getWorldLinearAccelX();
    }

    public double currentAccelerometerY() {
        return imu.getWorldLinearAccelY();
    }

    public double currentAccelerometerZ() {
        return imu.getWorldLinearAccelZ();
    }

    public boolean isOn() {
        return imu.isConnected();
    }

    public boolean isMagCalibrated() {
        return imu.isMagnetometerCalibrated();
    }

    public boolean isAutoCalibrating() {
        return imu.isCalibrating();
    }

    public boolean isMagInerference() {
        return imu.isMagneticDisturbance();
    }

    public void softResetAngle() {
        originalAngle = imu.getYaw();
    }

    public void setNavxAngle(double angle) {
        originalYaw = originalYaw + angle;
    }

    public void softResetYaw() {
        originalYaw = imu.getYaw();
    }

    public void softResetPitch(){
        originalPitch = imu.getPitch();
    }

    public double getAngleRate() {
        return (imu.getActualUpdateRate());
    }

}

