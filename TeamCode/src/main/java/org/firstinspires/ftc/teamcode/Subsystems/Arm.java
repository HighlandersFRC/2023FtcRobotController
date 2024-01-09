package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Tools.Constants;

public class Arm extends Subsystems {
        public static String name = "Arm";
        public static DcMotor Arm_Motor;
        public static AnalogInput armEncoder;

        public static void rotateArm(double power) {
            Arm_Motor.setPower(-power);
        }
        public static void initialize(HardwareMap hardwareMap){
            Arm_Motor = hardwareMap.dcMotor.get("Arm_Motor");
            armEncoder = hardwareMap.analogInput.get("absEncoder");

            Constants.armOffset = -Constants.getOffsetFromVoltage(Constants.absoluteArmZero - armEncoder.getVoltage());

            Arm_Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        public static void brakeMotors(){
            /*Arm_Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);*/
        }
        public static double getArmEncoder() {
            return (Arm_Motor.getCurrentPosition() - Constants.armOffset);
        }
    }