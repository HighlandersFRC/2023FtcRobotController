package org.firstinspires.ftc.teamcode.Commands;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.PID;
public class strafeRight extends Command {
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
    private final AHRS navX;


    public strafeRight(HardwareMap hardwareMap, double Speed, double Distance){
        this.speed = Speed;
        this.distance = Distance * 1.225;
        PID.setSetPoint(0);
        DriveTrain.initialize(hardwareMap);
        navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), com.kauailabs.navx.ftc.AHRS.DeviceDataType.kProcessedData);
        navX.zeroYaw();
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
        PID.setSetPoint(navX.getYaw());
    }
    public void execute() {
        backRight = DriveTrain.getRightBackEncoder();
        backLeft = DriveTrain.getLeftBackEncoder();
        frontLeft = DriveTrain.getLeftFrontEncoder();
        frontRight  = DriveTrain.getRightFrontEncoder();
        avgEncoder = (backRight + frontLeft + frontRight + backLeft) / 4;
        DrivePID.updatePID(avgEncoder);
        PID.updatePID(navX.getYaw());
        double deviation = PID.getResult();
        currentPos = navX.getYaw();
        double RightFrontPower = (-speed - deviation);
        double LeftFrontPower = (-speed + deviation);
        double RightBackPower = (speed - deviation);
        double LeftBackPower = (-speed + deviation);
        DriveTrain.Drive(RightFrontPower, -LeftFrontPower, RightBackPower, LeftBackPower);
    }
    public void end() {
        DriveTrain.Drive(0, 0, 0, 0);
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