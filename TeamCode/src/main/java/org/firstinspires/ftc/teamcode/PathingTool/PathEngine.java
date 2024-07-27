package org.firstinspires.ftc.teamcode.PathingTool;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Tools.Odometry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PathEngine {

    private JSONObject jsonPathData;
    private List<SampledPoint> sampledPoints;
    private int currentPointIndex = 0;
    private double startTime;
    private Odometry odometry;
    private DriveSubsystem driveSubsystem;
    private static final double COORDINATE_UNIT = 1.0;

    public PathEngine(OpMode opMode, String pathFileName, Odometry odometry, DriveSubsystem driveSubsystem) {
        loadJSONFromAsset(opMode, pathFileName);
        parseSampledPoints();
        this.odometry = odometry;
        this.driveSubsystem = driveSubsystem;
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
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseSampledPoints() {
        sampledPoints = new ArrayList<>();
        try {
            JSONArray pointsArray = jsonPathData.getJSONArray("sampled_points");
            for (int i = 0; i < pointsArray.length(); i++) {
                JSONObject point = pointsArray.getJSONObject(i);
                SampledPoint sampledPoint = new SampledPoint(
                        point.getDouble("time"),
                        point.getDouble("x"),
                        point.getDouble("y"),
                        point.getDouble("angle"),
                        point.getDouble("x_velocity"),
                        point.getDouble("y_velocity"),
                        point.getDouble("angular_velocity"),
                        point.getDouble("x_acceleration"),
                        point.getDouble("y_acceleration"),
                        point.getDouble("angular_acceleration")
                );
                sampledPoints.add(sampledPoint);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void startPath(double startTime) {
        this.startTime = startTime;
        currentPointIndex = 0;

        // Initialize odometry at the starting point
        if (!sampledPoints.isEmpty()) {
            SampledPoint startPoint = sampledPoints.get(0);
        }
    }

    public void update(double currentTime) {
        if (currentPointIndex >= sampledPoints.size()) {
            driveSubsystem.stop();
            return;
        }

        SampledPoint currentPoint = sampledPoints.get(currentPointIndex);

        // Determine the target point based on time
        while (currentPointIndex < sampledPoints.size() - 1 &&
                sampledPoints.get(currentPointIndex + 1).time <= currentTime - startTime) {
            currentPointIndex++;
            currentPoint = sampledPoints.get(currentPointIndex);
        }

        // Move to the target point
/*
        driveSubsystem.moveToPosition(currentPoint.angle, currentPoint.x, currentPoint.y);
*/
    }

    private static class SampledPoint {
        double time, x, y, angle, x_velocity, y_velocity, angular_velocity, x_acceleration, y_acceleration, angular_acceleration;

        public SampledPoint(double time, double x, double y, double angle, double x_velocity, double y_velocity,
                            double angular_velocity, double x_acceleration, double y_acceleration, double angular_acceleration) {
            this.time = time;
            this.x = x;
            this.y = y;
            this.angle = angle;
            this.x_velocity = x_velocity;
            this.y_velocity = y_velocity;
            this.angular_velocity = angular_velocity;
            this.x_acceleration = x_acceleration;
            this.y_acceleration = y_acceleration;
            this.angular_acceleration = angular_acceleration;
        }
    }
}
