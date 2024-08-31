package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

@TeleOp(name = "MegaTag 2 Localization")
public class MegaTag2 extends LinearOpMode {

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
                    parseAndDisplayMegaTagData(response);
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

    private void parseAndDisplayMegaTagData(String jsonData) {
        try {
            JSONObject jsonObj = new JSONObject(jsonData);

            // Check if the MegaTag 2 data is present
            if (jsonObj.has("MegaTag2")) {
                JSONObject megaTag2 = jsonObj.getJSONObject("MegaTag2");

                int id = megaTag2.getInt("id");
                double tx = megaTag2.getDouble("tx");
                double ty = megaTag2.getDouble("ty");
                double tz = megaTag2.getDouble("tz");
                double rx = megaTag2.getDouble("rx");
                double ry = megaTag2.getDouble("ry");
                double rz = megaTag2.getDouble("rz");

                // Display the MegaTag 2 data on the telemetry
                telemetry.addData("MegaTag2 ID", id);
                telemetry.addData("TX (deg)", tx);
                telemetry.addData("TY (deg)", ty);
                telemetry.addData("TZ (deg)", tz);
                telemetry.addData("RX (deg)", rx);
                telemetry.addData("RY (deg)", ry);
                telemetry.addData("RZ (deg)", rz);

                // Assuming you want to calculate the robot's position using tx, ty, and tz
                double robotX = calculateRobotX(tx, ty, tz);
                double robotY = calculateRobotY(tx, ty, tz);
                double robotTheta = calculateRobotTheta(rx, ry, rz);

                telemetry.addData("Robot Position", "X: " + robotX + ", Y: " + robotY + ", Theta: " + robotTheta);
            } else {
                telemetry.addData("Error", "No MegaTag2 data found");
            }
        } catch (Exception e) {
            telemetry.addData("Error", "JSON Parsing Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private double calculateRobotX(double tx, double ty, double tz) {
        // Implement your calculation for robot X based on tx, ty, and tz
        return 0.0; // Placeholder
    }

    private double calculateRobotY(double tx, double ty, double tz) {
        // Implement your calculation for robot Y based on tx, ty, and tz
        return 0.0; // Placeholder
    }

    private double calculateRobotTheta(double rx, double ry, double rz) {
        // Implement your calculation for robot Theta based on rx, ry, and rz
        return 0.0; // Placeholder
    }
}
