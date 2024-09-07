


package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Tools.Parameters;

import java.util.function.BooleanSupplier;

public class TriggerCommand extends SequentialCommandGroup {

    public TriggerCommand(CommandScheduler scheduler, BooleanSupplier startCondition, Command command, BooleanSupplier endCondition) {
        super(scheduler);
        addCommands(
                new WaitForCondition(startCondition),
                new ParallelCommandGroup(
                        scheduler,
                        Parameters.ALL,
                        new WaitForCondition(endCondition)
                )
        );
    }
}