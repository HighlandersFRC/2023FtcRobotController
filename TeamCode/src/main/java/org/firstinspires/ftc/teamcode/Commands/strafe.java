package org.firstinspires.ftc.teamcode.Commands;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.PID;


public class strafe extends Command {
    org.firstinspires.ftc.teamcode.Tools.PID PID = new PID(0.03, 0.0, 0.015);
    org.firstinspires.ftc.teamcode.Tools.PID DrivePID = new PID(0.03, 0.0, 0.0);
    public double currentPos;
    public double speed;
    public double distance;
    public double targetPos;
    public double avgEncoder;
    public double backLeft;
    public double backRight;
    public double frontLeft;
    public double frontRight;
    PID ArmPID = new PID(0.001, 0, 0);

    public strafe(HardwareMap hardwareMap, double Speed, double Distance) {
        this.speed = Speed;
        this.distance = Distance * 1.225;
        DriveTrain.initialize(hardwareMap);
        Peripherals.initialize(hardwareMap);

        Peripherals.resetYaw();
        PID.setSetPoint(0);
    }

    public String getSubsystem() {
        return "DriveTrain";
    }

    public void start() {
        targetPos = distance * Constants.motorTicksPerMeter;
        DrivePID.setSetPoint(targetPos);
        PID.setMaxInput(180);
        PID.setMinInput(-180);
        PID.setContinuous(true);
        PID.setMinOutput(-1);
        PID.setMaxOutput(1);
        PID.setSetPoint(0);
        //currentPos = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

    }

    public void execute() {
        backRight = DriveTrain.getRightBackEncoder();
        backLeft = DriveTrain.getLeftBackEncoder();
        frontLeft = DriveTrain.getLeftFrontEncoder();
        frontRight = DriveTrain.getRightFrontEncoder();
        avgEncoder = (backRight + frontLeft + frontRight + backLeft) / 4;
        DrivePID.updatePID(avgEncoder);
        PID.updatePID(Peripherals.getYaw());

        double correction = PID.getResult();
        currentPos = Peripherals.getYaw();

        double RightFrontPower = (speed + correction);
        double LeftFrontPower = (-speed + correction);
        double RightBackPower = (speed + correction);
        double LeftBackPower = (-speed + correction);

        DriveTrain.Drive(RightFrontPower, LeftFrontPower, -RightBackPower, -LeftBackPower);
    }

    public void end() {
        DriveTrain.Drive(0, 0, 0, 0);

    }

    public boolean isFinished() {
        if (Math.abs(avgEncoder) - 5 >= Math.abs(targetPos)) {
            return true;
        }
        return false;
    }
}