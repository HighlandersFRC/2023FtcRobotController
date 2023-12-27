package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class RotateLinearOp extends LinearOpMode {
    private DcMotor Left_Back;
    private DcMotor Right_Back;
    private DcMotor Left_Front;
    private DcMotor Right_Front;
    private IMU imu;
    double angle;
    double state;
    double integralSum = 0;
    double Kp = 1;
    double Ki = 0.5;
    double Kd = 0.5;
    ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;
    double referenceAngle = Math.toRadians(90);
    public double angleWrap(double radians){
        while (radians > Math.PI){
            radians -= 2 * Math.PI;
        }
        while (radians < -Math.PI){
            radians += 2 * Math.PI;
        }
        return radians;
    }

    public double PIDControl(double reference, double state){
        this.angle = reference;
        this.state = state;

        double error = reference - state;
        integralSum += error + timer.seconds();
        double derivative = (error - lastError) / timer.seconds();
        lastError = error;

        timer.reset();

        double output = (error + Kp) + (derivative + Kd) + (integralSum + Ki);
        return output;
    }
    @Override
    public void runOpMode() throws InterruptedException {


        DcMotor Left_Front = hardwareMap.dcMotor.get("Left_Front");
        DcMotor Left_Back = hardwareMap.dcMotor.get("Left_Back");
        DcMotor Right_Front = hardwareMap.dcMotor.get("Right_Front");
        DcMotor Right_Back = hardwareMap.dcMotor.get("Right_Back");

        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);

        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));

        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double power = PIDControl(referenceAngle, imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
            Left_Front.setPower(power);
            Left_Back.setPower(power);
            Right_Front.setPower(-power);
            Right_Back.setPower(-power);

            if (Math.toRadians(referenceAngle) >= referenceAngle - Math.toRadians(10) || Math.toRadians(referenceAngle) >= referenceAngle + Math.toRadians(10) || Math.toRadians(referenceAngle) <= referenceAngle - Math.toRadians(10) || Math.toRadians(referenceAngle) <= referenceAngle + Math.toRadians(10)){
                Left_Front.setPower(0);
                Left_Back.setPower(0);
                Right_Front.setPower(0);
                Right_Back.setPower(0);
            }


            telemetry.addData("parameters", parameters);
            telemetry.addData("IMU", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));


            telemetry.addData("time", time);
            telemetry.update();
        }
    }
}