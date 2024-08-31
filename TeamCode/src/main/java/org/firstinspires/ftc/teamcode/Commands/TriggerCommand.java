// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Tools.Parameters;

import java.util.function.BooleanSupplier;



// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TriggerCommand extends SequentialCommandGroup {
    /** Creates a new TriggerCommand. */
    public TriggerCommand(CommandScheduler scheduler, BooleanSupplier startCondition, Command command, BooleanSupplier endCondition) {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
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