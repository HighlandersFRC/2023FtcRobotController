package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp
public class NavxMXP extends LinearOpMode {
    //private DcMotor Left_Front;
    //private DcMotor Right_Front;
    //private DcMotor Left_Back;
    //private DcMotor Right_Back;
    public Orientation heading,angle;
    private NavxMicroNavigationSensor navxMXP;
    private AHRS navx;
    @Override
    public void runOpMode() throws InterruptedException {
        //hardware map
        //Left_Front = hardwareMap.dcMotor.get("Left_Front");
        //Right_Front = hardwareMap.dcMotor.get("Right_Front");
        //Left_Back = hardwareMap.dcMotor.get("Left_Back");
        //Right_Back = hardwareMap.dcMotor.get("Right_Back");
        waitForStart();
        while (opModeIsActive()) {
            //variables
            //double drive = gamepad1.left_stick_y;
            //double strafe = gamepad1.left_stick_x;
            //double twist = gamepad1.right_stick_x;
            //double pi = 3.1415926;
            //angle= navxMXP.getAngularOrientation(AxesReference.INTRINSIC,AxesOrder.ZYX,AngleUnit.DEGREES);
            //double gyro_degrees = heading.firstAngle;
            //double gyro_radians = gyro_degrees * pi / 180;
            telemetry.addData("test angle", navxMXP.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES));
            //2D array!!!
            /*double[] speeds = {
                    (drive + strafe + twist),
                    (drive - strafe - twist),
                    (drive - strafe + twist),
                    (drive + strafe - twist)
            };
            strafe = strafe * Math.cos(gyro_radians) - drive * Math.sin(gyro_radians);
            drive = strafe * Math.sin(gyro_radians) + drive * Math.cos(gyro_radians);
            //Set power
            Left_Front.setPower(speeds[0]);
            Right_Front.setPower(speeds[1]);
            Left_Back.setPower(speeds[2]);
            Right_Back.setPower(speeds[3]);

             */
            telemetry.update();

        }
    }
}
