package org.firstinspires.ftc.teamcode.Commands;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.PID;
public class turn2 extends Command{
    PID PID = new PID(0.07, 0.0, .8);
  /*  PID PID = new PID(0.1, 0.0001, 0.0);*/
    public DcMotor Left_Back;
    public DcMotor Right_Back;
    public DcMotor Left_Front;
    public DcMotor Right_Front;
    public IMU imu;
    public double targetAngle;

    public AHRS navX;
    public double currentPos;
    public double PIDOutput;
    public turn2(HardwareMap hardwareMap, double targetAngle){
        this.targetAngle = targetAngle;
        PID.setSetPoint(targetAngle);
        Left_Front = hardwareMap.dcMotor.get("Left_Front");
        Right_Front = hardwareMap.dcMotor.get("Right_Front");
        Left_Back = hardwareMap.dcMotor.get("Left_Back");
        Right_Back = hardwareMap.dcMotor.get("Right_Back");
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
        navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), com.kauailabs.navx.ftc.AHRS.DeviceDataType.kProcessedData);navX.zeroYaw();
    }
    public void start() {
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
        /*        Left_Back.setDirection(DcMotorSimple.Direction.REVERSE);*/
        PID.setMaxInput(179);
        PID.setMinInput(-179);
        PID.setContinuous(true);
        PID.setMinOutput(-.15);
        PID.setMaxOutput(.15);

    }
    public void execute() {
        navX.zeroYaw();
        currentPos = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        double power = PID.updatePID(currentPos);
        this.PIDOutput = power;
        System.out.println(power + "  " + PID.getError());
        System.out.println(navX.getYaw());

        Right_Front.setPower(-power);
        Left_Front.setPower(power);
        Left_Back.setPower(power);
        Right_Back.setPower(-power);

    }

    public void end() {
        navX.zeroYaw();
        Left_Front.setPower(0);
        Left_Back.setPower(0);
        Right_Front.setPower(0);
        Right_Back.setPower(0);

    }

    public boolean isFinished() {
        if (!(PID.getError() == 0)) {
            return (Math.abs(PID.getError())) < 5;
        }
        return false;
    }
}