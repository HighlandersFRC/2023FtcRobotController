/*
package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import java.util.List;

public class cmd extends Command {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    */
/**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     *//*

    private TfodProcessor tfod;


    */
/**
     * The variable to store our instance of the vision portal.
     *//*

    tfod = new TfodProcessor.Builder()
    private VisionPortal visionPortal;
    private static final String TFOD_MODEL_ASSET = "modeltrial1.tflite";

    private static final String[] LABELS = {
            "BLUECUBE",
            "REDCUBE",
            "PURPLEPIXEL",
            "YELLOWPIXEL",
            "GREENPIXEL"

    };

    VisionPortal.Builder builder = new VisionPortal.Builder();

    private List<Recognition> currentRecognitions;

   \

    public cmd(List<Recognition> currentRecognitions) {
        this.currentRecognitions = currentRecognitions;

    }

    @Override
    public void start() {

    }

    @Override
    public boolean execute() {


        for (Recognition recognition : currentRecognitions) {
            float x = (recognition.getLeft() + recognition.getRight()) / 2;
            float y = (recognition.getTop() + recognition.getBottom()) / 2;

            if (x < 280) {
                return; = Left;
            } else if (x > 390) {
                result = "Right";
            } else if (x > 280 && x < 390) {
                result = "Center";
            } else if (Float.isNaN(x)) {
                result = "Center";
            }
        }
    }

    @Override
    public void end() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

}*/
