package org.firstinspires.ftc.teamcode.PathingTool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.FieldOfMerit;
import org.firstinspires.ftc.teamcode.Tools.FinalPose;
import org.firstinspires.ftc.teamcode.Tools.Vector;
import org.json.JSONArray;
import org.json.JSONException;

public class PurePursuitFollower implements Command {
  private Drive drive;
  private Peripherals peripherals;

  private JSONArray path;

  private double initTime;
  private double currentTime;

  private double odometryFusedX = 0;
  private double odometryFusedY = 0;
  private double odometryFusedTheta = 0;

  private Number[] desiredVelocityArray = new Number[4];
  private double desiredThetaChange = 0;

  private boolean record;

  private ArrayList<double[]> recordedOdometry = new ArrayList<double[]>();
  public double pathStartTime;

  private boolean pickupNote;

  private int currentPathPointIndex = 0;
  private int returnPathPointIndex = 0;
  private int timesStagnated = 0;
  private final int STAGNATE_THRESHOLD = 3;

  public int getPathPointIndex() {
    return currentPathPointIndex;
  }

  public PurePursuitFollower(Drive drive, Peripherals peripherals, JSONArray pathPoints,
                             boolean record) throws JSONException {
    this.drive = drive;
    this.path = pathPoints;
    this.record = record;
    pathStartTime = pathPoints.getJSONObject(0).getDouble("time");
    this.peripherals = peripherals;
  }

  public void initialize() {
    initTime = System.currentTimeMillis();
    currentPathPointIndex = 0;
    returnPathPointIndex = 0;
    timesStagnated = 0;
  }

  @Override
  public void start() {

  }

  @Override
  public void execute() throws JSONException {
    FinalPose.poseUpdate();
    odometryFusedX = FinalPose.x;
    odometryFusedY = FinalPose.y;
    odometryFusedTheta = Peripherals.getYawDegrees();
    // System.out.println("Follower field side: " + this.drive.getFieldSide());

    // System.out.println("Odom - X: " + odometryFusedX + " Y: " + odometryFusedY +
    // " Theta: " + odometryFusedTheta);

    currentTime = System.currentTimeMillis() - initTime + pathStartTime;
    // call PIDController function
    currentPathPointIndex = returnPathPointIndex;
    desiredVelocityArray = drive.purePursuitController(odometryFusedX, odometryFusedY, odometryFusedTheta,
            currentPathPointIndex, path);

    returnPathPointIndex = desiredVelocityArray[3].intValue();
    if (returnPathPointIndex == currentPathPointIndex) {
      timesStagnated++;
      if (timesStagnated > STAGNATE_THRESHOLD) {
        returnPathPointIndex++;
        timesStagnated = 0;
      }
    } else {
      timesStagnated = 0;
    }

    // create velocity vector and set desired theta change
    Vector velocityVector = new Vector();
    velocityVector.setI(desiredVelocityArray[0].doubleValue());
    velocityVector.setJ(desiredVelocityArray[1].doubleValue());
    desiredThetaChange = desiredVelocityArray[2].doubleValue();
    // velocityVector.setI(0);
    // velocityVector.setJ(0);
    // desiredThetaChange = 0;

    drive.autoDrive(velocityVector, desiredThetaChange);
  }

  @Override
  public void end() {

    Vector velocityVector = new Vector();
    velocityVector.setI(0);
    velocityVector.setJ(0);
    double desiredThetaChange = 0.0;
    drive.autoDrive(velocityVector, desiredThetaChange);

    odometryFusedX = FinalPose.x;
    odometryFusedY = FinalPose.y;
    odometryFusedTheta = Peripherals.getYawDegrees();
    currentTime = System.currentTimeMillis() - initTime;
  }

  public boolean isFinished() {
    if (returnPathPointIndex >= path.length() - 1) {
      return true;
    } else {
      return false;
    }
  }
}