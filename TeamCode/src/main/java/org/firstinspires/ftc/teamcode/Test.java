//package org.firstinspires.ftc.teamcode;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//@TeleOp
//public class Test extends LinearOpMode {
//
////Initialise motors, classes, and sensors here
//
//    private DcMotor Arm1;
//   private DcMotor Left_Front;
//   private DcMotor Right_Front;
//   private DcMotor Left_Back;
//   private DcMotor Right_Back;
//
//    PID1 PID = new PID1(1,1,1);
//
//
//    @Override
//
//    public void runOpMode() throws InterruptedException {
//        Left_Front = hardwareMap.dcMotor.get("Left_Front");
//        Right_Front = hardwareMap.dcMotor.get("Right_Front");
//        Left_Back = hardwareMap.dcMotor.get("Left_Back");
//        Right_Back = hardwareMap.dcMotor.get("Right_Back");
//        Arm1 = hardwareMap.dcMotor.get("Arm1");
//
//    // Wait for the game to start (driver presses PLAY)
//
//        waitForStart();
//
//    // run until the end of the match (driver presses STOP)
//
//        while (opModeIsActive()) {
//            //stuff
//            double speed;
//            /*double vertical;
//            //double horizontal;
//            //double pivot;
//            //vertical = -gamepad1.left_stick_y;
//            //horizontal = gamepad1.left_stick_x;
//            pivot = -gamepad1.right_stick_x;
//            */
//
//            speed = (Arm1.getCurrentPosition()-Arm1.getCurrentPosition())/(time-time);
//
//    //Mecanum drive
//
//            /*Right_Front.setPower(pivot + (-vertical + horizontal));
//            //Right_Back.setPower(pivot + (-vertical - horizontal));
//            //Left_Back.setPower(pivot + (vertical - horizontal));
//            Left_Front.setPower(pivot + (vertical + horizontal));
//            */
//            waitForStart();
//
//
//    //Code for arm
//
//            while (opModeIsActive()) {
//                PID.setPID(.1,.0 ,.0);
//                    Arm1.setTargetPosition(PID.getSetPoint());
//                    Arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    if (gamepad1.b) {
//                        PID.setSetPoint(100);
//                        Arm1.setPower(PID.getSetPoint());
//
//                    }
//                    if (gamepad1.x) {
//                        PID.setSetPoint(100);
//                        PID.updatePID(Arm1.getCurrentPosition());
//                        Arm1.setPower(PID.getResult());
//                        telemetry.addData("speed",speed);
//                    }
//                    else {
//                        Arm1.setPower(0);
//                    }   resetRuntime();
//                Arm1.getCurrentPosition();
//
//            }
//
//    //adding data
//            telemetry.addData("speed",speed);
//            telemetry.addData("Arm Encode", Arm1.getCurrentPosition());
//            telemetry.addData("Arm Inches", ticksToInches(Arm1.getCurrentPosition()));
//            telemetry.addData("PID Result", PID.getResult());
//            telemetry.update();
//        }
//
//    }
//
//    //units for stuff
//
//    public double ticksToInches(double ticks) {
//        double inches = ticks / 52;
//        return inches;
//    }
//
//    }