package org.firstinspires.ftc.teamcode.Commands;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.PID;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
public class Drive extends Command {
    org.firstinspires.ftc.teamcode.Tools.
            PID PID = new PID(0.03, 0.0, 0.0);
    org.firstinspires.ftc.teamcode.Tools.PID DrivePID = new PID(0.03, 0.0, 0.0);

    public HardwareMap hardwareMap;
    public IMU imu;
    public AHRS navX;
    public double currentPos;
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
        DriveTrain.initialize(hardwareMap);
        imu = hardwareMap.get(IMU.class, "imu");
        navX = AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), AHRS.DeviceDataType.kProcessedData);
        this.hardwareMap = hardwareMap;
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
    }
    public String getSubsystem() {
        return "DriveTrain";
    }
    public void start() {
        targetPos = distance * Constants.motorTicksPerMeter;
        PID.setSetPoint(0);
        DrivePID.setSetPoint(targetPos);
        PID.setMaxInput(180);
        PID.setMinInput(-180);
        PID.setContinuous(true);
        PID.setMinOutput(-0.75);
        PID.setMaxOutput(.75);
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
        currentPos = navX.getYaw();
        PID.updatePID(currentPos);
        /*currentPos = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);*/

        double correction = PID.getResult();

        double RightFrontPower = (-speed + correction);
        double LeftFrontPower = (-speed + correction);
        double RightBackPower = (-speed + correction);
        double LeftBackPower = (-speed + correction);

        DriveTrain.Drive(RightFrontPower, LeftFrontPower, RightBackPower, LeftBackPower);
    }
    public void end() {
        DriveTrain.Drive(0, 0, 0, 0);
    }

    public boolean isFinished() {
        return Math.abs(avgEncoder) - 10 >= Math.abs(targetPos);
    }
}