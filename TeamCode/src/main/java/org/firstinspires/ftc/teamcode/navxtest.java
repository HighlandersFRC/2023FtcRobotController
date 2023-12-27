package org.firstinspires.ftc.teamcode;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
@TeleOp
public class navxtest extends LinearOpMode {
    private AHRS navX;
    private DcMotor Left_Front,Left_Back,Right_Front,Right_Back;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor Left_Front = hardwareMap.dcMotor.get("Left_Front");
        DcMotor Left_Back = hardwareMap.dcMotor.get("Left_Back");
        DcMotor Right_Front = hardwareMap.dcMotor.get("Right_Front");
        DcMotor Right_Back = hardwareMap.dcMotor.get("Right_Back");
        navX = AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navx"), AHRS.DeviceDataType.kProcessedData);
        double pi = 3.1415926;
        double gyro_degrees = navX.getYaw();
        
        double gyro_radians = gyro_degrees * pi/180;
        double rcw = gamepad1.right_stick_x;
        double forwrd = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double temp =  forwrd * cos(gyro_radians) + strafe * sin(gyro_radians);
        strafe = -forwrd * sin(gyro_radians)+strafe * cos(gyro_radians);
        waitForStart();
        while (opModeIsActive()) {
            double denominator = Math.max(Math.abs(strafe) + Math.abs(temp) + Math.abs(rcw), 1.0);
            double frontLeftPower = (strafe + temp + rcw) / denominator;
            double backLeftPower = (strafe - temp + rcw) / denominator;
            double frontRightPower = (strafe - temp - rcw) / denominator;
            double backRightPower = (strafe + temp - rcw) / denominator;
            Left_Front.setPower(frontLeftPower);
            Left_Back.setPower(backLeftPower);
            Right_Front.setPower(frontRightPower);
            Right_Back.setPower(backRightPower);
        telemetry.addData("YAW", navX.getYaw());
        telemetry.addData("radians",gyro_radians);
        telemetry.addData("pitch",navX.getPitch());
        telemetry.addData("roll",navX.getRoll());
        telemetry.update();
        }
    }
}
