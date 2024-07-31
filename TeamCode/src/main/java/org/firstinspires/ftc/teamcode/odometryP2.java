/*
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Tools.XyhVector;

public class odometryP2 {
    public static DcMotor leftEncoder;
    public static DcMotor rightEncoder;
    public static DcMotor centerEncoder; // Center encoder for additional tracking

    public static final int TicksPerRotation = 1962;
    public static final double rotationsPerMeter = 6.63145596218;
    public static final double L = 34.766231226235135; // Distance between left and right odo pods in cm
    public static final double HalfL = L / 2;
    public static final double wheelRadius = 24; // If needed
    public static final double cm_per_tick = 0.024; // cm per encoder tick
    public static double posH = 0;
    public static double telemetrydx = 0;
    public static double telemetrydh = 0;
    public static double dn1 = 0;
    public static double dn2 = 0;
    public static double dn3 = 0; // For the center encoder

    public odometryP2(String name) {
        // Constructor implementation, if needed
    }

    public static void initialize(HardwareMap hardwareMap) {
        leftEncoder = hardwareMap.get(DcMotor.class, "right_front");
        rightEncoder = hardwareMap.get(DcMotor.class, "left_front");
        centerEncoder = hardwareMap.get(DcMotor.class, "right_back" );
        // Set encoders to use encoders
        leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        centerEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        centerEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public static void getPos() {
        System.out.println("Left Encoder Position: " + leftEncoder.getCurrentPosition());
        System.out.println("Right Encoder Position: " + rightEncoder.getCurrentPosition());
        System.out.println("Center Encoder Position: " + centerEncoder.getCurrentPosition());
    }

    public static double getRotationR() {
        return (double) rightEncoder.getCurrentPosition() / TicksPerRotation;
    }

    public static double getRotationL() {
        return (double) leftEncoder.getCurrentPosition() / TicksPerRotation;
    }

    public static double getRotationC() {
        return (double) centerEncoder.getCurrentPosition() / TicksPerRotation;
    }

    public static double getPosR() {
        return rightEncoder.getCurrentPosition();
    }

    public static double getPosL() {
        return leftEncoder.getCurrentPosition();
    }

    public static double getPosC() {
        return centerEncoder.getCurrentPosition();
    }

    public static double getMeterR() {
        return getRotationR() / rotationsPerMeter;
    }

    public static double getMeterL() {
        return getRotationL() / rotationsPerMeter;
    }

    public static double getMeterC() {
        return getRotationC() / rotationsPerMeter;
    }

    static XyhVector StartingPos = new XyhVector(0, 0, Math.toRadians(0));
    public static XyhVector pos = new XyhVector(StartingPos);

    public static void update() {
        double oldRightPosition = getPosR();
        double oldLeftPosition = getPosL();
        double oldCenterPosition = getPosC();

        double currentRightPosition = getPosR();
        double currentLeftPosition = getPosL();
        double currentCenterPosition = getPosC();

        dn1 = currentLeftPosition - oldLeftPosition;
        dn2 = currentRightPosition - oldRightPosition;
        dn3 = currentCenterPosition - oldCenterPosition;

        // Calculate change in heading and position
        double dtheta = cm_per_tick * ((dn2 - dn1) / L); // Change in heading
        double dx = cm_per_tick * ((dn1 + dn2) / 2.0);    // Average movement
        double dy = cm_per_tick * dn3; // Movement from center encoder

        telemetrydx = dx;
        telemetrydh = dtheta;

        // Update position
        pos.h += dtheta;
        pos.x += dx * Math.cos(pos.h) - dy * Math.sin(pos.h);
        pos.y += dx * Math.sin(pos.h) + dy * Math.cos(pos.h);
    }
}
*/
