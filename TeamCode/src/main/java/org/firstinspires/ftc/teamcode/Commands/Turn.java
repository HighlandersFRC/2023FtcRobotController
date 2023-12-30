package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

public class Turn extends Command{
    PID PID = new PID(0.06, 0.0001, 0.0);
    public DcMotor Left_Back;
    public DcMotor Right_Back;
    public DcMotor Left_Front;
    public DcMotor Right_Front;
    public double targetAngle;
    public IMU imu;
    public NavxMicroNavigationSensor navX;
    public double currentPos;
    public double PIDOutput;
    public Turn(HardwareMap hardwareMap, double targetAngle){
        this.targetAngle = targetAngle;
        PID.setSetPoint(targetAngle);
        Left_Front = DriveTrain.Left_Front;
        Right_Front = DriveTrain.Right_Front;
        Left_Back = DriveTrain.Left_Back;
        Right_Back = DriveTrain.Right_Back;
        imu = hardwareMap.get(IMU.class, "imu");
        navX = hardwareMap.get(NavxMicroNavigationSensor.class, "navX");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
    }
    public void start() {
        PID.setMaxInput(180);
        PID.setMinInput(-180);
        PID.setContinuous(true);
        PID.setMinOutput(-0.25);
        PID.setMaxOutput(0.25);
        imu.resetYaw();
 }
    public void execute() {
     /*   currentPos = navX.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);*/
        currentPos = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        double power = PID.updatePID(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        this.PIDOutput = power;
        DriveTrain.Drive(-power, power, -power, power);
    }

        public void end() {
            DriveTrain.Drive(0, 0, 0, 0);
    }

    public boolean isFinished() {
        if (!(PID.getError() == 0)) {
            if ((Math.abs(PID.getError())) < 2) {
                return true;
            }
        }
        return false;
    }
}