/*
package org.firstinspires.ftc.teamcode;
//imports
import android.util.Size;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import java.util.concurrent.TimeUnit;

@TeleOp
public class AprilTagCustomDetection extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
//AprilTagProcessor setup
        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setLensIntrinsics(626.731, 626.731, 642.398, 380.131)
                //lens intrinsics for running at 640p .setLensIntrinsics(428.945, 428.945, 316.573,254.194 )
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagCustomLibrary.getSmallLibrary()) //custom library that you can change tag sizes in
                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
                .build();
//VisionPortal setup
        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)//do not use MJPEG
                .build();


        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
        }


        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(20, TimeUnit.MILLISECONDS);//change the exposure time according to field lighting

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(200);//you can change this too for more accuracy
        waitForStart();



        while (!isStopRequested() && opModeIsActive()) {
            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN); //built in pose solver

            for (AprilTagDetection detection : tagProcessor.getDetections()) {
                if (detection.rawPose != null) {
                    double x = detection.rawPose.x;
                    double y = detection.rawPose.z;
                    double z = -detection.rawPose.y;
//lines 64-73 are from ftc documentation for the ftc coordinate system
                    Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
                    double yaw = -rot.firstAngle;
                    double roll = rot.thirdAngle;
                    double pitch = rot.secondAngle;
//pythagorean math to get the range this is not corrected and is not accurate
                    double range = Math.sqrt(5 * 5 + y * y);
                    //not sure if 77 and 78 are being used
                    double bearing = Math.toDegrees(Math.atan2(x,y));
                    double elevation = Math.toDegrees(Math.atan2(z, Math.sqrt(x * x + y * y)));

                    AprilTagPoseFtc pose = new AprilTagPoseFtc(x, y, z, yaw, roll, pitch, range, bearing, elevation);
//corrected range uses a quartic regression to calculate an offset found in Constants
                  */
/*  double distance = Constants.fiveCorrected(pose.range);*//*

                    double CorrectX = Constants.yCorrected(pose.y);
                    double CorrectY =  -Constants.xCorrected(pose.x);

                    double measuredz = 5;

               */
/*     double YC = Math.cos(pose.bearing) * distance;
                    double XC = Math.sin(pose.bearing) * distance;*//*


                    //Polar coordinates

                    double r = Math.sqrt((CorrectX*CorrectX)+(CorrectY*CorrectY));
                    double theta = Math.toRadians(Math.atan2(CorrectY,CorrectX));

                    double angleoffset = (theta+(//robot yaw));

                    double xt = r*(Math.cos(angleoffset+180));
                    double yt = r*(Math.sin(angleoffset+180));

                    //convert to field coordinate

                    double FieldX = xt+0;//x value of library of vector f note look at tag to see which value
                    double FieldY= yt+0;//y value of library of vector f look at tag to see coordinates

                  */
/*double YC = Math.cos(Cbearing)*accuratedistance;
                  double XC = Math.sin(Cbearing)*accuratedistance;

                  double xf = 0;
                  double yf = 0;*//*

*/
/*
                  double xc =CorrectX;
                  double yc =CorrectY;

                  double Xf = Math.sqrt(CorrectX*CorrectX+CorrectY*CorrectY)*(Math.cos(90-(Math.atan2(CorrectX,CorrectY)+pose.yaw)));
                  double Yf =Math.sqrt(CorrectX*CorrectX+CorrectY*CorrectY)*(Math.sin(90-(Math.atan2(CorrectX,CorrectY)+pose.yaw)));*//*


                */
/*    telemetry.addData("accuratedistance", accuratedistance);
                    telemetry.addData("bearing", Cbearing);*//*

                 */
/*   telemetry.addData("not corrected distance",pose.range);*//*

                    telemetry.addData("pose", String.format("(%.2f, %.2f)",FieldX,FieldY));
                    telemetry.addData("CorrectX", CorrectX);
                    telemetry.addData("CorrectY", CorrectY);
                    telemetry.addData("radius", r);
                    telemetry.addData("angleoffset",angleoffset);
                    telemetry.addData("xt",xt);
                    telemetry.addData("yt",yt);
                    telemetry.addData("Tag ID", detection.id);
                    telemetry.addData("tagsize", detection.metadata.tagsize);
                    telemetry.addData("x", CorrectX);
                    telemetry.addData("y", CorrectY);
                    telemetry.addData("z", measuredz);
                    telemetry.addData("roll", pose.roll);
                    telemetry.addData("pitch", pose.pitch);
                    telemetry.addData("yaw", pose.yaw);
                    telemetry.addData("bearing (horizontal angle)", pose.bearing);
                    telemetry.addData("elevation (vertical angle)", pose.elevation);
           */
/*         telemetry.addData("Coordinates (XC, YC)", String.format("(%.2f, %.2f)", XC, YC));
                    telemetry.addData("DPC",String.format("(%.2f, %.2f)", (xf+xc)-0.127,(yf+yc)-0.1778));*//*

                    telemetry.addData("Raw Pose x", detection.rawPose.x);
                    telemetry.addData("Raw Pose y", detection.rawPose.y);
                    telemetry.addData("Raw Pose z", detection.rawPose.z);
                    telemetry.addData("exposure", exposure.isExposureSupported());
              */
/*      telemetry.addData("(Xf,Yf)", Xf + ", " + Yf);*//*

                    telemetry.update();
                }
            }
        }
    }
}

*/

//commented code above is without the imu yaw.
package org.firstinspires.ftc.teamcode;
//imports
import android.util.Size;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import java.util.concurrent.TimeUnit;

@TeleOp
public class AprilTagCustomDetection extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        // AprilTagProcessor setup
        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setLensIntrinsics(626.731, 626.731, 642.398, 380.131)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagCustomLibrary.getSmallLibrary())
                .setOutputUnits(DistanceUnit.METER, AngleUnit.RADIANS)
                .build();

        // VisionPortal setup
        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .build();

        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
        }
//exposure changing code...need to change according to environment conditions
        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(10, TimeUnit.MILLISECONDS);

        GainControl gain = visionPortal.getCameraControl(GainControl.class);
        gain.setGain(200);

        // IMU setup
        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);
//imu yaw reset with controller and initialization
        imu.resetYaw();

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            if (gamepad1.x) {
                imu.resetYaw();

            }

            tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN);
//documentation xyx setup
            for (AprilTagDetection detection : tagProcessor.getDetections()) {
                if (detection.rawPose != null) {
                    double x = detection.rawPose.x;
                    double y = detection.rawPose.z;
                    double z = -detection.rawPose.y;
//these are for the tag not for the robot that is custom
                    Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
                    double yaw = -rot.firstAngle;
                    double roll = rot.thirdAngle;
                    double pitch = rot.secondAngle;
//this range is incorrect and uses y and z
                    double range = Math.sqrt(5 * 5 + y * y);
                    double bearing = Math.toDegrees(Math.atan2(x, y));
                    double elevation = Math.toDegrees(Math.atan2(z, Math.sqrt(x * x + y * y)));

                    AprilTagPoseFtc pose = new AprilTagPoseFtc(x, y, z, yaw, roll, pitch, range, bearing, elevation);
//Correct X and Y using the linear regression offset values

                    double CorrectX = Constants.yCorrected(pose.y);
                    double CorrectY = -Constants.xCorrected(pose.x);
//polar coordinates r is the accurate distance (circle radius) and then theta is the bearing
                    double r = Math.sqrt((CorrectX * CorrectX) + (CorrectY * CorrectY));
                    double theta = (Math.atan2(CorrectY, CorrectX));

                    // robotyaw using the IMU
                    double robotYaw = (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
                    //angle offset
                    double angleoffset = (theta + robotYaw);
//this converts it to field coordinates
                    double xt = r * (Math.cos(angleoffset + Math.PI)); //or 180
                    double yt = r * (Math.sin(angleoffset + Math.PI));
                    //offset so that you measure from robot center
                   /* double Xcenteroffset = 0.120645;
                    double Ycenteroffset = 0.168275;*/
                    //field coordinates with the offset and added to the vector position of the april tag.


                    double FieldX = (xt + 0);
                    double FieldY = (yt + 0);


                    double tagyaw = 0;
                    if (detection.id == 7) {
                        tagyaw = Math.toDegrees(Math.PI);

                        double robotyawcalculated = (tagyaw + 180) - pose.yaw;
                        System.out.println(robotyawcalculated);
                    }
                    double robotyawcalculated = (tagyaw + 180) - pose.yaw;

                    telemetry.addData("robotyawcalculated", robotyawcalculated);
                    telemetry.addData("pose", String.format("(%.2f, %.2f)", FieldX, FieldY));
                    telemetry.addData("CorrectX", CorrectX);
                    telemetry.addData("CorrectY", CorrectY);
                    telemetry.addData("radius", r);
                    telemetry.addData("angleoffset", angleoffset);
                    telemetry.addData("xt", xt);
                    telemetry.addData("yt", yt);
                    telemetry.addData("Tag ID", detection.id);
                    telemetry.addData("tagsize", detection.metadata.tagsize);
                    telemetry.addData("x", CorrectX);
                    telemetry.addData("y", CorrectY);
                    telemetry.addData("z", 5);
                    telemetry.addData("robotyaw", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
                    telemetry.addData("pose.yaw", pose.yaw);
                    telemetry.addData("roll", pose.roll);
                    telemetry.addData("pitch", pose.pitch);
                    telemetry.addData("yaw", Math.toDegrees(Math.PI) - (pose.yaw));
                    telemetry.addData("bearing (horizontal angle)", pose.bearing);
                    telemetry.addData("elevation (vertical angle)", pose.elevation);
                    telemetry.addData("Raw Pose x", detection.rawPose.x);
                    telemetry.addData("Raw Pose y", detection.rawPose.y);
                    telemetry.addData("Raw Pose z", detection.rawPose.z);
                    telemetry.addData("exposure", exposure.isExposureSupported());
                    telemetry.update();
                }
            }
        }
    }
}