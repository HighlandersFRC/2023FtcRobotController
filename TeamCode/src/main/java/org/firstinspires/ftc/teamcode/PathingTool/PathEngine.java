/*
package org.firstinspires.ftc.teamcode.PathingTool;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PathEngine {

    private JSONObject jsonPathData;
    private JSONArray pathPoints;
    private int currentPointIndex = 0;
    private double startTime;
    private Odometry odometry;
    private DriveSubsystem driveSubsystem;
    private Telemetry telemetry;
    private PurePursuitController purePursuitController;

    public PathEngine(OpMode opMode, String pathFileName, Odometry odometry, DriveSubsystem driveSubsystem) {
        this.telemetry = opMode.telemetry;
        loadJSONFromAsset(opMode, pathFileName);
        this.odometry = odometry;
        this.driveSubsystem = driveSubsystem;
        this.purePursuitController = new PurePursuitController(driveSubsystem, odometry);
    }

    private void loadJSONFromAsset(OpMode opMode, String pathFileName) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(opMode.hardwareMap.appContext.getAssets().open(pathFileName))
            );
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            jsonPathData = new JSONObject(sb.toString());
            pathPoints = jsonPathData.getJSONArray("sampled_points");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void startPath(double startTime) {
        this.startTime = startTime;
        currentPointIndex = 0;

        if (pathPoints.length() > 0) {
            try {
                JSONObject startPoint = pathPoints.getJSONObject(0);
                odometry.setCurrentPosition(startPoint.getDouble("x"), startPoint.getDouble("y"), startPoint.getDouble("angle"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(double currentTime) {
        if (currentPointIndex >= pathPoints.length()) {
            driveSubsystem.stop();
            return;
        }

        try {
            Number[] velocityArray = purePursuitController.purePursuitController(
                    odometry.getOdometryX(),
                    odometry.getOdometryY(),
                    odometry.getOdometryTheta(),
                    currentPointIndex,
                    pathPoints
            );

            driveSubsystem.driveByVectors(
                    velocityArray[0].doubleValue(),
                    velocityArray[1].doubleValue(),
                    velocityArray[2].doubleValue()
            );

            currentPointIndex = velocityArray[3].intValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        telemetry.addData("Current Time", currentTime);
        telemetry.addData("Current Position X", Odometry.getOdometryX());
        telemetry.addData("Current Position Y", Odometry.getOdometryY());
        telemetry.addData("Current Angle", Odometry.getOdometryTheta());
        telemetry.update();
    }
}
*/
