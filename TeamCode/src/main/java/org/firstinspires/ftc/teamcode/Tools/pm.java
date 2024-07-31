/*
package org.firstinspires.ftc.teamcode.Tools;

public class pm {
    public double[] odometryPosition(){
        return new double[]{Odometry.getX(), Odometry.getY(), Odometry.getTheta()};
    }
    public double[] cameraPosition(){
        return new double[]{Odometry.getX(), Odometry.getY(), Odometry.getTheta()};
    }
    public double[] opticalPosition(){
        return new double[]{Odometry.getX(), Odometry.getY(), Odometry.getTheta()};
    }
}
*/


package org.firstinspires.ftc.teamcode.Tools;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class pm {

    public static void updateOdometryWithCamera(double cameraX, double cameraY, double cameraTheta) {
        Odometry.setCurrentPosition(cameraX, cameraY, cameraTheta);
        Odometry.resetEncoders();
    }

    public static void mergePose(double cameraX, double cameraY, double cameraTheta) {
        updateOdometryWithCamera(cameraX, cameraY, cameraTheta);
    }
}
