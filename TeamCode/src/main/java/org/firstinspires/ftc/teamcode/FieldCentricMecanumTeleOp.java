package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.teamcode.Commands.Arm;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;



@TeleOp
public class FieldCentricMecanumTeleOp extends LinearOpMode {
<<<<<<< Updated upstream
=======
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
    private static final String TFOD_MODEL_ASSET = "Centerstage.tflite";
    private static final String[] LABELS = {
            "Pixel"
    };
>>>>>>> Stashed changes

    @Override
    public void runOpMode() throws InterruptedException {
        PID1 PID = new PID1();
        // Declare our motors
        DcMotor Left_Front = hardwareMap.dcMotor.get("Left_Front");
        DcMotor Left_Back = hardwareMap.dcMotor.get("Left_Back");
        DcMotor Right_Front = hardwareMap.dcMotor.get("Right_Front");
        DcMotor Right_Back = hardwareMap.dcMotor.get("Right_Back");
        //I2cDevice navx = hardwareMap.i2cDevice.get("NavX");
        DcMotor Right_Intake = hardwareMap.dcMotor.get("Right_Intake");
        Servo LServo = hardwareMap.servo.get("LServo");
        Servo RServo = hardwareMap.servo.get("RServo");
        DcMotor Arm1 = hardwareMap.dcMotor.get("Arm1");
        DcMotor Arm2 = hardwareMap.dcMotor.get("Arm2");
        CRServo intakeServo = hardwareMap.crservo.get("intakeServo");

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);


<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        // Find a motor in the hardware map named "Arm Motor"

        // Reset the motor encoder so that it reads zero ticks
        Right_Intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Turn the motor back on, required if you use STOP_AND_RESET_ENCODER
        Right_Intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Arm1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Arm2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        waitForStart();
<<<<<<< Updated upstream

=======
        
>>>>>>> Stashed changes
        long timeElapsed = 0;

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double CPR = 288;
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            // Get the current position of the motor
            int position_Right_Intake = Right_Intake.getCurrentPosition();
            int position_Arm1 = Arm1.getCurrentPosition();
            int position_Arm2 = Arm2.getCurrentPosition();
<<<<<<< Updated upstream
            double revolutions_Right_Intake = position_Right_Intake / CPR;
            double revolutions_Arm1 = position_Arm1 / CPR;
            double revolutions_Arm2 = position_Arm2 / CPR;
            double angle_Right_Intake = revolutions_Right_Intake * 360;
            double angle_Arm1 = revolutions_Arm1 * 360;
            double angle_Arm2 = revolutions_Arm2 * 360;
=======
            double revolutions_Right_Intake = position_Right_Intake/CPR;
            double revolutions_Arm1 = position_Arm1/CPR;
            double revolutions_Arm2 = position_Arm2/CPR;
            double angle_Right_Intake = revolutions_Right_Intake * 360;
            double angle_Arm1 = revolutions_Arm1 * 360;
            double angle_Arm2= revolutions_Arm2 * 360;
>>>>>>> Stashed changes
            double angleNormalized_Right_Intake = angle_Right_Intake % 360;
            double angleNormalized_Arm1 = angle_Arm1 % 360;
            double angleNormalized_Arm2 = angle_Arm2 % 360;


<<<<<<< Updated upstream
            timeElapsed = System.currentTimeMillis();
            if (timeElapsed >= 1) {
=======




            timeElapsed  = System.currentTimeMillis();
            if (timeElapsed >= 1){
>>>>>>> Stashed changes

            }
            double leftTrigger = gamepad1.left_trigger;
            double rightTrigger = gamepad1.right_trigger;
            LServo.scaleRange(-180, 180);
            RServo.scaleRange(-180, 180);
            double intakePower = (rightTrigger - leftTrigger) * 0.75;

            PID.setMaxOutput(1);
            PID.setMinOutput(-1);
<<<<<<< Updated upstream
            PID.setPID(0.003, 0, 0.001);
=======
            PID.setPID(0.003,0 ,0.001);
>>>>>>> Stashed changes
            PID.updatePID(Arm1.getCurrentPosition());
            Arm1.setPower(PID.getResult() - 0.001);
            Arm2.setPower(PID.getResult() - 0.001);

<<<<<<< Updated upstream
            if (!(rightTrigger == 0)) {
                intakeServo.setPower(1);
            }
            if (!(leftTrigger == 0)) {
=======
            if (!(rightTrigger == 0)){
                intakeServo.setPower(1);
            }
            if (!(leftTrigger == 0)){
>>>>>>> Stashed changes
                intakeServo.setPower(-1);
            }
            /*
             if (gamepad1.dpad_left){
                 intakeServo.setPower(-1);
             }
             else {
                 if (gamepad1.dpad_right){
                     intakeServo.setPower(1);
                 }
                 else
                 {
                     intakeServo.setPower(0);
                 }
             }
            */
<<<<<<< Updated upstream
            if (gamepad1.dpad_up) {
                LServo.setPosition(-120);
                RServo.setPosition(120);
            }
            if (gamepad1.dpad_down) {
                LServo.setPosition(1);
                RServo.setPosition(-1);
            }
            if (gamepad2.a) {
                PID.setSetPoint(-350);

            }
            if (gamepad2.b) {
                PID.setSetPoint(250);
            }

            if (gamepad2.x) {
                PID.setSetPoint(-75);
            }
            if (gamepad2.y) {
=======
            if (gamepad1.dpad_up){
                LServo.setPosition(-120);
                RServo.setPosition(120);
            }
            if (gamepad1.dpad_down){
                LServo.setPosition(1);
                RServo.setPosition(-1);
            }
            if (gamepad2.a){
                PID.setSetPoint(-350);

            }
            if (gamepad2.b){
                PID.setSetPoint(250);
            }

            if (gamepad2.x){
                PID.setSetPoint(-75);
            }
            if (gamepad2.y){
>>>>>>> Stashed changes
                PID.setSetPoint(-175);
            }
            Right_Intake.setPower(-intakePower);

            if (gamepad1.options) {
                imu.resetYaw();
            }


            double botHeading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            double botHeadingRadian = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            if (botHeadingRadian != 0) {
                // Rotate the movement direction counter to the bot's rotation
                double rotX = x * Math.cos(botHeadingRadian) - y * Math.sin(botHeadingRadian);// Changed to positive due to things(change back when need)
                double rotY = x * Math.sin(botHeadingRadian) + y * Math.cos(botHeadingRadian);//Changed to positive due to things(change back when need)

                rotX = rotX * 1.1;  // Counteract imperfect strafing
                // Denominator is the largest motor power (absolute value) or 1rmn
                // This ensures all the powers maintain the same ratio,
                // but only if at least one is out of the range [-1, 1]
                double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);
                double frontLeftPower = (rotY + rotX + rx) / denominator;
                double backLeftPower = (rotY - rotX + rx) / denominator;
                double frontRightPower = (rotY - rotX - rx) / denominator;
                double backRightPower = (rotY + rotX - rx) / denominator;


<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
                Left_Front.setPower(-frontLeftPower);
                Left_Back.setPower(-backLeftPower);
                Right_Front.setPower(-frontRightPower);
                Right_Back.setPower(backRightPower);
                telemetry.addData("y", y);
                telemetry.addData("x", x);
                telemetry.addData("rx", rx);
                telemetry.addData("rotY", rotY);
                telemetry.addData("rotX", rotX);
                telemetry.addData("parameters", parameters);
                telemetry.addData("IMU", imu);

                telemetry.addData("denominator", denominator);
                telemetry.addData("botHeading", botHeading);
                telemetry.addData("botHeadingRadian", botHeadingRadian);
                telemetry.addData("frontLeftPower", frontLeftPower);
                telemetry.addData("backLeftPower", backLeftPower);
                telemetry.addData("frontRightPower", frontRightPower);
                telemetry.addData("backRightPower", backRightPower);
                telemetry.addData("time", time);

                telemetry.addData("Encoder Position Right Intake", position_Right_Intake);
<<<<<<< Updated upstream
                telemetry.addData("Encoder Position Arm1", position_Arm1);
                telemetry.addData("Encoder Position Arm2", position_Arm2);
                telemetry.addData("Encoder Revolutions Right Intake", revolutions_Right_Intake);
                telemetry.addData("Encoder Revolutions Arm1", revolutions_Arm1);
                telemetry.addData("Encoder Revolutions Arm2", revolutions_Arm2);
                telemetry.addData("Encoder Angle (Degrees) Right Intake", angle_Right_Intake);
                telemetry.addData("Encoder Angle (Degrees) Arm1", angle_Arm1);
                telemetry.addData("Encoder Angle (Degrees) Arm2", angle_Arm2);
                telemetry.addData("Encoder Angle Right Intake - Normalized (Degrees) ", angleNormalized_Right_Intake);
                telemetry.addData("Encoder Angle Arm2- Normalized (Degrees)", angleNormalized_Arm1);
                telemetry.addData("Encoder Angle Arm2- Normalized (Degrees)", angleNormalized_Arm2);

                telemetry.update();
            }
=======
                telemetry.addData("Encoder Position Arm1", position_Arm1 );
                telemetry.addData("Encoder Position Arm2", position_Arm2);
                telemetry.addData("Encoder Revolutions Right Intake", revolutions_Right_Intake );
                telemetry.addData("Encoder Revolutions Arm1", revolutions_Arm1 );
                telemetry.addData("Encoder Revolutions Arm2", revolutions_Arm2);
                telemetry.addData("Encoder Angle (Degrees) Right Intake", angle_Right_Intake );
                telemetry.addData("Encoder Angle (Degrees) Arm1", angle_Arm1 );
                telemetry.addData("Encoder Angle (Degrees) Arm2", angle_Arm2);
                telemetry.addData("Encoder Angle Right Intake - Normalized (Degrees) ", angleNormalized_Right_Intake );
                telemetry.addData("Encoder Angle Arm2- Normalized (Degrees)", angleNormalized_Arm1 );
                telemetry.addData("Encoder Angle Arm2- Normalized (Degrees)", angleNormalized_Arm2);

                telemetry.update();}

>>>>>>> Stashed changes
        }
    }
}

