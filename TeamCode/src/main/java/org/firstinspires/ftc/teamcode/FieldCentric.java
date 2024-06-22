package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Elevators;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.LEDLights;
import org.firstinspires.ftc.teamcode.Subsystems.Peripherals;
import org.firstinspires.ftc.teamcode.Subsystems.Wrist;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Tools.PID;

@TeleOp

public class FieldCentric extends LinearOpMode {
    PID ElevatorPIDL = new PID(0.0007, 0.0, 0.0007);
    PID ElevatorPIDR = new PID(0.0007, 0.0, 0.0007);
    PID ArmPID = new PID(0.0015, 0.0, 0.0018);
    @Override
    public void runOpMode() {

        waitForStart();

        Elevators.initialize(hardwareMap);
        DriveTrain.initialize(hardwareMap);
        Arm.initialize(hardwareMap);
        Intake.initialize(hardwareMap);
        Peripherals.initialize(hardwareMap);
        Wrist.initialize(hardwareMap);
        LEDLights.initialize(hardwareMap);

        Elevators.resetEncoders();

        LEDLights.setModeFire();

        while (opModeIsActive()) {

            double rightTrigger =  gamepad1.right_trigger;
            double leftTrigger =  gamepad1.left_trigger;
            double intakePower = -(leftTrigger - rightTrigger);

            boolean isArmUp = false;

            if (intakePower < 0) {
                intakePower = intakePower / 2;
            }

            Arm.rotateArm(ArmPID.getResult());
            Intake.moveMotor(intakePower);

            ElevatorPIDL.setMaxOutput(0.75);
            ElevatorPIDL.setMinOutput(-0.75);
            ElevatorPIDL.updatePID(Elevators.getArmLPosition());
            ElevatorPIDR.setMaxOutput(0.75);
            ElevatorPIDR.setMinOutput(-0.75);
            ElevatorPIDR.updatePID(Elevators.getArmRPosition());
            ElevatorPIDL.setMinInput(0);
            ElevatorPIDR.setMinInput(0);
            ElevatorPIDL.setMinInput(2700);
            ElevatorPIDR.setMaxInput(2700);

            ArmPID.setMaxOutput(0.5);
            ArmPID.setMinOutput(-0.5);
            ArmPID.updatePID(Arm.getArmEncoder());

            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double botHeading = Peripherals.getYaw();
            double pi = Math.PI;
            double botHeadingRadian = -botHeading * pi/180;

            double rotX = (x * Math.cos(botHeadingRadian) - y * Math.sin(botHeadingRadian));
            double rotY = (x * Math.sin(botHeadingRadian) + y * Math.cos(botHeadingRadian));

            x = x *1.1;

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            if (y==0 && x==0 &&rx==0){
                DriveTrain.brakeMotors();
            }

            if(ArmPID.getSetPoint() == Constants.armPlace){
                backLeftPower = backLeftPower * 1 / 2;
                backRightPower = backRightPower * 1 / 2;
                frontLeftPower = frontLeftPower * 1 / 2;
                frontRightPower = frontRightPower * 1 / 2;
              /*  if (intakePower == 0){
                    Intake.moveMotor(0.3);
                }*/
            }
            if (gamepad1.dpad_down) {
                Peripherals.resetYaw();
            }

            if (gamepad1.dpad_left){
                Wrist.Wrist(Constants.wristDown);
            }else
            if (gamepad1.dpad_right){
                Wrist.Wrist(Constants.wristUp);
            }else{
                Wrist.Wrist(Constants.wristUp);
            }
            DriveTrain.Drive(frontRightPower, frontLeftPower, backRightPower, backLeftPower);

            if (gamepad1.left_bumper) {
                if (Elevators.getArmLPosition() >= Constants.retractedElevator){
                    if (Elevators.getArmRPosition() >= Constants.retractedElevator){
                        Elevators.moveElevatorsR(0.75);
                        Elevators.moveElevatorsL(-0.75);
                        ElevatorPIDL.setSetPoint(Elevators.getArmLPosition());
                        ElevatorPIDR.setSetPoint(Elevators.getArmRPosition());
                    }
                }

            } else if (gamepad1.right_bumper) {
                Elevators.moveElevatorsL(0.75);
                Elevators.moveElevatorsR(-0.75);

                ElevatorPIDL.setSetPoint(Elevators.getArmLPosition());ElevatorPIDR.setSetPoint(Elevators.getArmRPosition());
            } else {
                Elevators.moveElevatorsL(0);
                Elevators.moveElevatorsR(0);
                Elevators.brakeMotors();
            }
            if (gamepad1.x) {
                ElevatorPIDL.setSetPoint(Constants.retractedElevator);
                ElevatorPIDR.setSetPoint(Constants.retractedElevator);
            }
            if (gamepad1.y) {
                ElevatorPIDL.setSetPoint(Constants.deployedElevator);
                ElevatorPIDR.setSetPoint(Constants.deployedElevator);
            }
            if (gamepad1.a){
                ArmPID.setSetPoint(Constants.armIntake);
            }
            if (gamepad1.b){
                ArmPID.setSetPoint(Constants.armPlace);
            }
            if (intakePower == 0) {
                Wrist.Wrist(Constants.wristUp);
            }/*else if (Arm.getArmEncoder() >= 300){
                Wrist.Wrist(Constants.wristDown);
            }else{
                Wrist.Wrist(Constants.wristUp);
            }*/
            if (gamepad2.left_trigger > 0 && gamepad2.dpad_left && ArmPID.getSetPoint() == Constants.armPlace){
                Intake.moveMotor(-gamepad2.left_trigger/2);
            }
            if(ArmPID.getSetPoint() == Constants.armPlace){
                isArmUp = true;
            }
            else {
                isArmUp = false;
            }
            telemetry.addData("ArmL", Elevators.getArmLPosition());
            telemetry.addData("ArmR", Elevators.getArmRPosition());
            telemetry.addData("Yaw", Peripherals.getYaw());
            telemetry.addData("ArmPID Power", ArmPID.getResult());
            telemetry.addData("Arm Encoder", Arm.getArmEncoder());
            telemetry.addData("Arm Offset", Arm.getOffset());
            telemetry.addData("Arm Voltage", Arm.getVoltage());
            telemetry.addData("Raw Arm Position", Arm.getRawPosition());
            telemetry.addData("Current sensor", Constants.currentNavigationalSensor);
            telemetry.update();
        }
    }
}
//s