/*
package org.firstinspires.ftc.teamcode.PathingTool;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.firstinspires.ftc.teamcode.Commands.Command;
import org.firstinspires.ftc.teamcode.Commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.Commands.ConditionalCommand;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.TriggerCommand;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Parameters;
import org.firstinspires.ftc.teamcode.Tools.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AutonomousFollower implements Command {
    private JSONObject path;
    private boolean timerStarted = false;
    private double endTime = 0;
    private PurePursuitFollower follower;
    private CommandScheduler scheduler;
    private TriggerCommand followerCommand;
    private Set<String> addedCommandKeys = new HashSet<>();
    private HashMap<String, Supplier<Command>> commandMap;
    private HashMap<String, BooleanSupplier> conditionMap;

    public AutonomousFollower(CommandScheduler scheduler, JSONObject jsonObject) throws JSONException {
        this.path = jsonObject;
        this.scheduler = scheduler;

        follower = new PurePursuitFollower(path.getJSONArray("sampled_points"), false);

        followerCommand = new TriggerCommand(scheduler,
                () -> {
                    try {
                        return follower.pathStartTime <= getPathTime();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                follower,
                () -> false);

        ArrayList<Command> commands = new ArrayList<>();
        commands.add(followerCommand);

        for (int i = 0; i < path.getJSONArray("commands").length(); i++) {
            JSONObject command = path.getJSONArray("commands").getJSONObject(i);
            String commandKey = command.toString(); // Unique representation of the command
            if (!addedCommandKeys.contains(commandKey)) {
                commands.add(addCommandsFromJSON(command, commandMap, conditionMap));
                addedCommandKeys.add(commandKey);
            }
        }

        for (Command command : commands) {
            followerCommand.addCommands(command);
        }
    }


    @Override
    public void start() throws JSONException {
        followerCommand = new TriggerCommand(scheduler,
                () -> {
                    try {
                        return follower.pathStartTime <= getPathTime();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                follower,
                () -> false);

        ArrayList<Command> commands = new ArrayList<>();
        commands.add(followerCommand);

        for (int i = 0; i < path.getJSONArray("commands").length(); i++) {
            JSONObject command = path.getJSONArray("commands").getJSONObject(i);
            String commandKey = command.toString(); // Unique representation of the command
            if (!addedCommandKeys.contains(commandKey)) {
                commands.add(addCommandsFromJSON(command, commandMap, conditionMap));
                addedCommandKeys.add(commandKey);
            }
        }

        for (Command command : commands) {
            followerCommand.addCommands(command);
        }
    }

    @Override
    public void execute() throws JSONException {

        double currentPathTime = getPathTime();

        followerCommand = new TriggerCommand(scheduler,
                () -> {
                    try {
                        return follower.pathStartTime <= getPathTime();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                follower,
                () -> false);

        ArrayList<Command> commands = new ArrayList<>();
        commands.add(followerCommand);

        for (int i = 0; i < path.getJSONArray("commands").length(); i++) {
            JSONObject command = path.getJSONArray("commands").getJSONObject(i);
            String commandKey = command.toString(); // Unique representation of the command
            if (!addedCommandKeys.contains(commandKey)) {
                commands.add(addCommandsFromJSON(command, commandMap, conditionMap));
                addedCommandKeys.add(commandKey);
            }
        }

        for (Command command : commands) {
            followerCommand.addCommands(command);
        }

    }

    @Override
    public void end() {
    }

    @Override
    public boolean isFinished() {
        return follower.isFinished();
    }
    private Command addCommandsFromJSON(JSONObject command, HashMap<String, Supplier<Command>> commandMap,
                                        HashMap<String, BooleanSupplier> conditionMap) throws JSONException {
        if (command.has("command")) {
            return createTriggerCommand(command, commandMap);
        } else if (command.has("branched_command")) {
            return createBranchedCommand(command, commandMap, conditionMap);
        } else if (command.has("parallel_command_group")) {
            return createParallelCommandGroup(command, commandMap, conditionMap);
        } else if (command.has("parallel_deadline_group")) {
            return createParallelDeadlineGroup(command, commandMap, conditionMap);
        } else if (command.has("parallel_race_group")) {
            return createParallelRaceGroup(command, commandMap, conditionMap);
        } else if (command.has("sequential_command_group")) {
            return createSequentialCommandGroup(command, commandMap, conditionMap);
        } else {
            throw new IllegalArgumentException("Invalid command JSON: " + command.toString());
        }
    }

    private TriggerCommand createTriggerCommand(JSONObject command, HashMap<String, Supplier<Command>> commandMap) throws JSONException {
        BooleanSupplier startSupplier = () -> {
            try {
                return command.getDouble("start") < getPathTime();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        };
        BooleanSupplier endSupplier = () -> {
            try {
                return command.getDouble("end") <= getPathTime();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        };
        return new TriggerCommand(scheduler, startSupplier, Objects.requireNonNull(commandMap.get(command.getJSONObject("command").getString("name"))).get(), endSupplier);
    }

    private Command createBranchedCommand(JSONObject command, HashMap<String, Supplier<Command>> commandMap,
                                          HashMap<String, BooleanSupplier> conditionMap) throws JSONException {
        BooleanSupplier startSupplier = () -> {
            try {
                return command.getDouble("start") < getPathTime();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        };
        BooleanSupplier endSupplier = () -> {
            try {
                return command.getDouble("end") <= getPathTime();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        };
        JSONObject branchedCommand = command.getJSONObject("branched_command");
        JSONObject onTrue = branchedCommand.getJSONObject("on_true");
        JSONObject onFalse = branchedCommand.getJSONObject("on_false");
        BooleanSupplier condition = conditionMap.get(branchedCommand.getString("condition"));

        return new TriggerCommand(
                scheduler,
                startSupplier,
                new ConditionalCommand(
                        condition,
                        createTriggerCommand(onTrue, commandMap),
                        createTriggerCommand(onFalse, commandMap)
                ),
                endSupplier
        );
    }

    private ParallelCommandGroup createParallelCommandGroup(JSONObject command, HashMap<String, Supplier<Command>> commandMap,
                                                            HashMap<String, BooleanSupplier> conditionMap) throws JSONException {
        ArrayList<Command> commands = new ArrayList<>();
        for (int i = 0; i < command.getJSONObject("parallel_command_group").getJSONArray("commands").length(); i++) {
            commands.add(addCommandsFromJSON(
                    command.getJSONObject("parallel_command_group").getJSONArray("commands").getJSONObject(i),
                    commandMap, conditionMap));
        }
        return new ParallelCommandGroup(
                scheduler, Parameters.ALL,
                commands.toArray(new Command[0])
        );
    }

    private ParallelCommandGroup createParallelDeadlineGroup(JSONObject command, HashMap<String, Supplier<Command>> commandMap,
                                                             HashMap<String, BooleanSupplier> conditionMap) throws JSONException {
        Command deadlineCommand = addCommandsFromJSON(
                command.getJSONObject("parallel_deadline_group").getJSONArray("commands").getJSONObject(0),
                commandMap, conditionMap);
        ArrayList<Command> commands = new ArrayList<>();
        for (int i = 1; i < command.getJSONObject("parallel_deadline_group").getJSONArray("commands").length(); i++) {
            commands.add(addCommandsFromJSON(
                    command.getJSONObject("parallel_deadline_group").getJSONArray("commands").getJSONObject(i),
                    commandMap, conditionMap));
        }
        return new ParallelCommandGroup(scheduler, Parameters.SPECIFIC, deadlineCommand, commands.get(0));
    }

    private ParallelCommandGroup createParallelRaceGroup(JSONObject command, HashMap<String, Supplier<Command>> commandMap,
                                                         HashMap<String, BooleanSupplier> conditionMap) throws JSONException {
        ArrayList<Command> commands = new ArrayList<>();
        for (int i = 0; i < command.getJSONObject("parallel_race_group").getJSONArray("commands").length(); i++) {
            commands.add(addCommandsFromJSON(
                    command.getJSONObject("parallel_race_group").getJSONArray("commands").getJSONObject(i),
                    commandMap, conditionMap));
        }
        return new ParallelCommandGroup(scheduler, Parameters.ANY, commands.toArray(new Command[0]));
    }

    private SequentialCommandGroup createSequentialCommandGroup(JSONObject command, HashMap<String, Supplier<Command>> commandMap,
                                                                HashMap<String, BooleanSupplier> conditionMap) throws JSONException {
        ArrayList<Command> commands = new ArrayList<>();
        for (int i = 0; i < command.getJSONObject("sequential_command_group").getJSONArray("commands").length(); i++) {
            commands.add(addCommandsFromJSON(
                    command.getJSONObject("sequential_command_group").getJSONArray("commands").getJSONObject(i),
                    commandMap, conditionMap));
        }
        return new SequentialCommandGroup(scheduler, commands.toArray(new Command[0]));
    }

    private double getPathTime() throws JSONException {
        double retval;
        if (follower.isFinished()) {
            if (!timerStarted) {
                endTime = System.currentTimeMillis();
                timerStarted = true;
            }
            retval = System.currentTimeMillis() - endTime;
            retval += path.getJSONArray("sampled_points")
                    .getJSONObject(path.getJSONArray("sampled_points").length() - 1).getDouble("time");
        } else {
            timerStarted = false;
            retval = path.getJSONArray("sampled_points")
                    .getJSONObject(follower.getPathPointIndex()).getDouble("time");
        }
        return retval;
    }
}
*/
