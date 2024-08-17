package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.PoseMErging;

@TeleOp(name = "PoseMerging Test", group = "Test")
public class PoseMergingTest extends LinearOpMode {

    private PoseMErging poseMerging;

    @Override
    public void runOpMode() {

        poseMerging = new PoseMErging(hardwareMap, telemetry);


        waitForStart();

        while (opModeIsActive()) {

            poseMerging.processTags();

            double fieldX = poseMerging.getFieldX();
            double fieldY = poseMerging.getFieldY();
            double theta = poseMerging.getTheta();


            telemetry.addData("Field X", fieldX);
            telemetry.addData("Field Y", fieldY);
            telemetry.addData("Theta", theta);
            telemetry.update();

            sleep(50);
        }
    }
}
