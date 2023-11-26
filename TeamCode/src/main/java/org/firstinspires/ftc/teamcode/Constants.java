package org.firstinspires.ftc.teamcode;

public class Constants {
    //constants for arm positions
    public static double armPlace = -4200;
    public static double armIntake = 0;
    public static double absoluteArmZero = 3.247;
    public static double voltsPer1000Ticks = 0.44;
    public static double armOffset = 0;
    public static double getOffsetFromVoltage(double voltage){
        return 205 + -5188*voltage + 2932*Math.pow(voltage, 2) + -638*Math.pow(voltage, 3);
    }
    public static double wristDown = 0.41;//comp bot
    public static double wristUp = 0.9; //comp bot
    //public static double wristDown = 0.3; //practice bot
    //public static double wristUp = 0.9; //practice bot
}
