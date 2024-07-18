package org.firstinspires.ftc.teamcode.Subsystems;
import static org.firstinspires.ftc.teamcode.Tools.constants.dtheta;
import static org.firstinspires.ftc.teamcode.Tools.constants.posH;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Tools.constants;

public class Odometry extends Subsystem {
    public static DcMotor leftMotor;
    public static DcMotor rightMotor;
    static final int TicksPerRotation = 2000;
    static final double rotationsPerMeter = 20.8333333333;
    static final double L = 0; //distance between left and right odo pod
    static final double MidPoint = 0; //distance between the mid point of all the encoders
    static final double wheelRadius = 24;
    static final double cm_per_tick = 0.024;
    public Odometry(String name) {
        super(name);
    }

    public static void initialize(HardwareMap hardwareMap) {
        leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right_motor");

    }

    public static  void  getPos (){
        System.out.println(leftMotor.getCurrentPosition());

        System.out.println(rightMotor.getCurrentPosition());
    }

    public static  double  getRotationR (){
       return rightMotor.getCurrentPosition()/TicksPerRotation;
    }

    public static  double  getRotationL (){
        return leftMotor.getCurrentPosition()/TicksPerRotation;
    }

    public static  double  getPosR (){
        return rightMotor.getCurrentPosition();
    }

    public static  double  getPosL (){
        return leftMotor.getCurrentPosition();
    }

    public static  double  getMeterR (){
        return getRotationR()/rotationsPerMeter;
    }

    public static  double  getMeterL (){
        return getRotationL()/rotationsPerMeter;
    }


   public void odometry() {
        double oldRightPosition = getPosR();
        double oldLeftPosition = getPosL();
//        double oldAuxPosition = currentAuxPosition;
//
        double currentRightPosition = getPosR();
        double currentLeftPosition = getPosL();
//        currentAuxPosition = encoderAux.getCurrentPosition();
//
        double dn1 = currentLeftPosition  - oldLeftPosition;
        double dn2 = currentRightPosition - oldRightPosition;
//        int dn3 = currentAuxPosition - oldAuxPosition;
//
//        // the robot has moved and turned a tiny bit between two measurements:
        dtheta = cm_per_tick * ((dn2-dn1) / (L)); // code is ready
        double dx = cm_per_tick * ((dn1+dn2) / 2.0);    // code is ready
//        double dy = cm_per_tick * (dn3 + ((dn2-dn1) / 2.0));

            constants.telemetrydx = dx; // code is ready
//        telemetrydy = dy; +
            constants.telemetrydh = dtheta; // code is ready
//
//        // small movement of the robot gets added to the field coordinate system:
          posH += dtheta / 2; // code is ready // code is ready
//        pos.x += dx * Math.cos(pos.h) - dy * Math.sin(pos.h);
//        pos.y += dx * Math.sin(pos.h) + dy * Math.cos(pos.h);
          posH += dtheta;// code is ready
  }

}
