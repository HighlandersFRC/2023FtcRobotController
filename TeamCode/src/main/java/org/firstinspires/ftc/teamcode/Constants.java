package org.firstinspires.ftc.teamcode;

public class Constants {
    //constants for arm positions
    public static double armPlace = -4000;
    public static double armIntake = 130;
    public static double lowArm = -4500;
    public static double absoluteArmZero = 3.241;
    public static double armOffset = 0;
    public static double motorTicksPerRotation = 1685;
    public static double leftServoUp = 0.8;
    public static double rightServoUp = 0.2;
    public static double leftServoDown = 0.3;
    public static double rightServoDown = 0.8;
    public static double getOffsetFromVoltage(double voltage){
        return 0 + -4506*(voltage) + 1852*Math.pow(voltage, 2) + -93.9*Math.pow(voltage, 3) + -85.8*Math.pow(voltage, 4);
    }
    public static double wristDown = 0.45;//comp bot

    public static double wristUp = 0.95;//comp bot
    public static double lowWrist = 0.85;
/*    public static double wristDown = 0.3; //practice bot
    public static double wristUp = 0.7; //practice bot*/
}