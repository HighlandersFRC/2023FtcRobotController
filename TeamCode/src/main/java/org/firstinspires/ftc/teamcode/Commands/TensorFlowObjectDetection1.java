

package org.firstinspires.ftc.teamcode.Commands;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import java.util.List;


@TeleOp(name = "TensorFlow Object Detection", group = "Concept")
//@Disabled
public class TensorFlowObjectDetection1 extends LinearOpMode {
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



    @Override
    public void runOpMode() {

        initTfod();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                telemetryTfod();

                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                //    if (gamepad1.dpad_down) {
                //      visionPortal.stopStreaming();
                //} else if (gamepad1.dpad_up) {
                //  visionPortal.resumeStreaming();
                //}

                // Share the CPU.
                sleep(10);
            }
        }


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
                //.setIsModelTensorFlow2(true)
                .setIsModelQuantized(false)
                .setModelInputSize(320)
                 .setModelAspectRatio(4.0 / 3.0)
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
        builder.enableLiveView(true);
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.setAutoStopLiveView(false);
        builder.addProcessor(tfod);
builder.setCameraResolution(new Size(320,240));

        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        tfod.setMinResultConfidence(0.50f);


        visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection recognitions.

     * @return
     */

    private String telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());
        Boolean go = true;
        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            float x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            float y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

           /* if("BLUECUBE".equals(LABELS)) {
                return String.valueOf(go);
            }
            if (go) {*/
            /*    if (x < 280) {
                    telemetry.addData("Location", "Left");
                    return "Left";
                }
                if (x > 390) {
                    telemetry.addData("Location", "Right");
                    return "Right";
                }
                if (x > 280 && x < 390) {
                    telemetry.addData("Location", "Center");
                    return "Center";
                }
                if (Float.isNaN(x)) {
                    telemetry.addData("Location", "Center");
                    return "Center";

                }*/

//NOTE, for the better Left, Right, Center without the telemetry copy the one from my command named MODULETEAMPROP.java in the commands folder.
// It may also be renamed as TEAMPROPOBJECTDETECTION.java TENSORFLOWOBJECTDETECTIONCOMMAND.java and cmd.java are NOT correct -------|^

            currentRecognitions = tfod.getFreshRecognitions();




            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }   // end for() loop

        return null;

    }   //end method telemetryTfod()

}   // end class
/*
 *

 */
//135