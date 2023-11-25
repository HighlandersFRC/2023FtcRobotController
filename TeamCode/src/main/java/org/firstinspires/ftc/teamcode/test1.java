package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMUNew;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
//import com.qualcomm.robotcore.hardware.IMU.Parameters;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;


@TeleOp
public class test1 extends LinearOpMode {

    private DcMotor Left_Front;
    private DcMotor Right_Front;
    private DcMotor Left_Back;
    private DcMotor Right_Back;
    private DcMotor Arm1;
    private IMU imu;
    @Override

    public void runOpMode() {
        Left_Front = hardwareMap.dcMotor.get("Left_Front");
        Right_Front = hardwareMap.dcMotor.get("Right_Front");
        Left_Back = hardwareMap.dcMotor.get("Left_Back");
        Right_Back = hardwareMap.dcMotor.get("Right_Back");
        Arm1 = hardwareMap.dcMotor.get("Arm1");
        imu = hardwareMap.get(IMU.class, "imu");

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        IMU.Parameters myIMUparameters;

        myIMUparameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        new Orientation(
                                AxesReference.INTRINSIC,
                                AxesOrder.ZYX,
                                AngleUnit.DEGREES,
                                0,
                                0,
                                0,
                                0  // acquisitionTime, not used
                        )
                )
        );

        // Two methods for Initializing the IMU:
// Initialize IMU directly
        imu.initialize(
                new IMU.Parameters(
                        new RevHubOrientationOnRobot(
                                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                        )
                )
        );
        // Create an object to receive the IMU angles
        YawPitchRollAngles robotOrientation;
        robotOrientation = imu.getRobotYawPitchRollAngles();

// Now use these simple methods to extract each angle
// (Java type double) from the object you just created:
        double Yaw   = robotOrientation.getYaw(AngleUnit.DEGREES);
        double Pitch = robotOrientation.getPitch(AngleUnit.DEGREES);
        double Roll  = robotOrientation.getRoll(AngleUnit.DEGREES);
        // Create Orientation variable
        Orientation myRobotOrientation;

// Get Robot Orientation
        myRobotOrientation = imu.getRobotOrientation(
                AxesReference.INTRINSIC,
                AxesOrder.XYZ,
                AngleUnit.DEGREES
        );

// Then read or display the desired values (Java type float):
System.out.println();




// Initialize IMU using Parameters
        imu.initialize(myIMUparameters);

        while (opModeIsActive()) {
            float X_axis = myRobotOrientation.firstAngle;
            float Y_axis = myRobotOrientation.secondAngle;
            float Z_axis = myRobotOrientation.thirdAngle;
// Create angular velocity array variable
            AngularVelocity myRobotAngularVelocity;

// Read Angular Velocities
            myRobotAngularVelocity = imu.getRobotAngularVelocity(AngleUnit.DEGREES);

// Then read or display these values (Java type float)
// from the object you just created:
            float zRotationRate = myRobotAngularVelocity.zRotationRate;
            float xRotationRate = myRobotAngularVelocity.xRotationRate;
            float yRotationRate = myRobotAngularVelocity.yRotationRate;
            // Reset Yaw
            imu.resetYaw();


            double vertical;
                double horizontal;
                double pivot;

//                IMU.getRobotYawPitchAngles
            // The codes to define the wheels way
                vertical = gamepad1.left_stick_y;
                horizontal = -gamepad1.left_stick_x;
                pivot = -gamepad1.right_stick_x;
            //The code for the Mecanum drive
                Right_Front.setPower(pivot+( vertical - horizontal));
                Right_Back.setPower(pivot+( vertical + horizontal));
                Left_Front.setPower(pivot+( -vertical - horizontal));
                Left_Back.setPower(pivot+( -vertical + horizontal));
                System.out.println(imu);


                if (gamepad1.a) {
                    Arm1.setPower(1);
                    Arm1.setTargetPosition(-950);
                    Arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                } else {

                    Arm1.setPower(0);

                }
                if (gamepad1.b){
                    Arm1.setPower(1);
                    Arm1.setTargetPosition(-15);
                    Arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }else {

                    Arm1.setPower(0);

                }
                //printing out bunch of useless things.
            imu.getRobotYawPitchRollAngles();
            telemetry.addData("Y Rotation Rate", yRotationRate);
            telemetry.addData("IMU Position", imu.getRobotYawPitchRollAngles());
            telemetry.addData("Yaw", Yaw);
            telemetry.addData("Pitch", Pitch);
            telemetry.addData("Roll", Roll);
            telemetry.update();
        }
    }
}
