package org.firstinspires.ftc.teamcode.PathingTool;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.TestCommand;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PathEngine {

    private JSONObject jsonPathData;
    private static int currentIndex = 0;
    private final double COORDINATE_UNIT = 1.0;
    private double[] lastPoint = {0.0, 0.0};
    private double[] currentPoint = {0.0, 0.0};
    private static double lastPointLeft = 0;
    private static double lastpointRight = 0;
    CommandScheduler scheduler;
    HashMap<String, Command> map = new HashMap<String, Command>();


    public PathEngine(OpMode opMode, String pathFileName) {
        loadJSONFromAsset(opMode, pathFileName);

        System.out.println(jsonPathData);

        map.put("TestCommand", new TestCommand());
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
            opMode.telemetry.addData("Error", "Failed to load JSON file: " + pathFileName);
            opMode.telemetry.update();
        }
    }

    public double getNextSampledPointTime() {
        try {
            JSONArray sampledPoints = jsonPathData.getJSONArray("sampled_points");
            if (currentIndex < sampledPoints.length()) {
                JSONObject nextPoint = sampledPoints.getJSONObject(currentIndex);
                double time = nextPoint.getDouble("time");
                return time;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double getNextSampledPointX() {
        try {
            JSONArray sampledPoints = jsonPathData.getJSONArray("sampled_points");
            if (currentIndex < sampledPoints.length()) {
                JSONObject nextPoint = sampledPoints.getJSONObject(currentIndex);
                double x = nextPoint.getDouble("x") * COORDINATE_UNIT;
                return x;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double getNextSampledPointY() {
        try {
            JSONArray sampledPoints = jsonPathData.getJSONArray("sampled_points");
            if (currentIndex < sampledPoints.length()) {
                JSONObject nextPoint = sampledPoints.getJSONObject(currentIndex);
                double y = nextPoint.getDouble("y") * COORDINATE_UNIT;
                return y;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double getNextSampledPointAngle() {
        try {
            JSONArray sampledPoints = jsonPathData.getJSONArray("sampled_points");
            if (currentIndex < sampledPoints.length()) {
                JSONObject nextPoint = sampledPoints.getJSONObject(currentIndex);
                double angle = nextPoint.getDouble("angle");
                return angle;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static void finishCurrentPoint() {
        lastPointLeft = DriveSubsystem.leftMotor.getCurrentPosition();
        lastpointRight = DriveSubsystem.rightMotor.getCurrentPosition();
        currentIndex++;
    }

    public void update() throws InterruptedException {
        System.out.println(getNextSampledPointX());
        double x = getNextSampledPointX();
        double y = getNextSampledPointY();
        double angle = getNextSampledPointAngle();
        DriveSubsystem.moveToPosition(x, y, calculateDistance(), angle, lastPointLeft, lastpointRight);
        lastPoint[0] = currentPoint[0];
        lastPoint[1] = currentPoint[1];
        currentPoint[0] = x;
        currentPoint[1] = y;
    }

    public double calculateDistance() {
        double lastX = lastPoint[0];
        double lastY = lastPoint[1];
        double currentX = getNextSampledPointX();
        double currentY = getNextSampledPointY();

        double deltaX = currentX - lastX;
        double deltaY = currentY - lastY;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}