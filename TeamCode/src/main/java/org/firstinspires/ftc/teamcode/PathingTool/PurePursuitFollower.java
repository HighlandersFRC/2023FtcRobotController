package org.firstinspires.ftc.teamcode.PathingTool;

import java.util.ArrayList;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Vector;
import org.json.JSONArray;
import org.json.JSONException;


public class PurePursuitFollower implements Command {
  private Drive drive;

  private JSONArray path;

  private double initTime;
  private double currentTime;

  private double odometryFusedX = 0;
  private double odometryFusedY = 0;
  private double odometryFusedTheta = 0;

  private Number[] desiredVelocityArray = new Number[4];
  private double desiredThetaChange = 0;

  private double pathStartTime;

  private int currentPathPointIndex = 0;
  private int returnPathPointIndex = 0;

  public int getPathPointIndex(){
    return currentPathPointIndex;
  }

  public PurePursuitFollower(Drive drive, JSONArray pathPoints) throws JSONException {
    this.drive = drive;
    this.path = pathPoints;
    pathStartTime = pathPoints.getJSONObject(0).getDouble("time");
    Peripherals.resetYaw();
  }

  public void initialize() {
    initTime = System.currentTimeMillis();
    currentPathPointIndex = 0;
    returnPathPointIndex = 0;
  }

  @Override
  public void start() {

  }

  @Override
  public void execute() throws JSONException{

    DriveSubsystem.update();
    odometryFusedX = DriveSubsystem.getOdometryX();
    odometryFusedY = DriveSubsystem.getOdometryY();
    odometryFusedTheta = DriveSubsystem.getOdometryTheta();
    currentTime = System.currentTimeMillis() - initTime + pathStartTime;
    // call PIDController function
    currentPathPointIndex = returnPathPointIndex;
    desiredVelocityArray = drive.purePursuitController(odometryFusedX, odometryFusedY, odometryFusedTheta, currentPathPointIndex, path);
    returnPathPointIndex = desiredVelocityArray[3].intValue() + 1;
    // create velocity vector and set desired theta change
    Vector velocityVector = new Vector();
    velocityVector.setI(desiredVelocityArray[0].doubleValue());
    velocityVector.setJ(desiredVelocityArray[1].doubleValue());
    desiredThetaChange = desiredVelocityArray[2].doubleValue();
    // velocityVector.setI(0);    // velocityVector.setJ(0);
    // desiredThetaChange = 0;

    mecanumVectorDrive(velocityVector, desiredThetaChange);
  }

  public void end() {

    Vector velocityVector = new Vector();
    velocityVector.setI(0);
    velocityVector.setJ(0);
    double desiredThetaChange = 0.0;
    odometryFusedX = Drive.getOdometryX();
    odometryFusedY = Drive.getOdometryY();
    odometryFusedTheta = Drive.getOdometryTheta();
    currentTime = System.currentTimeMillis() - initTime;
  }
  public void mecanumVectorDrive(Vector vector, double thetaChange) {
    double X = vector.getI();
    double Y = vector.getJ();

    double odometryX = Drive.getOdometryX();
    double odometryY = Drive.getOdometryY();
    double odometryTheta = Drive.getOdometryTheta();

    double deltaX = X - odometryX;
    double deltaY = Y - odometryY;
    double deltaTheta = thetaChange - odometryTheta;

    double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    double angle = Math.atan2(deltaY, deltaX);

    double leftBackPower = distance * Math.cos(angle + Math.toRadians(90)) + deltaTheta;
    double rightBackPower = distance * Math.sin(angle + Math.toRadians(90)) - deltaTheta;
    double leftFrontPower = distance * Math.cos(angle - Math.toRadians(90)) + deltaTheta;
    double rightFrontPower = distance * Math.sin(angle - Math.toRadians(90)) - deltaTheta;

    Drive.drive(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);
  }

  @Override
  public boolean isFinished() {
    if (currentPathPointIndex >= path.length()-1){
      return true;
    } else {
      return false;
    }
  }
}
