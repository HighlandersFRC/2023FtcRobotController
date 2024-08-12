package org.firstinspires.ftc.teamcode;

import java.util.HashMap;
import java.util.Map;

public class ConstantsVision {
    public static double yCorrected(double AY) {
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
        public double tagangle;

        public AprilTagData(double positionX, double positionY, double size, double tagangle) {
            this.positionX = positionX;
            this.positionY = positionY;
            this.size = size;
            this.tagangle = tagangle; // Angle already provided in radians
        }
    }


    // HashMap to store AprilTag data
    public static final Map<Integer, AprilTagData> aprilTagMap = new HashMap<>();

    static {
        aprilTagMap.put(7, new AprilTagData(0.0, 0.0, 1.27, 0));
        aprilTagMap.put(8, new AprilTagData(5.0, 5.0, 1.27, Math.PI / 2));
    }
}
