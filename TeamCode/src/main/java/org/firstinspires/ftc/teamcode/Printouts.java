package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Tools.FinalPose;
import org.firstinspires.ftc.teamcode.Tools.Robot;
@TeleOp
public class Printouts extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.initialize(hardwareMap);
        while (opModeIsActive()) {
            FinalPose.poseUpdate();
        }
    }
}
