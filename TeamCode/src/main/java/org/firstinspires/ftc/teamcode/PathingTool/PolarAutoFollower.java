// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.firstinspires.ftc.teamcode.PathingTool;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.Commands.ConditionalCommand;
import org.firstinspires.ftc.teamcode.Commands.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.json.JSONArray;




public class PolarAutoFollower extends SequentialCommandGroup {


    public PolarAutoFollower(CommandScheduler scheduler, JSONObject polarAutoJSON, Drive drive, Peripherals peripherals, HashMap<String, Supplier<Command>> commandMap, HashMap<String, BooleanSupplier> conditionMap) throws Exception {
        super(scheduler);
        JSONArray schedule = polarAutoJSON.getJSONArray("schedule");
        JSONArray paths = polarAutoJSON.getJSONArray("paths");
        for (int i = 0; i < schedule.length(); i++) {
            JSONObject scheduleEntry = schedule.getJSONObject(i);
            if (!scheduleEntry.getBoolean("branched")){
                addCommands(
                        new PolarPathFollower(drive, peripherals, paths.getJSONObject(scheduleEntry.getInt("path")), commandMap, conditionMap, scheduler)
                );
            } else {
                JSONArray onTrueSchedule = scheduleEntry.getJSONObject("branched_path").getJSONArray("on_true");
                JSONArray onFalseSchedule = scheduleEntry.getJSONObject("branched_path").getJSONArray("on_false");
                JSONObject onTrueJSON = new JSONObject();
                JSONObject onFalseJSON = new JSONObject();
                onTrueJSON.put("paths", new JSONArray());
                onFalseJSON.put("paths", new JSONArray());
                for (int j = 0; j < paths.length(); j++){
                    JSONObject path = paths.getJSONObject(j);
                    onTrueJSON.getJSONArray("paths").put(path);
                    onFalseJSON.getJSONArray("paths").put(path);
                }
                onTrueJSON.getJSONArray("paths").remove(0);
                onFalseJSON.getJSONArray("paths").remove(0);
                onTrueJSON.put("schedule", new JSONArray());
                onFalseJSON.put("schedule", new JSONArray());
                for (int j = 0; j < onTrueSchedule.length(); j++){
                    JSONObject sched = onTrueSchedule.getJSONObject(j);
                    onTrueJSON.getJSONArray("schedule").put(sched);
                }
                for (int j = 0; j < onFalseSchedule.length(); j++){
                    JSONObject sched = onFalseSchedule.getJSONObject(j);
                    onFalseJSON.getJSONArray("schedule").put(sched);
                }
                onTrueJSON.getJSONArray("schedule").remove(0);
                onFalseJSON.getJSONArray("schedule").remove(0);
                addCommands(

                );
            }
        }
    }
}
