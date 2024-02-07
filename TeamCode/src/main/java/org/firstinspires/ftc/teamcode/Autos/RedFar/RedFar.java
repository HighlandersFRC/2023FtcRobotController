

package org.firstinspires.ftc.teamcode.Autos.RedFar;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.sun.tools.javac.Main;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Commands.Arm;
import org.firstinspires.ftc.teamcode.Commands.Wait;
import org.firstinspires.ftc.teamcode.Commands.strafe;
import org.firstinspires.ftc.teamcode.Tools.Constants;
import org.firstinspires.ftc.teamcode.Commands.CommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Commands.MainIntake;
import org.firstinspires.ftc.teamcode.Commands.MoveWrist;
import org.firstinspires.ftc.teamcode.Commands.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.Scheduler;
import org.firstinspires.ftc.teamcode.Commands.Turn;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import java.util.List;
import java.util.TreeMap;


@Autonomous
//@Disabled
public class RedFar extends LinearOpMode {
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
    @Override
    public void runOpMode() {
        initTfod();
        waitForStart();
        String autoside = getLeftRightCenter();

        telemetry.addData("autoside", autoside);
        if (autoside.equals("Right")){
            scheduler.add(new CommandGroup(scheduler,
                    new ParallelCommandGroup(scheduler, new Drive(hardwareMap, -0.4, 0.15), new MoveWrist(hardwareMap, Constants.wristDown)),
                    new Turn(hardwareMap, -90),
                    new Drive(hardwareMap, -0.2, 0.19),
                    new MainIntake(hardwareMap,750,-0.35),
                    new MoveWrist(hardwareMap, Constants.wristUp),
                    new Drive(hardwareMap, -1, 0.33),
                    new Wait(500),
                    new strafe(hardwareMap, -0.5, 0.2),
                    new Arm(hardwareMap,Constants.armHigh),
                    new ParallelCommandGroup(scheduler, new Arm(hardwareMap, Constants.armHigh), new MainIntake(hardwareMap,1000,-0.20)),
                    new Arm(hardwareMap, Constants.armIntake),
                    new Drive(hardwareMap, 0.3, 0.02),
                    new strafe(hardwareMap,-0.5, 0.4),
                    new Drive(hardwareMap, -0.4, 0.06)
            ));
        } else if (autoside.equals("Left")){
            scheduler.add(new CommandGroup(scheduler,
                    new ParallelCommandGroup(scheduler, new Drive(hardwareMap, -0.4, 0.17), new MoveWrist(hardwareMap, Constants.wristDown)),
                    new Turn(hardwareMap, -92),
                    new Drive(hardwareMap, -0.4, 0.015),
                    new MainIntake(hardwareMap,750,-.2),
                    new MoveWrist(hardwareMap, Constants.wristUp),
                    new Drive(hardwareMap, -1, 0.5),
                    new Wait(500),
                    new strafe(hardwareMap, 0.5, 0.15),
                    new Arm(hardwareMap, Constants.armHigh),
                    new ParallelCommandGroup(scheduler, new Arm(hardwareMap, Constants.armHigh), new MainIntake(hardwareMap, 750, -0.3)),
                    new Arm(hardwareMap, Constants.armIntake)
            ));
        } else if (autoside.equals("Middle")){
            scheduler.add(new CommandGroup(scheduler,
                    new MoveWrist(hardwareMap, Constants.wristDown),
                    new Drive(hardwareMap, -0.4, 0.137),
                    new Turn(hardwareMap, 180),
                    new MainIntake(hardwareMap, 750, -0.2),
                    new Turn(hardwareMap, 90),
                    new Drive(hardwareMap, -1, 0.545),
                    new MoveWrist(hardwareMap, Constants.wristUp),
                    new Arm(hardwareMap, Constants.armHigh),
                    new ParallelCommandGroup(scheduler, new Arm(hardwareMap, Constants.armHigh), new MainIntake(hardwareMap, 750, -0.3)),
                    new Arm(hardwareMap, Constants.armIntake)
            ));
        } else if (autoside.equals("None")) {

        }
        while (opModeIsActive()) {
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
            System.out.println("Detected X" + "" + x);
            if (x < 125 && !(x == 0)) {
                visionPortal.stopStreaming();
                return "Left";
            }
            if (x > 175) {
                visionPortal.stopStreaming();
                return "Right";
            }
            if (x > 125 && x < 175) {
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

        return "Left";

    }

}
