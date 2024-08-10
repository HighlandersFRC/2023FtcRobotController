package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class arm extends Subsystems {
    String name = "arm";
    public static int targetPos;
    public static double armPower;
    public static DcMotor arm_Motor ;
    public static double pidSetPoint;

    public static void initialize(HardwareMap hardwareMap){
        arm_Motor = hardwareMap.dcMotor.get("arm_motor");
    }
    public static double getArmPos (){
        return arm_Motor.getCurrentPosition();
    }
    public static void PidSetPoint (double pidPoint){
        pidSetPoint = pidPoint;
    }

    public static void moveArm (int tPos){
        targetPos = tPos;
        PidSetPoint(targetPos);
    }
    public static void constantPowerArm (double armPow){
        armPower = armPow;
        arm_Motor.setPower(armPower);
    }
    public static void breakModeArm(){
        arm_Motor.setPower(0);
        arm_Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

}
