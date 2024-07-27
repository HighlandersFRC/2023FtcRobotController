package org.firstinspires.ftc.teamcode.Tools;

public class XyhVector {
    public double x;
    public double y;
    public double h;

    public XyhVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.h = z;
    }

    public XyhVector() {
    }

    @Override
    public String toString() {
        return "Tuple{" + "x=" + x + ", y=" + y + ", z=" + h + '}';
    }
}

