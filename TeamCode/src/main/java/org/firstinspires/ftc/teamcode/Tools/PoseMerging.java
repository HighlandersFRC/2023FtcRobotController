package org.firstinspires.ftc.teamcode.Tools;

import org.firstinspires.ftc.teamcode.AprilTagCustomDetection;

public class PoseMerging {
    private double odo_x;
    private double odo_y;
    private double odo_theta;

    public PoseMerging(double x, double y, double theta) {
        this.odo_x = x;
        this.odo_y = y;
        this.odo_theta = theta;
    }

    public PoseMerging() {
    }

    public double[] odometryPosition() {
        return new double[]{Odometry.getX(), Odometry.getY(), Odometry.getTheta()};
    }

    public double[] cameraPosition() {
        return new double[]{AprilTagCustomDetection.getCorrectX(), AprilTagCustomDetection.getCorrectY(), AprilTagCustomDetection.getRobotyawcalculated()};
    }

    public double[] opticalPosition() {
        return new double[]{Odometry.getX(), Odometry.getY(), Odometry.getTheta()};
    }

    @Override
    public String toString() {
        return "PoseMerging{" +
                "odo_x=" + odo_x +
                ", odo_y=" + odo_y +
                ", odo_theta=" + odo_theta +
                '}';
    }
}
