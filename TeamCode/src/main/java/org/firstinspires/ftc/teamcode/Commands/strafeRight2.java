//package org.firstinspires.ftc.teamcode.Commands;
//import com.kauailabs.navx.ftc.AHRS;
//import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.teamcode.Constants;
//import org.firstinspires.ftc.teamcode.PID;
//
//public class strafeRight2 extends Command {
//    public DcMotor Left_Back;
//    public DcMotor Right_Back;
//    public DcMotor Left_Front;
//    public DcMotor Right_Front;
//    public strafeRight2(HardwareMap hardwareMap, double Speed, double Distance){
//        Left_Front = hardwareMap.dcMotor.get("Left_Front");
//        Right_Front = hardwareMap.dcMotor.get("Right_Front");
//        Left_Back = hardwareMap.dcMotor.get("Left_Back");
//        Right_Back = hardwareMap.dcMotor.get("Right_Back");
//
//        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
//        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
//
//    }
//
//
//
//    public void start() {
//
//    }
//
//    public void execute() {
//Left_Front.setPower(1);
//Right_Back.setPower(1);
//Left_Back.setPower(1);
//Right_Front.setPower(1);
//System.out.println(Right_Front.getPower()+ " Right_Front");
//System.out.println(Right_Back.getPower()+ " Right_Back");
//System.out.println(Left_Front.getPower()+ " Left_Front");
//System.out.println(Left_Back.getPower()+ " Left_back");
//    }
//
//
//    public void end() {
//
//    }
//
//
//    public boolean isFinished() {
//        return false;
//    }
//}
