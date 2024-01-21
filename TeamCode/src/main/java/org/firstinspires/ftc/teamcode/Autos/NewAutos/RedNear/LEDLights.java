package org.firstinspires.ftc.teamcode.Autos.NewAutos.RedNear;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.LED;

public class LEDLights extends OpMode {
    RevBlinkinLedDriver LEDLights;
    int temp = 1;
@Override
public void init(){
    LEDLights = hardwareMap.get(RevBlinkinLedDriver.class, "LEDLights");
    LEDLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_GREEN);
}
public void loop() {

    }
}
