package org.firstinspires.ftc.teamcode;
// autonomous program that drives bot forward a set distance, stops then
// backs up to the starting point using encoders to measure the distance.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name="Drive Encoder", group="Exercises")
//@Disabled
public class UseableEncoder extends LinearOpMode
{
    DcMotor leftMotor;
    DcMotor rightMotor;

    @Override
    public void runOpMode() throws InterruptedException
    {
        DcMotor Left_Front = hardwareMap.dcMotor.get("Left_Front");
        DcMotor Left_Back = hardwareMap.dcMotor.get("Left_Back");
        DcMotor Right_Front = hardwareMap.dcMotor.get("Right_Front");
        DcMotor Right_Back = hardwareMap.dcMotor.get("Right_Back");

        // You will need to set this based on your robot's
        // gearing to get forward control input to result in
        // forward motion.
        Left_Front.setDirection(DcMotor.Direction.REVERSE);
        Left_Back.setDirection(DcMotor.Direction.REVERSE);


        // reset encoder counts kept by motors.
        Left_Front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Right_Front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        // set motors to run forward for 5000 encoder counts.
        Left_Front.setTargetPosition(5000);
        Left_Back.setTargetPosition(5000);
        Right_Front.setTargetPosition(5000);
        Right_Back.setTargetPosition(5000);

        // set motors to run to target encoder position and stop with brakes on.
        Left_Front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Left_Back.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Right_Front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Right_Back.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        // wait for start button.

        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

        // set both motors to 25% power. Movement will start. Sign of power is
        // ignored as sign of target encoder position controls direction when
        // running to position.

        Left_Front.setPower(0.25);
        Left_Back.setPower(0.25);
        Right_Front.setPower(0.25);
        Right_Back.setPower(0.25);


        // wait while opmode is active and left motor is busy running to position.

        while (opModeIsActive() && Left_Front.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left-front", Left_Front.getCurrentPosition() + "  busy=" + Left_Front.isBusy());
            telemetry.addData("encoder-fwd-left-back", Left_Back.getCurrentPosition() + "  busy=" + Left_Back.isBusy());
            telemetry.addData("encoder-fwd-right-front", Right_Front.getCurrentPosition() + "  busy=" + Right_Front.isBusy());
            telemetry.addData("encoder-fwd-right-back", Right_Back.getCurrentPosition() + "  busy=" + Right_Back.isBusy());
            telemetry.update();
            idle();
        }
        while (opModeIsActive() && Left_Back.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left-front", Left_Front.getCurrentPosition() + "  busy=" + Left_Front.isBusy());
            telemetry.addData("encoder-fwd-left-back", Left_Back.getCurrentPosition() + "  busy=" + Left_Back.isBusy());
            telemetry.addData("encoder-fwd-right-front", Right_Front.getCurrentPosition() + "  busy=" + Right_Front.isBusy());
            telemetry.addData("encoder-fwd-right-back", Right_Back.getCurrentPosition() + "  busy=" + Right_Back.isBusy());
            telemetry.update();
            idle();
        }
        // set motor power to zero to turn off motors. The motors stop on their own but
        // power is still applied so we turn off the power.

        Left_Front.setPower(0.0);
        Left_Back.setPower(0.0);
        Right_Front.setPower(0.0);
        Right_Back.setPower(0.0);

        // wait 5 sec to you can observe the final encoder position.

        resetRuntime();

        while (opModeIsActive() && getRuntime() < 5)
        {
            telemetry.addData("encoder-fwd-left-front-end", Left_Front.getCurrentPosition());
            telemetry.addData("encoder-fwd-left-back-end", Left_Back.getCurrentPosition());
            telemetry.addData("encoder-fwd-right-front-end", Right_Front.getCurrentPosition());
            telemetry.addData("encoder-fwd-right-back-end", Right_Back.getCurrentPosition());
            telemetry.update();
            idle();
        }

        // From current position back up to starting point. In this example instead of
        // having the motor monitor the encoder we will monitor the encoder ourselves.

        Left_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Left_Front.setTargetPosition(0);
        Left_Back.setTargetPosition(0);
        Right_Front.setTargetPosition(0);
        Right_Back.setTargetPosition(0);

        // Power sign matters again as we are running without encoder.
        Left_Front.setPower(-0.25);
        Left_Back.setPower(-0.25);
        Right_Front.setPower(-0.25);
        Right_Back.setPower(-0.25);

        while (opModeIsActive() && leftMotor.getCurrentPosition() > leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-Left_Front", Left_Front.getCurrentPosition());
            telemetry.addData("encoder-Left_Back", Left_Back.getCurrentPosition());
            telemetry.addData("encoder-Right_Front", Right_Front.getCurrentPosition());
            telemetry.addData("encoder-Right_Back", Right_Back.getCurrentPosition());
            telemetry.update();
            idle();
        }

        // set motor power to zero to stop motors.

        Left_Front.setPower(0.0);
        Left_Back.setPower(0.0);
        Right_Front.setPower(0.0);
        Right_Back.setPower(0.0);
        resetRuntime();

        while (opModeIsActive() && getRuntime() < 5)
        {
            telemetry.addData("encoder-Left_Front-end", Left_Front.getCurrentPosition());
            telemetry.addData("encoder-Left_Back-end", Left_Back.getCurrentPosition());
            telemetry.addData("encoder-Right_Front-end", Right_Front.getCurrentPosition());
            telemetry.addData("encoder-Right_Back-end", Right_Back.getCurrentPosition());
            telemetry.update();
            idle();
        }
    }
}
