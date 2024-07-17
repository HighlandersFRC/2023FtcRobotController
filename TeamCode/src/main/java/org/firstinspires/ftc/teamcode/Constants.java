package org.firstinspires.ftc.teamcode;

public class Constants {
    public static double fiveCorrected(double range){
        return range - (-0.106 + 0.508*(range) + -0.431*Math.pow(range,2)  + 0.223*Math.pow(range, 3) + -0.0379*Math.pow(range,4));
    }
}
//this is a an equation for the offset on the range found using quartic regressions