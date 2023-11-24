package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.CameraConstants;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

public class TeamPropDetection extends Command {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
    private static final String TFOD_MODEL_ASSET = "modeltrial1.tflite";
    private static final String[] LABELS = {
            "REDCUBE",
            "BLUECUBE",
            "WHITEPIXEL",
            "PURPLEPIXEL",
            "GREENPIXEL",
            "YELLOWPIXEL",};
    long time;
    long endTime;
    public void runOpMode() {
        initTfod();

        // Wait for the DS start button to be touched.

       /* if (opModeIsActive()) {
            while (opModeIsActive()) {


               // Push telemetry to the Driver Station.

               // Save CPU resources; can resume streaming when needed.
               //    if (gamepad1.dpad_down) {
               //      visionPortal.stopStreaming();
               //} else if (gamepad1.dpad_up) {
               //  visionPortal.resumeStreaming();
               //}
               // Share the CPU.
         }
        }*/
        visionPortal.close();

    }   // end runOpMode()

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private void initTfod() {
        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()
                .setModelAssetName(TFOD_MODEL_ASSET)
                // .setModelFileName(TFOD_MODEL_ASSET) //if you have downloaded a custom team model to the Robot Controller.
                //.setModelAssetName(TFOD_MODEL_ASSET)
                //.setModelFileName(TFOD_MODEL_FILE)
                .setModelLabels(LABELS)
                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                .setIsModelQuantized(true)
                .setModelInputSize(360)
                // .setModelAspectRatio(16.0 / 9.0)
                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();
        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {

            builder.setCamera(hardwareMap.get(WebcamName.class, "webcam1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }


        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.setAutoStopLiveView(false);
        builder.addProcessor(tfod);


        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        tfod.setMinResultConfidence(0.75f);


        visionPortal.setProcessorEnabled(tfod, true);

    }

    ;

    public void start() {
        endTime = System.currentTimeMillis() + 3000;
    }

    public void execute() {
        List<Recognition> currentRecognitions = tfod.getRecognitions();
        for (Recognition recognition : currentRecognitions) {
            float x = (recognition.getLeft() + recognition.getRight()) / 2;
            float y = (recognition.getTop() + recognition.getBottom()) / 2;
            boolean Left = x < 280;
            boolean Right = x > 390;
            boolean Center = x > 280 && x < 390;
            boolean Middle = Center;
            if (x < 280) {
                CameraConstants.autoSide = "Left";
            }
            if (x > 390) {
                CameraConstants.autoSide = "Right";
            }
            if (x > 280 && x < 390) {
                CameraConstants.autoSide = "Middle";
            }
            if (Float.isNaN(x)) {
                CameraConstants.autoSide = "Middle";
            }
        }
    }

    public void end() {

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