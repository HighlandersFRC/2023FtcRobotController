package org.firstinspires.ftc.teamcode.Tools;

public class PoseMerging {
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
