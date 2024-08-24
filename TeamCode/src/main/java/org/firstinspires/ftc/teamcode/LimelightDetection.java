/*
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

@TeleOp(name = "Limelight AprilTag Detection")
public class LimelightDetection extends LinearOpMode {

    private static final String LIMELIGHT_IP = "172.28.2.1";

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            try {
                String response = getLimelightData();
                if (response != null) {
                    parseAndDisplayAprilTagData(response);
                }
            } catch (Exception e) {
                telemetry.addData("Error", "Exception: " + e.getMessage());
                e.printStackTrace();
            }

            telemetry.update();
            sleep(1000);
        }
    }

    private String getLimelightData() {
        String urlString = "http://" + LIMELIGHT_IP + ":5807/results";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return content.toString();
        } catch (Exception e) {
            telemetry.addData("Error", "Failed to retrieve data: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void parseAndDisplayAprilTagData(String jsonData) {
        try {
            JSONObject jsonObj = new JSONObject(jsonData);
            JSONArray targets = jsonObj.getJSONArray("Results");

            for (int i = 0; i < targets.length(); i++) {
                JSONObject tag = targets.getJSONObject(i);
                int id = tag.getInt("id");
                double x = tag.getDouble("tx");
                double y = tag.getDouble("ty");
                double z = tag.getDouble("tz");
                double rotation = tag.getDouble("rotation");

                telemetry.addData("Tag ID", id);
                telemetry.addData("X", x);
                telemetry.addData("Y", y);
                telemetry.addData("Z", z);
                telemetry.addData("Rotation", rotation);

                Constants.AprilTagData tagData = Constants.aprilTagMap.get(id);
                double tagheight = (tagData != null ? tagData.positionZ: 0 );
            }
        } catch (Exception e) {
            telemetry.addData("Error", "JSON Parsing Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
*/
/*

*//*

*/
/*ackage org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

@TeleOp(name = "Limelight AprilTag Detection")
public class LimelightDetection extends LinearOpMode {

    private static final String LIMELIGHT_IP = "172.28.2.1";  // Adjust to your Limelight IP
    private static final double CAMERA_HEIGHT = 0.7874;  // Example height in meters
    private static final double TAG_HEIGHT = 1.4478;     // Example AprilTag height in meters

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            try {
                String response = getLimelightData();
                if (response != null) {
                    parseAndDisplayAprilTagData(response);
                }
            } catch (Exception e) {
                telemetry.addData("Error", "Exception: " + e.getMessage());
                e.printStackTrace();
            }

            telemetry.update();
            sleep(1000);
        }
    }

    private String getLimelightData() {
        String urlString = "http://" + LIMELIGHT_IP + ":5807/results";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return content.toString();
        } catch (Exception e) {
            telemetry.addData("Error", "Failed to retrieve data: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void parseAndDisplayAprilTagData(String jsonData) {
        try {
            // Print the raw JSON data for debugging purposes
            telemetry.addData("Raw JSON Data", jsonData);

            // Parse JSON response
            JSONObject jsonObj = new JSONObject(jsonData);
            JSONArray targets = jsonObj.getJSONArray("targets");  // Adjust according to actual JSON structure

            for (int i = 0; i < targets.length(); i++) {
                JSONObject tag = targets.getJSONObject(i);
                int id = tag.getInt("id");
                double tx = tag.getDouble("tx");
                double ty = tag.getDouble("ty");
                double ta = tag.getDouble("ta");
                double tz = tag.getDouble("tz");
                double rotation = tag.getDouble("rotation");

                // Calculate distance to the AprilTag using vertical angle and camera/tag heights
                double distance = (CAMERA_HEIGHT - TAG_HEIGHT) / Math.tan(Math.toRadians(ty));

                // Assuming a known position of the AprilTag on the field (x_tag, y_tag)
                double x_tag = 0.0;  // Example field position of the tag in meters
                double y_tag = 0.0;

                // Calculate the robot's position on the field
                double x_robot = x_tag - distance * Math.cos(Math.toRadians(tx));
                double y_robot = y_tag - distance * Math.sin(Math.toRadians(tx));

                // Display the information on the telemetry
                telemetry.addData("Tag ID", id);
                telemetry.addData("tx (deg)", tx);
                telemetry.addData("ty (deg)", ty);
                telemetry.addData("Distance (m)", distance);
                telemetry.addData("Robot Position", "X: " + x_robot + ", Y: " + y_robot);
            }
        } catch (Exception e) {
            telemetry.addData("Error", "JSON Parsing Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
*//*
*/
/*

        package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

@TeleOp(name = "Limelight AprilTag Detection")
public class LimelightDetection extends LinearOpMode {

    private static final String LIMELIGHT_IP = "172.28.2.1"; // Replace with your Limelight's IP address

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            try {
                String response = getLimelightData();
                if (response != null) {
                    parseAndDisplayAprilTagData(response);
                }
            } catch (Exception e) {
                telemetry.addData("Error", "Exception: " + e.getMessage());
                e.printStackTrace();
            }

            telemetry.update();
            sleep(1000);
        }
    }

    private String getLimelightData() {
        String urlString = "http://" + LIMELIGHT_IP + ":5807/results"; // The REST API endpoint for Limelight
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return content.toString();
        } catch (Exception e) {
            telemetry.addData("Error", "Failed to retrieve data: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void parseAndDisplayAprilTagData(String jsonData) {
        try {
            JSONObject jsonObj = new JSONObject(jsonData);
            JSONArray targets = jsonObj.getJSONArray("Results");

            for (int i = 0; i < targets.length(); i++) {
                JSONObject tag = targets.getJSONObject(i);
                int id = tag.getInt("id");
                double x = tag.getDouble("tx");
                double y = tag.getDouble("ty");
                double z = tag.getDouble("tz");
                double rotation = tag.getDouble("rotation");

                telemetry.addData("Tag ID", id);
                telemetry.addData("X", x);
                telemetry.addData("Y", y);
                telemetry.addData("Z", z);
                telemetry.addData("Rotation", rotation);
            }
        } catch (Exception e) {
            telemetry.addData("Error", "JSON Parsing Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

@TeleOp(name = "Limelight AprilTag Detection")
public class LimelightDetection extends LinearOpMode {

    private static final String LIMELIGHT_IP = "172.28.2.1"; // Replace with your Limelight's IP address
    private static final double CAMERA_HEIGHT = 0.8001;  // Example height in meters
    private static final double TAG_HEIGHT = 1.4478;     // Example AprilTag height in meters
    private static final double FIELD_TAG_X = 0.0;       // Known X position of the AprilTag on the field in meters
    private static final double FIELD_TAG_Y = 0.0;       // Known Y position of the AprilTag on the field in meters

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            try {
                String response = getLimelightData();
                if (response != null) {
                    parseAndDisplayAprilTagData(response);
                }
            } catch (Exception e) {
                telemetry.addData("Error", "Exception: " + e.getMessage());
                e.printStackTrace();
            }

            telemetry.update();
            sleep(1000);
        }
    }

    private String getLimelightData() {
        String urlString = "http://" + LIMELIGHT_IP + ":5807/results"; // The REST API endpoint for Limelight
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return content.toString();
        } catch (Exception e) {
            telemetry.addData("Error", "Failed to retrieve data: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void parseAndDisplayAprilTagData(String jsonData) {
        try {
            JSONObject jsonObj = new JSONObject(jsonData);

            // Check if the Fiducial array exists
            if (jsonObj.has("Fiducial")) {
                JSONArray fiducials = jsonObj.getJSONArray("Fiducial");

                for (int i = 0; i < fiducials.length(); i++) {
                    JSONObject tag = fiducials.getJSONObject(i);
                    int id = tag.getInt("fID");
                    double tx = tag.getDouble("tx");
                    double ty = tag.getDouble("ty");


                    // Calculate the distance to the tag based on vertical angle and height
                    double angle = Math.toRadians(ty);
                    double distance = (TAG_HEIGHT - CAMERA_HEIGHT) / Math.tan(angle);

                    // Calculate robot's position relative to the field
                    double robotX = FIELD_TAG_X - distance * Math.cos(Math.toRadians(tx));
                    double robotY = FIELD_TAG_Y - distance * Math.sin(Math.toRadians(tx));

                    // Display the information on the telemetry
                    telemetry.addData("Tag ID", id);
                    telemetry.addData("TX (deg)", tx);
                    telemetry.addData("TY (deg)", ty);
                    telemetry.addData("Distance (m)", distance);
                    telemetry.addData("Robot Position", "X: " + robotX + ", Y: " + robotY);
                }
            } else {
                telemetry.addData("Error", "No Fiducial data found");
            }
        } catch (Exception e) {
            telemetry.addData("Error", "JSON Parsing Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
