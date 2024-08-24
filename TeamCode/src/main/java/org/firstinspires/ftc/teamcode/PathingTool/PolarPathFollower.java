/*
package org.firstinspires.ftc.teamcode.PathingTool;

import java.util.ArrayList;
import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Parameters;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PolarPathFollower extends ParallelCommandGroup {

  static PurePursuitFollower follower;
  static double endTime = 0;
  static boolean timerStarted = false;
  static JSONObject pathJSON;

  // Constructor for PolarPathFollower
  public PolarPathFollower(CommandScheduler scheduler, Parameters parameter, Drive drive, Peripherals peripherals, JSONObject pathJSON) throws JSONException {
    super(scheduler, parameter); // Call to the constructor of ParallelCommandGroup

    // Initialize the PurePursuitFollower
    follower = new PurePursuitFollower(drive, pathJSON.getJSONArray("sampled_points"));
    this.pathJSON = pathJSON;

    // Add the PurePursuitFollower to the list of commands
    addCommand(follower);

    // Add any additional commands from the pathJSON
    JSONArray commandArray = pathJSON.getJSONArray("commands");
    for (int i = 0; i < commandArray.length(); i++) {
      JSONObject command = commandArray.getJSONObject(i);
      // Instantiate and add commands based on the JSON data (not implemented in this example)
      // Example: addCommand(new SomeCommand());
    }
  }

  // Method to get the current path time
  public static double getPathTime() throws JSONException {
    double retval;
    if (follower.isFinished()) {
      if (!timerStarted) {
        endTime = System.currentTimeMillis();
        timerStarted = true;
      }
      retval = System.currentTimeMillis() - endTime + pathJSON.getJSONArray("sampled_points")
              .getJSONObject(pathJSON.getJSONArray("sampled_points").length() - 1).getDouble("time");
    } else {
      retval = pathJSON.getJSONArray("sampled_points")
              .getJSONObject(follower.getPathPointIndex()).getDouble("time");
    }
    return retval;
  }
}
*/
