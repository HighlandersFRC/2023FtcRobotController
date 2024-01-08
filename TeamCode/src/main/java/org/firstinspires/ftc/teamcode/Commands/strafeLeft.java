//package org.firstinspires.ftc.teamcode.Commands;
//import com.kauailabs.navx.ftc.AHRS;
//import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import org.firstinspires.ftc.teamcode.Constants;
//import org.firstinspires.ftc.teamcode.PID;
//
//public class strafeLeft extends Command{
//
//        org.firstinspires.ftc.teamcode.PID PID = new PID(1, 0.0, 1);
//        org.firstinspires.ftc.teamcode.PID DrivePID = new PID(0.3, 0.0, 0.0);
//
//        public DcMotor Left_Back;
//        public DcMotor Right_Back;
//        public DcMotor Left_Front;
//        public DcMotor Right_Front;
//        public double targetAngle;
//
//        public double currentPos;
//        public double PIDOutput;
//        public double speed;
//        public double distance;
//        public double targetPos;
//        public double avgEncoder;
//        public double backLeft;
//        public double backRight;
//        public double frontLeft;
//        public double frontRight;
//        public DcMotor Arm_Motor;
//        private AHRS navX;
//        PID ArmPID = new PID(0.001, 0, 0);
//
//        public strafeLeft(HardwareMap hardwareMap, double Speed, double Distance){
//            this.speed = Speed;
//            this.distance = Distance;
//            PID.setSetPoint(0);
//            Left_Front = hardwareMap.dcMotor.get("Left_Front");
//            Right_Front = hardwareMap.dcMotor.get("Right_Front");
//            Left_Back = hardwareMap.dcMotor.get("Left_Back");
//            Right_Back = hardwareMap.dcMotor.get("Right_Back");
//            navX = com.kauailabs.navx.ftc.AHRS.getInstance(hardwareMap.get(NavxMicroNavigationSensor.class, "navX"), com.kauailabs.navx.ftc.AHRS.DeviceDataType.kProcessedData);
//
//            Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
//            Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
//
//            Arm_Motor = hardwareMap.dcMotor.get("Arm_Motor");
//        }
//        public void start() {
//            Right_Front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            Left_Front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            Right_Back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            Left_Back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//            Right_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//            Left_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//            Right_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//            Left_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//            currentPos = navX.getYaw();
//            targetPos = distance * Constants.motorTicksPerRotation;
//            DrivePID.setSetPoint(targetPos);
//            PID.setMaxInput(180);
//            PID.setMinInput(-180);
//            PID.setContinuous(true);
//            PID.setMinOutput(-1);
//            PID.setMaxOutput(1);
//            navX.zeroYaw();
//        }
//
//        public void execute() {
//            ArmPID.updatePID(Arm_Motor.getCurrentPosition());
//            backRight = Right_Back.getCurrentPosition();
//            backLeft = Left_Back.getCurrentPosition();
//            frontLeft = Left_Front.getCurrentPosition();
//            frontRight  = Right_Front.getCurrentPosition();
//            avgEncoder = (backRight + frontLeft + frontRight + backLeft) / 4 ;
//            DrivePID.updatePID(avgEncoder);
//            PID.updatePID(currentPos);
//
//            double deviation = PID.getResult();
//
//
//            Right_Front.setPower(speed + deviation);
//            Left_Front.setPower(-speed - deviation);
//            Right_Back.setPower(-speed - deviation);
//            Left_Back.setPower(speed - deviation);
//            System.out.println(avgEncoder);
//            System.out.println(Right_Front.getPower()+ " Right_Front");
//            System.out.println(Right_Back.getPower()+ " Right_Back");
//            System.out.println(Left_Front.getPower()+ " Left_Front");
//            System.out.println(Left_Back.getPower()+ " Left_back");
//        }
//        public void end() {
//            Left_Front.setPower(0);
//            Left_Back.setPower(0);
//            Right_Back.setPower(0);
//            Right_Front.setPower(0);
//            Left_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            Left_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            Right_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            Right_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        }
//
//        public boolean isFinished() {
//            if (Math.abs(-Right_Back.getCurrentPosition()) - 10 >= Math.abs(targetPos)) {
//                return true;
//            }
//            if (Math.abs(Left_Back.getCurrentPosition()) - 10 >= Math.abs(targetPos)) {
//                return true;
//            }
//            if (Math.abs(-Left_Front.getCurrentPosition()) - 10 >= Math.abs(targetPos)) {
//                return true;
//            }
//            return Math.abs(Right_Front.getCurrentPosition()) - 10 >= Math.abs(targetPos);
//        }
//}
//
//
