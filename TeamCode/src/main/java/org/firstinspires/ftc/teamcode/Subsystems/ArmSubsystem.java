package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Tools.Constants;

public class ArmSubsystem extends Subsystem  {
    public static DcMotor armMotor;
    public static AnalogInput armEncoder;

    public ArmSubsystem(String name) {
        super();
    }
    public static void initialize(HardwareMap hardwareMap) {
        armMotor = hardwareMap.dcMotor.get("armMotor");
        armEncoder = hardwareMap.analogInput.get("absEncoder");

        Constants.armOffset = -Constants.getOffsetFromVoltage(Constants.absoluteArmZero - armEncoder.getVoltage());

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public static void start(double power){
        armMotor.setPower(power);
    }
    public static void stop(){
        armMotor.setPower(0);
    }
    public static double getArmEncoder() {
        return (armMotor.getCurrentPosition() - Constants.armOffset);
    }
    public static double getOffset(){
        return Constants.armOffset;
    }
    public static double getVoltage(){
        return armEncoder.getVoltage();
    }
    public static double getRawPosition(){
        return armMotor.getCurrentPosition();
    }
    public static void brakeMotors(){
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
