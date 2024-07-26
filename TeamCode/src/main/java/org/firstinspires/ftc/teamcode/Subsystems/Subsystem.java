package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class Subsystem {

    private String name;

    public Subsystem() {
        this.name = name;
    }

    public Subsystem(String name) {
    }

    public String getName() {
        return name;
    }

    public static void initialize(HardwareMap hardwareMap) {

    }

}
