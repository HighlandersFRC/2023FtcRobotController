package org.firstinspires.ftc.teamcode.Tools;

public class Constants {
    public static double absoluteArmZero =0.306;
    public static double armOffset;
    public static double ArmUpPosition = 4000;
    public static double ElevatorsUpPosition = 2000;
    public static double ElevatorsDownPosition = 200;
    public static double ArmDownPosition = 200;
    public static double getOffsetFromVoltage(double voltage){
        return 5.03 + -4950*voltage + -4731*Math.pow(voltage, 2) + -2098*Math.pow(voltage, 3) + -286*Math.pow(voltage, 4);

    }
}
