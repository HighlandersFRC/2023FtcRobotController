import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Tools.Odometry;

@Autonomous
public class Test extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
Odometry.initialize(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                Odometry.resetEncoders();
            }

            telemetry.addData("X", Odometry.getX());
            telemetry.addData("Y", Odometry.getY());
            telemetry.addData("Theta", Odometry.getTheta());
            telemetry.addData("Left Encoder", Odometry.leftEncoderMotor.getCurrentPosition());
            telemetry.addData("Right Encoder", Odometry.rightEncoderMotor.getCurrentPosition());
            telemetry.addData("Y", Odometry.centerEncoderMotor.getCurrentPosition());
            Odometry.update();
            telemetry.update();
        }
    }
}
