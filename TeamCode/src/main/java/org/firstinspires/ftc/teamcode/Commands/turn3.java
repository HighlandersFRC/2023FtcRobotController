package org.firstinspires.ftc.teamcode.Commands;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;


import org.firstinspires.ftc.teamcode.PID;

public class turn3 extends Command{
    PID PID = new PID(0.06, 0.0001, 0.0);
    public DcMotor Left_Back;
    public DcMotor Right_Back;
    public DcMotor Left_Front;
    public DcMotor Right_Front;
    public double targetAngle;
    public IMU imu;
    public AHRS navX;
    public double currentPos;
    public double PIDOutput;
    public turn3(HardwareMap hardwareMap, double targetAngle){
        this.targetAngle = -targetAngle;
        PID.setSetPoint(-targetAngle);
        Left_Front = hardwareMap.dcMotor.get("Left_Front");
        Right_Front = hardwareMap.dcMotor.get("Right_Front");
        Left_Back = hardwareMap.dcMotor.get("Left_Back");
        Right_Back = hardwareMap.dcMotor.get("Right_Back");
        imu = hardwareMap.get(IMU.class, "imu");
        navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), com.kauailabs.navx.ftc.AHRS.DeviceDataType.kProcessedData);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
        /*        Left_Back.setDirection(DcMotorSimple.Direction.REVERSE);*/
        PID.setMaxInput(180);
        PID.setMinInput(-180);
        PID.setContinuous(true);
        PID.setMinOutput(-1);
        PID.setMaxOutput(1);
        navX.zeroYaw();
        System.out.println("zero yaw");
    }
    public void start() {

    }
    public void execute() {
        /*   currentPos = navX.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);*/
        currentPos = navX.getYaw();

        double power = PID.updatePID(-navX.getYaw());
        this.PIDOutput = power;
        System.out.println(power + "  " + PID.getError());
        System.out.println(navX.getYaw()+" yaw");

        Right_Front.setPower(-power);
        Left_Front.setPower(power);
        Left_Back.setPower(power);
        Right_Back.setPower(-power);
    }

    public void end() {
        Left_Front.setPower(0);
        Left_Back.setPower(0);
        Right_Front.setPower(0);
        Right_Back.setPower(0);
    }

    public boolean isFinished() {
        if (!(PID.getError() == 0)) {
            return (Math.abs(PID.getError())) < .5;
        }
        return false;
    }
}