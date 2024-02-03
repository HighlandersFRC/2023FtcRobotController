package org.firstinspires.ftc.teamcode.Autos;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Commands.strafe;
import org.firstinspires.ftc.teamcode.Commands.strafeRight;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
@Autonomous
public class navxcmdtest extends LinearOpMode {
    Scheduler scheduler = new Scheduler();
    @Override
    public void runOpMode() {
        waitForStart();

        scheduler.add(new CommandGroup(scheduler,
   new strafe(hardwareMap,.5,.2)
        ));
        while (opModeIsActive())
        {//telemetry.addData("NavXYaw", Peripherals.getYaw());
         telemetry.update();
            scheduler.update();
        }
    }
}