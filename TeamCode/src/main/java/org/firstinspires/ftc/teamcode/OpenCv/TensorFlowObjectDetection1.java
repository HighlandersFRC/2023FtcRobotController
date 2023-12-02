<<<<<<< Updated upstream
/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
=======

>>>>>>> Stashed changes

package org.firstinspires.ftc.teamcode.OpenCv;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
<<<<<<< Updated upstream

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@TeleOp(name = "Concept: TensorFlow Object Detection White Pixel", group = "Concept")
//@Disabled
public class TensorFlowObjectDetection1 extends LinearOpMode {

=======
import java.util.List;


@TeleOp(name = "TensorFlow Object Detection", group = "Concept")
//@Disabled
public class TensorFlowObjectDetection1 extends LinearOpMode {
>>>>>>> Stashed changes
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
<<<<<<< Updated upstream
    private static final String TFOD_MODEL_ASSET = "Centerstage.tflite";
    private static final String[] LABELS = {
            "Pixel"
=======
    private static final String TFOD_MODEL_ASSET = "model_20231124_191315.tflite";
    private static final String[] LABELS = {
            "RedCube",
            "BlueCube"

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
              //  if (gamepad1.dpad_down) {
               //     visionPortal.stopStreaming();
               // } else if (gamepad1.dpad_up) {
              //      visionPortal.resumeStreaming();
              //  }

                // Share the CPU.
                sleep(20);
            }
        }

        // Save more CPU resources when camera is no longer needed.
=======
            //    if (gamepad1.dpad_down) {
              //      visionPortal.stopStreaming();
                //} else if (gamepad1.dpad_up) {
                  //  visionPortal.resumeStreaming();
               //}

                // Share the CPU.
                sleep(10);
            }
        }


>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
            //.setModelLabels(LABELS)
            //.setIsModelTensorFlow2(true)
            //.setIsModelQuantized(true)
            //.setModelInputSize(300)
            //.setModelAspectRatio(16.0 / 9.0)

=======
            .setModelLabels(LABELS)
            //.setIsModelTensorFlow2(true)
            .setIsModelQuantized(true)
            .setModelInputSize(360)
           // .setModelAspectRatio(16.0 / 9.0)
>>>>>>> Stashed changes
            .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "webcam1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

<<<<<<< Updated upstream
        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        //tfod.setMinResultConfidence(0.75f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);
=======



        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
      //
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.setAutoStopLiveView(false);
        builder.addProcessor(tfod);


        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        tfod.setMinResultConfidence(0.75f);


        visionPortal.setProcessorEnabled(tfod, true);
>>>>>>> Stashed changes

    }   // end method initTfod()

    /**
<<<<<<< Updated upstream
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
=======
     * Add telemetry about TensorFlow Object Detection recognitions.

     * @return
     */

    private String telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());
  /* Boolean go = true;*/
        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            float x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            float y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

         /*   if("BLUECUBE".equals(LABELS)) {
                return String.valueOf(go);
            }
if(go) {*/
    if (x < 280) {
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
    }
/*};*/

//NOTE, for the better Left, Right, Center without the telemetry copy the one from my command named MODULETEAMPROP.java in the commands folder.
// It may also be renamed as TEAMPROPOBJECTDETECTION.java TENSORFLOWOBJECTDETECTIONCOMMAND.java and cmd.java are NOT correct -------|^

            currentRecognitions = tfod.getFreshRecognitions();


>>>>>>> Stashed changes


            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }   // end for() loop

<<<<<<< Updated upstream
    }   // end method telemetryTfod()

}   // end class
=======
        return null;

    }   //end method telemetryTfod()

}   // end class
/*
*

*/
//135
>>>>>>> Stashed changes
