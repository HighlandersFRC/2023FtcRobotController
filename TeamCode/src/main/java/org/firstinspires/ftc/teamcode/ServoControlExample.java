package org.firstinspires.ftc.teamcode;// Import necessary classes from the FTC SDK
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// Define your custom OpMode class
@TeleOp(name = "ServoControlExample", group = "FTC")
public class ServoControlExample extends LinearOpMode {

    // Declare servo and other variables
    private Servo rotationServo;
    private ElapsedTime runtime = new ElapsedTime();
    Servo servo1;
    double servoPosition = 0.0;


    @Override
    public void runOpMode() {

        // Initialize servo
        rotationServo = hardwareMap.get(Servo.class, "servo1");

        // Wait for the start button to be pressed on the driver station
        waitForStart();

        // Main loop
        while (opModeIsActive()) {

            // Control the servo position
            double targetPosition = 0.5;  // Adjust this value as needed
            rotationServo.setPosition(targetPosition);

{

                while (opModeIsActive()) {

                    // Add telemetry to display servo position on the driver station
                    telemetry.addData("Servo Position", rotationServo.getPosition());
                    telemetry.addData("Status", "Initialized");
                    servo1 = hardwareMap.servo.get("servo1");
                    servo1.setPosition(servoPosition);
                    telemetry.addData("Status", "Run Time: " + runtime.toString());
                    telemetry.update();

                    if (gamepad2.a) {
                        servoPosition = 1.0;
                        servo1.setPosition(servoPosition);


                    } else {
                        servoPosition = 0.0;
                        servo1.setPosition(servoPosition);

                    }

                    // Sleep for a short period to avoid continuous servo movement
                    sleep(1);  // Adjust the sleep time as needed
                }
            }
        }
    }
}