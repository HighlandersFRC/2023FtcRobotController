package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.PID;

public class PixelTray extends Command {
    public CRServo holderservo_left;
    public CRServo holderservo_right;
    public long time;
    public double speed;
    public long endTime;
    public String LR;
    public DcMotor Arm_Motor;
    PID ArmPID = new PID(0.001, 0, 0);



    public PixelTray(HardwareMap hardwareMap, long Time, double Speed, String LR) {
        holderservo_left = hardwareMap.crservo.get("HolderServo_Left");
        holderservo_right = hardwareMap.crservo.get("HolderServo_Right");
        this.speed = Speed;
        this.time = Time;
        this.LR = LR;
    }

    public void start() {
        if (LR.equals("L")) {
            holderservo_left.setPower(-speed);
        }else
            if(LR.equals("R")){
                holderservo_right.setPower(speed);
        }else
            if (LR.equals("LR")){
                holderservo_left.setPower(-speed);
                holderservo_right.setPower(speed);
            }


        endTime = System.currentTimeMillis() + time;
    }
    public void execute() {
/*        ArmPID.updatePID(Arm_Motor.getCurrentPosition());
        Arm_Motor.setPower(ArmPID.getResult());*/
    }

    public void end() {
        holderservo_left.setPower(0);
        holderservo_right.setPower(0);
    }

    public boolean isFinished() {
        if (System.currentTimeMillis() >= endTime){
            return true;
        }
        else {
            return false;
        }
    }
}