package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LEDLights extends Subsystems{
    public static RevBlinkinLedDriver blinkin;
    public static void initialize(HardwareMap hardwareMap){
        blinkin = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
    }
    public static void setModeRedSideAuto(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIGHT_CHASE_RED);
    }
    public static void setModeBlueSideAuto(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIGHT_CHASE_RED);
    }
    public static void setModeRainbow(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_RAINBOW_PALETTE);
    }
    public static void setModePurple(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
    }
    public static void setModeGreen(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
    }
    public static void setModePink(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
    }
    public static void setModeFire(){
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.FIRE_LARGE);
    }
}
