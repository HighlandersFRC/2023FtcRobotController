package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class LEDLights extends OpMode {
    RevBlinkinLedDriver LEDLights;
    int temp = 1;

    @Override
    public void init() {
        LEDLights = hardwareMap.get(RevBlinkinLedDriver.class, "LEDLights");
        LEDLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
    }

    public void loop() {
        if (temp == 1) {
            resetRuntime();
            temp = 2;
        }
        if(time >= 30 && time<130){
            LEDLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED_ORANGE);
        }else if(time >=130 && time <150){
            LEDLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE);
        }else{
            LEDLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
        }
    }
}