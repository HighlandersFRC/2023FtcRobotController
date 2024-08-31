import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;


@Autonomous
public class Test extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
DriveSubsystem.initialize(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                DriveSubsystem.resetEncoders();
            }

            telemetry.addData("X", DriveSubsystem.getOdometryX());
            telemetry.addData("Y", DriveSubsystem.getOdometryY());
            telemetry.addData("Theta", DriveSubsystem.getOdometryTheta());
            telemetry.addData("Left Encoder", DriveSubsystem.leftEncoderMotor.getCurrentPosition());
            telemetry.addData("Right Encoder", DriveSubsystem.rightEncoderMotor.getCurrentPosition());
            telemetry.addData("Y Encoder", DriveSubsystem.centerEncoderMotor.getCurrentPosition());
            DriveSubsystem.update();
            telemetry.update();
        }
    }
}
