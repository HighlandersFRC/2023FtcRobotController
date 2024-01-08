package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Elevators;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Subsystems.Wrist;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.PID;

@TeleOp

public class FieldCentric extends LinearOpMode {
    PID ElevatorPID = new PID(0.03, 0.0, 0.0);
    PID ArmPID = new PID(0.001, 0.0, 0.0);
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        Elevators.initialize(hardwareMap);
        DriveTrain.initialize(hardwareMap);
        Arm.initialize(hardwareMap);
        Intake.initialize(hardwareMap);
        Peripherals.initialize(hardwareMap);
        Wrist.initialize(hardwareMap);

        while (opModeIsActive()) {
            ElevatorPID.setMaxOutput(1);
            ElevatorPID.setMinOutput(-1);
            ElevatorPID.updatePID((Elevators.getArmLPosition() + Elevators.getArmRPosition()) / 2);

            ArmPID.setMaxOutput(0.5);
            ArmPID.setMinOutput(-0.5);
            ArmPID.updatePID(Arm.getArmEncoder());

            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double rightTrigger =  gamepad1.right_trigger;
            double leftTrigger =  gamepad1.left_trigger;
            double intakePower = (rightTrigger - leftTrigger);

            if (intakePower > 0){
                intakePower = intakePower / 2;
            }

            double botHeading = -Peripherals.getYaw();
            double pi = 3.1415926;
            double botHeadingRadian = -botHeading * pi/180;

                double rotX = (x * Math.cos(botHeadingRadian) - y * Math.sin(botHeadingRadian));
                double rotY = (x * Math.sin(botHeadingRadian) + y * Math.cos(botHeadingRadian));

                x = x *1.1;

                double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);
                double frontLeftPower = (rotY + rotX + rx) / denominator;
                double backLeftPower = (rotY - rotX + rx) / denominator;
                double frontRightPower = (rotY - rotX - rx) / denominator;
                double backRightPower = (rotY + rotX - rx) / denominator;

                DriveTrain.Drive(frontRightPower, frontLeftPower, backRightPower, backLeftPower);

            if (gamepad1.right_bumper) {
                Peripherals.resetYaw();
            }

            if (gamepad1.a){
                ElevatorPID.setSetPoint(Constants.retractedElevator);
            }

            if (gamepad1.b){
                ElevatorPID.setSetPoint(Constants.deployedElevator);
            }

            if (gamepad2.a){
                ArmPID.setSetPoint(Constants.armIntake);
            }

            if (gamepad2.b){
                ArmPID.setSetPoint(Constants.armPlace);
            }

            if (gamepad2.x){
                Wrist.Wrist(Constants.wristDown);
            }

            if (gamepad2.y){
                Wrist.Wrist(Constants.wristUp);
            }
            if (intakePower == 0) {
                Wrist.Wrist(Constants.wristUp);
            }else{
                Wrist.Wrist(Constants.wristDown);
            }
            Elevators.moveElevatorsUsingPower(ElevatorPID.getResult());
            Arm.rotateArm(ArmPID.getResult());
            Intake.moveMotor(intakePower);
        }
    }
}
//ke qus passcode 9487