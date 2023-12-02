/*
package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

public class TensorFlowObjectDetectionCommand extends Command {
    private static final boolean USE_WEBCAM = true;
    private TfodProcessor tfod;
    private VisionPortal visionPortal;
    private static final String TFOD_MODEL_ASSET = "modeltrial1.tflite";
    private static final String[] LABELS = {
            "BLUECUBE",
            "REDCUBE",
            "PURPLEPIXEL",
            "YELLOWPIXEL",
            "GREENPIXEL"
    };

    public TensorFlowObjectDetectionCommand() {
        //variables :)
    }

    @Override
    public void start() {
        initializeTfod();

    }

    @Override
    public boolean execute() {
        updateTelemetryTfod();
        // You can add any logic that needs to be continuously executed here.
        return false;
    }

    @Override
    public void end() {
        visionPortal.close();
        // You can add any logic that needs to be executed when the command ends here.
    }

    @Override
    public boolean isFinished() {
        return commandCompleted;
        // You can implement a condition to determine when the command is finished.
    }

    private void initializeTfod() {
        tfod = new TfodProcessor.Builder()
                .setModelAssetName(TFOD_MODEL_ASSET)
                .setModelLabels(LABELS)
                .setIsModelQuantized(true)
                .setModelInputSize(360)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();

        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "webcam1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        builder.enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(false)
                .addProcessor(tfod);

        visionPortal = builder.build();

        tfod.setMinResultConfidence(0.75f);
        visionPortal.setProcessorEnabled(tfod, true);
    }

    private void updateTelemetryTfod() {
        List<Recognition> currentRecognitions = tfod.getRecognitions();

        for (Recognition recognition : currentRecognitions) {
            float x = (recognition.getLeft() + recognition.getRight()) / 2;
            float y = (recognition.getTop() + recognition.getBottom()) / 2;
Float Left = x < 280;
            if (x < 280) {
               return "Left";
            } else if (x > 390) {
                return "Right";
            }  else if (280 < x && x < 390) {
                return "Center";
            }else {return "Center";
                // Do something for center
            }
        }
    }
}
*/
