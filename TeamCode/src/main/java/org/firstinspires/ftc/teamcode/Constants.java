package org.firstinspires.ftc.teamcode;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static double yCorrected(double AY) {
        /*  return range - (-0.106 + 0.508*(range) + -0.431*Math.pow(range,2)  + 0.223*Math.pow(range, 3) + -0.0379*Math.pow(range,4));*/
        /* return AY - (1.13 + (-2.59*AY) + (2.43*Math.pow(AY,2)) + (-0.904*Math.pow(AY,3)) + (0.12*Math.pow(AY,4)));*/
        return AY - ((0.172 * AY) + 0.00307);
    }

    public static double xCorrected(double AX) {
        return AX - (0.181 * AX + -0.00049);
    }

    // Define a class to hold vector field position, size, and angle
    public static class AprilTagData {
        public double positionX;
        public double positionY;
        public double size;
        public double angle;

        public AprilTagData(double positionX, double positionY, double size, double angle) {
            this.positionX = positionX;
            this.positionY = positionY;
            this.size = size;
            this.angle = angle;
        }
    }

    // HashMap to store AprilTag data
    public static final Map<Integer, AprilTagData> aprilTagMap = new HashMap<>();

    static {

        aprilTagMap.put(1, new AprilTagData(1.0, 2.0, 3.0, Math.toRadians(45.0)));
        aprilTagMap.put(2, new AprilTagData(4.0, 5.0, 6.0, Math.toRadians(90.0)));

    }
}
