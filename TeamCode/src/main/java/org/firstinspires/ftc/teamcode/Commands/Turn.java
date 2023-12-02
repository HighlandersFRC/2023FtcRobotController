package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.PID1;

public class Turn extends Command{
    PID1 PID = new PID1(0.07, 0.0, 0.0);
    public DcMotor Left_Back;
    public DcMotor Right_Back;
    public DcMotor Left_Front;
    public DcMotor Right_Front;
    public double targetAngle;
    public IMU imu;
    public double currentPos;
    public double PIDOutput;
    public Turn(HardwareMap hardwareMap, double targetAngle){
        this.targetAngle = targetAngle;
        PID.setSetPoint(targetAngle);
        Left_Front = hardwareMap.dcMotor.get("Left_Front");
        Right_Front = hardwareMap.dcMotor.get("Right_Front");
        Left_Back = hardwareMap.dcMotor.get("Left_Back");
        Right_Back = hardwareMap.dcMotor.get("Right_Back");
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);
    }
    public void start() {
        PID.setMaxInput(180);
        PID.setMinInput(-180);
        PID.setContinuous(true);
        PID.setMinOutput(-1);
        PID.setMaxOutput(1);
        imu.resetYaw();
 }
    public void execute() {
        currentPos = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        double power = PID.updatePID(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        this.PIDOutput = power;

        Right_Front.setPower(power);
        Left_Front.setPower(power);
        Left_Back.setPower(power);
        Right_Back.setPower(power);
    }

        public void end() {
            Left_Front.setPower(0);
            Left_Back.setPower(0);
            Right_Front.setPower(0);
            Right_Back.setPower(0);
    }

    public boolean isFinished() {
        if (PID.getResult() <= 0.1 && PIDOutput >= PID.getSetPoint()) {
            return true;
        }
        return false;
    }
}