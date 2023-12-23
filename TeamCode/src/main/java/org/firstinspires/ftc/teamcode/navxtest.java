package org.firstinspires.ftc.teamcode;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp
public class navxtest extends LinearOpMode {

    private AHRS navX;
    @Override
    public void runOpMode() throws InterruptedException {
        navX = AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navx"), AHRS.DeviceDataType.kProcessedData);
        double pi = 3.1415926;
        double gyro_degrees = navX.getYaw();
        waitForStart();
        while (opModeIsActive()) {
        double gyro_radians = gyro_degrees * pi/180;
        telemetry.addData("YAW", navX.getYaw());
        telemetry.addData("radians",gyro_radians);
        telemetry.addData("pitch",navX.getPitch());
        telemetry.addData("roll",navX.getRoll());
        telemetry.update();
        }
    }
}
