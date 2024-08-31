package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.PathingTool.DriveConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.PID;

public class Drive implements Command {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private PID rotationPID;
    private double speed;
    private double distance;
    private double targetTicks;
    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private ElapsedTime runtime = new ElapsedTime();
    private double initialHeading;
    private double targetHeading;
    private double currentHeading;

    public Drive(HardwareMap hardwareMap, Telemetry telemetry, double speed, double distance) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.speed = speed;
        this.distance = distance;
        this.rotationPID = new PID(0.03, 0.0, 0.0); // Adjust PID constants as needed
    }


    public String getSubsystem() {
        return "Drive";
    }

    @Override
    public void start() {
        // Initialize motors
        leftFront = hardwareMap.dcMotor.get("Left_Front");
        rightFront = hardwareMap.dcMotor.get("Right_Front");
        leftBack = hardwareMap.dcMotor.get("Left_Back");
        rightBack = hardwareMap.dcMotor.get("Right_Back");

        // Reset encoders
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set target position in ticks
        targetTicks = distance * DriveConstants.TicksPerInch;

        // Set PID setpoint and parameters
        rotationPID.setSetPoint(0); // Setpoint for rotation correction
        rotationPID.setMaxInput(180); // Adjust based on your needs
        rotationPID.setMinInput(-180); // Adjust based on your needs
        rotationPID.setContinuous(true);
        rotationPID.setMinOutput(-0.25); // Adjust based on your needs
        rotationPID.setMaxOutput(0.25); // Adjust based on your needs

        // Reset and initialize heading
        Peripherals.resetYaw();
        initialHeading = Peripherals.getYawDegrees();
        targetHeading = initialHeading;

        // Start timer
        runtime.reset();
    }

    @Override
    public void execute() {
        // Update current heading
        currentHeading = Peripherals.getYaw();

        // Calculate correction using PID
        double correction = rotationPID.updatePID(currentHeading);

        // Calculate motor powers with correction
        double leftFrontPower = speed - correction;
        double rightFrontPower = speed + correction;
        double leftBackPower = speed - correction;
        double rightBackPower = speed + correction;

        // Set motor powers
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftBack.setPower(leftBackPower);
        rightBack.setPower(rightBackPower);

        // Log telemetry for debugging
        telemetry.addData("Distance", distance);
        telemetry.addData("Current Position", (leftFront.getCurrentPosition() + rightFront.getCurrentPosition() +
                leftBack.getCurrentPosition() + rightBack.getCurrentPosition()) / 4);
        telemetry.addData("Correction", correction);
        telemetry.update();
    }

    @Override
    public void end() {

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Peripherals.resetYaw();
    }

    @Override
    public boolean isFinished() {
        // Check if average position of all motors reaches or exceeds target ticks
        return Math.abs((leftFront.getCurrentPosition() + rightFront.getCurrentPosition() +
                leftBack.getCurrentPosition() + rightBack.getCurrentPosition()) / 4) >= Math.abs(targetTicks);
    }
}
