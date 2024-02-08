package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
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
    public strafe(HardwareMap hardwareMap, double Speed, double Distance){
        this.speed = Speed;
        this.distance = Distance * 1.225;
        PID.setSetPoint(0);
        DriveTrain.initialize(hardwareMap);
        Peripherals.initialize(hardwareMap);
        //imu.resetYaw();
    }

    public String getSubsystem() {
        return "DriveTrain";
    }

    public void start() {
        targetPos = distance * Constants.motorTicksPerMeter;
        DrivePID.setSetPoint(targetPos);
        PID.setMaxInput(180);
        PID.setMinInput(-180);
        PID.setMinOutput(-0.2);
        PID.setMaxOutput(0.2);
        PID.setSetPoint(0);
        Peripherals.resetYaw();
        DriveTrain.resetEncoders();
    }
    public void execute() {
        backRight = DriveTrain.getRightBackEncoder();
        backLeft = DriveTrain.getLeftBackEncoder();
        frontLeft = DriveTrain.getLeftFrontEncoder();
        frontRight  = DriveTrain.getRightFrontEncoder();
        avgEncoder = (backRight + frontLeft + frontRight + backLeft) / 4;
        DrivePID.updatePID(avgEncoder);
        currentPos = -Peripherals.getYaw();
        PID.updatePID(currentPos);

        double correction = PID.getResult();
        double RightFrontPower = (-speed - correction);
        double LeftFrontPower = (-speed + correction);
        double RightBackPower = (-speed - correction);
        double LeftBackPower = (-speed + correction);

        DriveTrain.Drive(RightFrontPower, -LeftFrontPower, -RightBackPower, LeftBackPower);
        System.out.println(backRight+"RightbackEncoder");
        System.out.println(frontLeft+"leftfront");
        System.out.println(frontRight+"rightBack");
        System.out.println(backLeft+"LeftBack");

    }
    public void end() {
        DriveTrain.Drive(0, 0, 0, 0);
        DriveTrain.brakeMotors();
    }

    public boolean isFinished() {
        if (Math.abs(backRight) - 5 >= Math.abs(targetPos)) {
            return true;
        }
        if (Math.abs(-backLeft) - 5 >= Math.abs(targetPos)) {
            return true;
        }
        if (Math.abs(frontLeft) - 5 >= Math.abs(targetPos)) {
            return true;
        }
        return Math.abs(-frontRight) - 5 >= Math.abs(targetPos);
    }
}