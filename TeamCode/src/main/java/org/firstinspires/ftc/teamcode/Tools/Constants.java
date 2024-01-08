package org.firstinspires.ftc.teamcode.Tools;

public class Constants {
    //constants for arm positions
    public static double armPlace = 3800;
    public static double armIntake = 0;
    public static double lowArm = -4500;
    //old comp bot
    public static double absoluteArmZero = 3.241;
    /*public static double absoluteArmZero = 2.46;*/
    public static double armOffset = 0;
    public static double motorTicksPerMeter = 1685;
    public static double leftServoUp = 0.8;
    public static double rightServoUp = 0.2;
    public static double leftServoDown = 0.3;
    public static double rightServoDown = 0.8;
    //old comp bot
    public static double getOffsetFromVoltage(double voltage){
        return 0 + -4506*(voltage) + 1852*Math.pow(voltage, 2) + -93.9*Math.pow(voltage, 3) + -85.8*Math.pow(voltage, 4);
    }
    //new bot
  /*  public static double getOffsetFromVoltage(double voltage){
        return -2.21-30 + 7.13-03*voltage+ -1.46-5*Math.pow(voltage, 2) + 8.95-9*Math.pow(voltage, 3) + -2.1-12*Math.pow(voltage, 4) + 1.68-16*Math.pow(voltage, 5);
    }*/
    public static double wristDown = 0.33;//comp bot

    public static double wristUp = 0.95;//comp bot
    public static double lowWrist = 0.85;
    public static double lastSetElevatorPosition = 0;
    public static double retractedElevator = 0;
    public static double deployedElevator = -1200;
/*    public static double wristDown = 0.3; //practice bot
    public static double wristUp = 0.7; //practice bot*/
}