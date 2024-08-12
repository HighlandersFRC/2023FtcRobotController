package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

@TeleOp
public class LimelightDetection extends LinearOpMode {

    private static final String LIMELIGHT_IP = "192.168.1.10";

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
        String urlString = "http://" + LIMELIGHT_IP + "/getpipe";
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
