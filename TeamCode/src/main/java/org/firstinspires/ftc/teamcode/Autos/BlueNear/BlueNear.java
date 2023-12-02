

package org.firstinspires.ftc.teamcode.Autos.BlueNear;

import android.util.Size;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.CameraConstants;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.DeployIntake;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.Intake;
import org.firstinspires.ftc.teamcode.Commands.MoveWrist;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.PixelTray;
import org.firstinspires.ftc.teamcode.Commands.RetractIntake;
import org.firstinspires.ftc.teamcode.Commands.RotateArm;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.teamcode.Commands.Wait;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import java.util.List;


@Autonomous
//@Disabled
public class BlueNear extends LinearOpMode {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;


    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
    private static final String TFOD_MODEL_ASSET = "backroundbrmodel.tflite";
    private static final String[] LABELS = {
            "bluecube",
            "redcube"

    };
    Scheduler scheduler = new Scheduler();
    public IMU imu;



    @Override
    public void runOpMode() {
        initTfod();
        waitForStart();
        String autoside = getLeftRightCenter();
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);

        telemetry.addData("autoside", autoside);
        if (autoside.equals("Right")){
            scheduler.add(new CommandGroup(scheduler,
                    new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.2, 0.65), new CommandGroup(scheduler, new Wait(1000), new DeployIntake(hardwareMap, "Deploy"))),
                    new MoveWrist(hardwareMap, Constants.wristDown),
                    new Turn(hardwareMap, 90),
                    new Drive(hardwareMap, -0.2, 0.28),
                    new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.3, -0.1), new PixelTray(hardwareMap, 3000, -1, "R"), new CommandGroup(scheduler, new Wait(1000), new Intake(hardwareMap, 1000, -0.25))),
                    new MoveWrist(hardwareMap, Constants.wristUp),
                    new Wait(1000),
                    new DeployIntake(hardwareMap, "Retract"),
                    new Drive(hardwareMap, 0.2, 0.4),
                    new DeployIntake(hardwareMap, "Deploy"),
                    new Drive(hardwareMap, 0.2, 0.11),
                    new Drive(hardwareMap, 0.2, 0.436),
                    new RotateArm(hardwareMap, Constants.armPlace),
                    new ParallelCommandGroup(scheduler, new PixelTray(hardwareMap, 3000, -1, "L"), new RotateArm(hardwareMap, Constants.armPlace))
            ));
        } else if (autoside.equals("Left")){
            scheduler.add(new CommandGroup(scheduler,
                    new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.2, 0.75), new CommandGroup(scheduler, new Wait(1000), new DeployIntake(hardwareMap, "Deploy"))),
                    new MoveWrist(hardwareMap, Constants.wristDown),
                    new Turn(hardwareMap, 90),
                    new Drive(hardwareMap, 0.3, 0.4),
                    new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.15, -0.1), new PixelTray(hardwareMap, 3000, -1, "R"), new CommandGroup(scheduler, new Wait(1000),  new Intake(hardwareMap, 1000, -0.25))),
                    new MoveWrist(hardwareMap, Constants.wristUp),
                    new Wait(1000),
                    new DeployIntake(hardwareMap, "Retract"),
                    new Drive(hardwareMap, 0.25, 0.34),
                    new DeployIntake(hardwareMap, "Deploy"),
                    new Wait(1000),
                    new RotateArm(hardwareMap, Constants.armPlace),
                    new ParallelCommandGroup(scheduler, new PixelTray(hardwareMap, 3000, -1, "L"), new RotateArm(hardwareMap, Constants.armPlace))
            ));
        } else if (autoside.equals("Middle")){
            scheduler.add(new CommandGroup(scheduler,
                    new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.2, 0.37), new CommandGroup(scheduler, new Wait(1000), new DeployIntake(hardwareMap, "Deploy"))),
                    new MoveWrist(hardwareMap, Constants.wristDown),
                    new Turn(hardwareMap, 179),
                    new Drive(hardwareMap, -0.2, 0.4),
                    new ParallelCommandGroup(scheduler, new Drive(hardwareMap, 0.15, -0.1), new PixelTray(hardwareMap, 3000, -1, "R"), new CommandGroup(scheduler, new Wait(1000),  new Intake(hardwareMap, 1000, -0.25))),
                    new MoveWrist(hardwareMap, Constants.wristUp),
                    new Wait(1000),
                    new DeployIntake(hardwareMap, "Retract"),
                    new Drive(hardwareMap, 0.3, 0.05),
                    new Turn(hardwareMap, 90),
                    new Drive(hardwareMap,0.3, 0.81),
                    new RotateArm(hardwareMap, Constants.armPlace),
                    new ParallelCommandGroup(scheduler, new PixelTray(hardwareMap, 3000, -1, "L"), new RotateArm(hardwareMap, Constants.armPlace))
            ));
        }
            while (opModeIsActive()) {
                telemetry.addData("IMU yaw", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
                telemetry.update();
                scheduler.update();
            }
        visionPortal.close();
    }
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                .setModelAssetName(TFOD_MODEL_ASSET)
                .setModelLabels(LABELS)
                .setIsModelQuantized(false)
                .setModelInputSize(320)
                .setModelAspectRatio(4.0 / 3.0)
                .build();


        VisionPortal.Builder builder = new VisionPortal.Builder();
builder.setCameraResolution(new Size(320, 240));
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "webcam1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.setAutoStopLiveView(false);
        builder.addProcessor(tfod);


        visionPortal = builder.build();

        tfod.setMinResultConfidence(0.50f);


        visionPortal.setProcessorEnabled(tfod, true);

    }

    private String getLeftRightCenter() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());
        int frames = 0;

        for (Recognition recognition : currentRecognitions) {
            frames = frames + 1;
            System.out.println("test");
            float x = (recognition.getLeft() + recognition.getRight()) / 2;
            float y = (recognition.getTop() + recognition.getBottom()) / 2;

            if (x < 280) {
                CameraConstants.autoSide = "Left";
                visionPortal.stopStreaming();
                return "Left";
            }
            if (x > 390) {
                CameraConstants.autoSide = "Right";
                visionPortal.stopStreaming();
                return "Right";
            }
            if (x > 280 && x < 390) {
                CameraConstants.autoSide = "Middle";
                visionPortal.stopStreaming();
                return  "Middle";
            }
            if (frames > 100){
                return "Middle";
            }
            /*if (Float.isNaN(x)) {
                CameraConstants.autoSide = "Middle";
            }*/


            currentRecognitions = tfod.getFreshRecognitions();


        }   // end for() loop

        return "Middle";

    }

}
