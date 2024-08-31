package org.firstinspires.ftc.teamcode.PathingTool;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Tools.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AutonomousFollower implements Command {
    private DriveSubsystem drive;
    private JSONArray path;
    private double initTime;
    private double currentTime;
    private double previousTime;
    private double odometryFusedX = 0;
    private double odometryFusedY = 0;
    private double odometryFusedTheta = 0;
    private double[] desiredVelocityArray = new double[3];
    private double desiredThetaChange = 0;
    private double pathStartTime;
    private double pathEndTime;

    public AutonomousFollower(DriveSubsystem drive, String pathFileName, double pathStartTime, double pathEndTime, OpMode opMode) {
        loadJSONFromAsset(opMode, pathFileName);
        this.drive = drive;
        this.pathStartTime = pathStartTime;
        this.pathEndTime = pathEndTime;
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
            JSONObject jsonPathData = new JSONObject(sb.toString());
            path = jsonPathData.getJSONArray("sampled_points");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        initTime = System.currentTimeMillis();
    }

    @Override
    public void execute() throws JSONException {
        DriveSubsystem.update();
        odometryFusedX = DriveSubsystem.getOdometryX();
        odometryFusedY = DriveSubsystem.getOdometryY();
        odometryFusedTheta = DriveSubsystem.getOdometryTheta();

        currentTime = (System.currentTimeMillis() - initTime) / 1000.0 + pathStartTime;

        desiredVelocityArray = DriveSubsystem.pidController(odometryFusedX, odometryFusedY, odometryFusedTheta, currentTime, path);

        Vector velocityVector = new Vector();
        velocityVector.setI(desiredVelocityArray[0]);
        velocityVector.setJ(desiredVelocityArray[1]);
        desiredThetaChange = desiredVelocityArray[2];

        drive.autoDrive(velocityVector, desiredThetaChange);

        previousTime = currentTime;
    }

    @Override
    public void end() {
        Vector velocityVector = new Vector();
        velocityVector.setI(0);
        velocityVector.setJ(0);
        double desiredThetaChange = 0.0;
        drive.autoDrive(velocityVector, desiredThetaChange);

        odometryFusedX = DriveSubsystem.getOdometryX();
        odometryFusedY = DriveSubsystem.getOdometryY();
        odometryFusedTheta = DriveSubsystem.getOdometryTheta();
        currentTime = System.currentTimeMillis() - initTime;
    }

    @Override
    public boolean isFinished() {
        return currentTime > pathEndTime;
    }
}
