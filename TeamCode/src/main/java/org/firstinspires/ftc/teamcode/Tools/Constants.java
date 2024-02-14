package org.firstinspires.ftc.teamcode.Tools;

public class Constants {
    //constants for arm positions
    public static double armPlace = 3900;
    public static double armIntake = 100;
    public static double armLow = 4020;
    public static double armHigh = 3800;
    //old comp bot
/*    public static double absoluteArmZero = 3.241;*/
    public static double absoluteArmZero =0.306;
    public static double armOffset;
    public static double motorTicksPerMeter = 1685;
    public static double leftServoUp = 0.8;
    public static double rightServoUp = 0.2;
    public static double leftServoDown = 0.3;
    public static double rightServoDown = 0.8;
    //old comp bot
/*    public static double getOffsetFromVoltage(double voltage){
        return 0 + -4506*(voltage) + 1852*Math.pow(voltage, 2) + -93.9*Math.pow(voltage, 3) + -85.8*Math.pow(voltage, 4);
    }*/
    //new bot
    public static double getOffsetFromVoltage(double voltage){
        return 5.03 + -4950*voltage + -4731*Math.pow(voltage, 2) + -2098*Math.pow(voltage, 3) + -286*Math.pow(voltage, 4);

    }
    public static double wristDown = 0.45;//comp bot

    public static double wristUp = 1;//comp bot
    public static double lowWrist = 0.85;
    public static double lastSetElevatorPosition = 0;
    public static double retractedElevator = 100;
    public static double deployedElevator = 2600;
    public static String currentNavigationalSensor = "IMU";
/*    public static double wristDown = 0.3; //practice bot
    public static double wristUp = 0.7; //practice bot*/
}