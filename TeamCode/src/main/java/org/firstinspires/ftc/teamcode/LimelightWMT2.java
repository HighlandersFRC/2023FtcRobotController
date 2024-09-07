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



public class LimelightWMT2 extends LinearOpMode {

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
            JSONArray targets = jsonObj.getJSONArray("Fiducial");

            for (int i = 0; i < targets.length(); i++) {
                JSONObject tag = targets.getJSONObject(i);
                int id = tag.getInt("fID");
                double x = tag.getDouble("tx");
                double y = tag.getDouble("ty");

                telemetry.addData("Tag ID", id);
                telemetry.addData("X", x);
                telemetry.addData("Y", y);
            }


            if (jsonObj.has("botpose_orb")) {
                JSONArray botPoseArray = jsonObj.getJSONArray("botpose_orb");

                double botposeX = botPoseArray.getDouble(0);
                double botposeY = botPoseArray.getDouble(1);
                double botposeZ = botPoseArray.getDouble(2);
                double roll = botPoseArray.getDouble(3);
                double pitch = botPoseArray.getDouble(4);
                double yaw = botPoseArray.getDouble(5);

                telemetry.addData("BotPose X", botposeX);
                telemetry.addData("BotPose Y", botposeY);
                telemetry.addData("BotPose Z", botposeZ);
                telemetry.addData("BotPose Roll", roll);
                telemetry.addData("BotPose Pitch", pitch);
                telemetry.addData("BotPose Yaw", yaw);
            } else {
                telemetry.addData("Error", "No 'botpose' key found in JSON");
            }

        } catch (Exception e) {
            telemetry.addData("Error", "JSON Parsing Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
