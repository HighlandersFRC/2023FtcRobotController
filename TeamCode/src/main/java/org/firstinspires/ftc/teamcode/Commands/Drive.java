package org.firstinspires.ftc.teamcode.Commands;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.PID;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

public class Drive extends Command {
    org.firstinspires.ftc.teamcode.Tools.PID PID = new PID(0.015, 0.0, 0.0);
    org.firstinspires.ftc.teamcode.Tools.PID DrivePID = new PID(0.03, 0.0, 0.0);

    public DcMotor Left_Back;
    public DcMotor Right_Back;
    public DcMotor Left_Front;
    public DcMotor Right_Front;
    public HardwareMap hardwareMap;
    public double targetAngle;
    public IMU imu;
    public AHRS navX;
    public double currentPos;
    public double PIDOutput;
    public double speed;
    public double distance;
    public double targetPos;
    public double avgEncoder;
    public double backLeft;
    public double backRight;
    public double frontLeft;
    public double frontRight;

    public Drive(HardwareMap hardwareMap, double Speed, double Distance){
        this.speed = Speed;
        this.distance = Distance;
        PID.setSetPoint(0);
        Left_Front = DriveTrain.Left_Front;
        Right_Front = DriveTrain.Right_Front;
        Left_Back = DriveTrain.Left_Back;
        Right_Back = DriveTrain.Right_Back;
        imu = hardwareMap.get(IMU.class, "imu");
        navX = AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), AHRS.DeviceDataType.kProcessedData);
        this.hardwareMap = hardwareMap;
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
    }
    public void start() {
        DriveTrain.initializeMotors(hardwareMap);
        targetPos = distance * Constants.motorTicksPerRotation;
        DrivePID.setSetPoint(targetPos);
        PID.setMaxInput(180);
        PID.setMinInput(-180);
        PID.setContinuous(true);
        PID.setMinOutput(-0.25);
        PID.setMaxOutput(0.25);
        imu.resetYaw();
        navX.zeroYaw();
    }

    public void execute() {
        backRight = DriveTrain.getRightBackEncoder();
        backLeft = DriveTrain.getLeftBackEncoder();
        frontLeft = DriveTrain.getLeftFrontEncoder();
        frontRight  = DriveTrain.getRightFrontEncoder();
        avgEncoder = (backRight + frontLeft + frontRight + backLeft) / 4;
        DrivePID.updatePID(avgEncoder);
        /*currentPos = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);*/
        currentPos = Math.toDegrees(navX.getYaw());

        double deviation = PID.getResult();

        double RightFrontPower = (-speed - deviation);
        double LeftFrontPower = (-speed + deviation);
        double RightBackPower = (-speed - deviation);
        double LeftBackPower = (-speed + deviation);

        DriveTrain.Drive(hardwareMap, RightFrontPower, LeftFrontPower, RightBackPower, LeftBackPower);
    }
    public void end() {
        DriveTrain.Drive(hardwareMap, 0, 0, 0, 0);
    }

    public boolean isFinished() {
        if (Math.abs(avgEncoder) - 10 >= Math.abs(targetPos)) {
          return true;
        }
        return false;
    }
}