// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.firstinspires.ftc.teamcode.Commands;

import java.util.function.BooleanSupplier;


public class WaitForCondition implements Command {
    private final BooleanSupplier condition;
    public WaitForCondition(BooleanSupplier condition) {
        this.condition = condition;
    }


    @Override
    public void start() {

    }

    @Override
    public void execute() {
    }

    @Override
    public void end() {

    }



    @Override
    public boolean isFinished() {
        return condition.getAsBoolean();
    }
}