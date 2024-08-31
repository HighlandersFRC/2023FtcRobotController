package org.firstinspires.ftc.teamcode.PathingTool;

import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Tools.PID;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PurePursuitController {

    private PID xPID, yPID, thetaPID;
    private DriveSubsystem driveSubsystem;

    public PurePursuitController(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        xPID = new PID(Constants.PID_X_P, Constants.PID_X_I, Constants.PID_X_D);
        yPID = new PID(Constants.PID_Y_P, Constants.PID_Y_I, Constants.PID_Y_D);
        thetaPID = new PID(Constants.PID_THETA_P, Constants.PID_THETA_I, Constants.PID_THETA_D);
    }

    public Number[] purePursuitController(double currentX, double currentY, double currentTheta, int currentIndex, JSONArray pathPoints) throws JSONException, JSONException {
        JSONObject targetPoint = pathPoints.getJSONObject(currentIndex);
        int targetIndex = currentIndex;

        for (int i = currentIndex; i < pathPoints.length(); i++) {
            JSONObject point = pathPoints.getJSONObject(i);
            if (insideRadius(currentX - point.getDouble("x"), currentY - point.getDouble("y"), 2)) {
                targetPoint = point;
                targetIndex = i;
                break;
            }
        }

        double targetX = targetPoint.getDouble("x");
        double targetY = targetPoint.getDouble("y");
        double targetTheta = targetPoint.getDouble("angle");

        xPID.setSetPoint(targetX);
        yPID.setSetPoint(targetY);
        thetaPID.setSetPoint(targetTheta);

        xPID.updatePID(currentX);
        yPID.updatePID(currentY);
        thetaPID.updatePID(currentTheta);

        double xVelNoFF = xPID.getResult();
        double yVelNoFF = yPID.getResult();
        double thetaVelNoFF = -thetaPID.getResult();

        double feedForwardX = targetPoint.getDouble("x_velocity");
        double feedForwardY = targetPoint.getDouble("y_velocity");
        double feedForwardTheta = 0;

        Number[] velocityArray = new Number[]{
                feedForwardX + xVelNoFF,
                -(feedForwardY + yVelNoFF),
                feedForwardTheta + thetaVelNoFF,
                targetIndex,
        };

        return velocityArray;
    }

    private boolean insideRadius(double deltaX, double deltaY, double radius) {
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) < radius;
    }

    public double getAdjustedY(double originalX, double originalY) {
        return originalY * Math.sqrt(1 - (Math.pow(originalX, 2) / 2));
    }

    public double getAdjustedX(double originalX, double originalY) {
        return originalX * Math.sqrt(1 - (Math.pow(originalY, 2) / 2));
    }
}
