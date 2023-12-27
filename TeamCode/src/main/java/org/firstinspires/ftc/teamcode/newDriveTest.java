package org.firstinspires.ftc.teamcode;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public  class newDriveTest extends LinearOpMode {
    private DcMotor Left_Front;
    private DcMotor Right_Front;
    private DcMotor Left_Back;
    private DcMotor Right_Back;
    private AHRS navX;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor Left_Front = hardwareMap.dcMotor.get("Left_Front");
        DcMotor Left_Back = hardwareMap.dcMotor.get("Left_Back");
        DcMotor Right_Front = hardwareMap.dcMotor.get("Right_Front");
        DcMotor Right_Back = hardwareMap.dcMotor.get("Right_Back");
        navX = AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), AHRS.DeviceDataType.kProcessedData);
        waitForStart();
        while (opModeIsActive()) {
            double pi = 3.1415926;
            double gyro_degrees = navX.getYaw();
            double gyro_radians = gyro_degrees * pi/180;
            double drive = gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double twist = gamepad1.right_stick_x;
            //drive =  drive * cos(gyro_radians) + strafe * sin(gyro_radians);
            //strafe = -drive * sin(gyro_radians)+strafe * cos(gyro_radians);
            double[] speeds = {
                    (drive + strafe + twist),
                    (drive - strafe - twist),
                    (drive - strafe + twist),
                    (drive + strafe - twist)
            };
            if (gamepad1.options) {
                navX.zeroYaw();
            }
            Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
            Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
            Left_Front.setDirection(DcMotorSimple.Direction.REVERSE);
            Left_Back.setDirection(DcMotorSimple.Direction.REVERSE);
            Left_Front.setPower(speeds[0]);
            Right_Front.setPower(speeds[1]);
            Left_Back.setPower(speeds[2]);
            Right_Back.setPower(speeds[3]);
            telemetry.addData("YAW", navX.getYaw());
            telemetry.addData("radians",gyro_radians);
            telemetry.addData("pitch",navX.getPitch());
            telemetry.addData("roll",navX.getRoll());
            telemetry.update();
        }
    }
}
