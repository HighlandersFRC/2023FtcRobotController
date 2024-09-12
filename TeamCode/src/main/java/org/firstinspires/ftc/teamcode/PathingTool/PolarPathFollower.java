package org.firstinspires.ftc.teamcode.PathingTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.firstinspires.ftc.teamcode.Commands.*;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Tools.Parameters;
import org.json.JSONException;
import org.json.JSONObject;

public class PolarPathFollower extends ParallelCommandGroup {
    private PurePursuitFollower follower;
    private double endTime = 0;
    private boolean timerStarted = false;
    private JSONObject pathJSON;
    private TriggerCommand followerCommand;
    private CommandScheduler scheduler;
    private Set<String> addedCommandKeys = new HashSet<>();

    public PolarPathFollower(Drive drive, Peripherals peripherals, JSONObject pathJSON,
                             HashMap<String, Supplier<Command>> commandMap, HashMap<String, BooleanSupplier> conditionMap, CommandScheduler scheduler) throws Exception {
        super(scheduler, Parameters.ALL);

        this.scheduler = scheduler;
        this.pathJSON = pathJSON;

        System.out.println("Loaded Path JSON: " + pathJSON.toString(2)); // Print JSON for debugging

        follower = new PurePursuitFollower(drive, peripherals, pathJSON.getJSONArray("sampled_points"), false);

        followerCommand = new TriggerCommand(scheduler,
                () -> {
                    return follower.pathStartTime <= getPathTime();
                },
                follower,
                () -> false);

        // Process commands and ensure no duplicates are added
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(followerCommand);

        for (int i = 0; i < pathJSON.getJSONArray("commands").length(); i++) {
            JSONObject command = pathJSON.getJSONArray("commands").getJSONObject(i);
            String commandKey = command.toString();
            System.out.println("Processing Command JSON: " + command.toString(2)); // Print each command for debugging
            if (!addedCommandKeys.contains(commandKey)) {
                Command newCommand = addCommandsFromJSON(command, commandMap, conditionMap);
                if (newCommand != null) {
                    commands.add(newCommand);
                    addedCommandKeys.add(commandKey);
                }
            }
        }

        // Add all commands to the ParallelCommandGroup
        for (Command command : commands) {
            System.out.println("Adding Command: " + command.getClass().getSimpleName()); // Print command being added
            addCommands(command); // Ensure addCommands accepts single Command
        }
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
        ParallelCommandGroup parallelGroup = new ParallelCommandGroup(scheduler, Parameters.ALL);

        for (int i = 0; i < command.getJSONObject("parallel_command_group").getJSONArray("commands").length(); i++) {
            parallelGroup.addCommands(addCommandsFromJSON(
                    command.getJSONObject("parallel_command_group").getJSONArray("commands").getJSONObject(i),
                    commandMap, conditionMap));
        }
        return parallelGroup;
    }

    private ParallelCommandGroup createParallelDeadlineGroup(JSONObject command, HashMap<String, Supplier<Command>> commandMap,
                                                             HashMap<String, BooleanSupplier> conditionMap) throws JSONException {
        Command deadlineCommand = addCommandsFromJSON(
                command.getJSONObject("parallel_deadline_group").getJSONArray("commands").getJSONObject(0),
                commandMap, conditionMap);
        ParallelCommandGroup parallelGroup = new ParallelCommandGroup(scheduler, Parameters.SPECIFIC, deadlineCommand);

        for (int i = 1; i < command.getJSONObject("parallel_deadline_group").getJSONArray("commands").length(); i++) {
            parallelGroup.addCommands(addCommandsFromJSON(
                    command.getJSONObject("parallel_deadline_group").getJSONArray("commands").getJSONObject(i),
                    commandMap, conditionMap));
        }
        return parallelGroup;
    }

    private ParallelCommandGroup createParallelRaceGroup(JSONObject command, HashMap<String, Supplier<Command>> commandMap,
                                                         HashMap<String, BooleanSupplier> conditionMap) throws JSONException {
        ParallelCommandGroup parallelGroup = new ParallelCommandGroup(scheduler, Parameters.ANY);

        for (int i = 0; i < command.getJSONObject("parallel_race_group").getJSONArray("commands").length(); i++) {
            parallelGroup.addCommands(addCommandsFromJSON(
                    command.getJSONObject("parallel_race_group").getJSONArray("commands").getJSONObject(i),
                    commandMap, conditionMap));
        }
        return parallelGroup;
    }

    private SequentialCommandGroup createSequentialCommandGroup(JSONObject command, HashMap<String, Supplier<Command>> commandMap,
                                                                HashMap<String, BooleanSupplier> conditionMap) throws JSONException {
        SequentialCommandGroup sequentialGroup = new SequentialCommandGroup(scheduler);

        for (int i = 0; i < command.getJSONObject("sequential_command_group").getJSONArray("commands").length(); i++) {
            sequentialGroup.addCommands(addCommandsFromJSON(
                    command.getJSONObject("sequential_command_group").getJSONArray("commands").getJSONObject(i),
                    commandMap, conditionMap));
        }
        return sequentialGroup;
    }

    private double getPathTime() {
        return (System.currentTimeMillis() - follower.pathStartTime) / 1000.0;
    }
}
