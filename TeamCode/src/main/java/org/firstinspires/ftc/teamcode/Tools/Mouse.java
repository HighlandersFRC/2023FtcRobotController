package org.firstinspires.ftc.teamcode.Tools;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

public class Mouse {

    private static double fieldX;
    private static double fieldY;
    private static double theta;
    static SparkFunOTOS mouse;

    public static  void init( ) {
        mouse = hardwareMap.get(SparkFunOTOS.class, "mouse");
        configureOtos();
    }
    public static double getX(){
        SparkFunOTOS.Pose2D field = mouse.getPosition();
        fieldX=field.x;
        return fieldX;
    }
    public static double getY(){
        SparkFunOTOS.Pose2D field = mouse.getPosition();
        fieldY=field.y;
        return fieldY;
    }
    public static double getTheta(){
        SparkFunOTOS.Pose2D field = mouse.getPosition();
        theta=field.h;
        return theta;
    }

    private static void configureOtos() {

        mouse.setLinearUnit(SparkFunOTOS.LinearUnit.METERS);
        mouse.setAngularUnit(SparkFunOTOS.AngularUnit.RADIANS);
        SparkFunOTOS.Pose2D offset = new SparkFunOTOS.Pose2D(0, 0, 0);
        mouse.setOffset(offset);
        mouse.calibrateImu();
        mouse.resetTracking();
        SparkFunOTOS.Pose2D currentPosition = new SparkFunOTOS.Pose2D(0, 0, 0);
        mouse.setPosition(currentPosition);

    }
}
