import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Odometry;

@Autonomous
public class Test extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Odometry.initialize(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
        Odometry.getPos();
        Odometry.update();
        System.out.println("R_R"+Odometry.getRotationR());
        System.out.println("R_L"+Odometry.getRotationL());
        System.out.println("meterR"+Odometry.getMeterR());
        System.out.println("meterL"+Odometry.getMeterL());
        System.out.println("theta"+Odometry.telemetrydh);
        System.out.println("x"+Odometry.telemetrydx);
        System.out.println("dn1"+Odometry.dn1);
        System.out.println("dn2"+Odometry.dn2);

        }
    }
}
