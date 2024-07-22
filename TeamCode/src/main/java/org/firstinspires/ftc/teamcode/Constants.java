package org.firstinspires.ftc.teamcode;

public class Constants {
    public static double yCorrected(double AY) {
        /*  return range - (-0.106 + 0.508*(range) + -0.431*Math.pow(range,2)  + 0.223*Math.pow(range, 3) + -0.0379*Math.pow(range,4));*/
       /* return AY - (1.13 + (-2.59*AY) + (2.43*Math.pow(AY,2)) + (-0.904*Math.pow(AY,3)) + (0.12*Math.pow(AY,4)));*/
        return AY-((0.172*AY) + 0.00307);
    }
    public static double xCorrected(double AX){

        return AX - (0.181*AX + -0.00049);
    }
}
//this is a an equation for the offset on the range found using quartic regressions