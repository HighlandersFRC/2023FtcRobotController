package org.firstinspires.ftc.teamcode.OpenCv;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class testCOLOR extends OpMode {

OpenCvWebcam webcam1 = null;
    @Override
    public void init() {

        WebcamName WebcamName = hardwareMap.get(org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName.class, "webcam1");
int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
webcam1 = OpenCvCameraFactory.getInstance().createWebcam(WebcamName, cameraMonitorViewId);

webcam1.setPipeline(new examplePipeline());

webcam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
    @Override
    public void onOpened() {
        webcam1.startStreaming(640,360, OpenCvCameraRotation.UPRIGHT);
    }

    @Override
    public void onError(int errorCode) {

    }
});
    }


    @Override
    public void loop() {
abstract class examplePipeline extends OpenCvPipeline{
    Mat YCBCr = new Mat();
    Mat leftCrop;
    Mat rightCrop;
    double leftavgfin;
    double rightavgfin;
    Mat output = new Mat();
    Scalar rectColor = new Scalar(255.0, 0,0);


    public Mat processframe (Mat input){

        Imgproc.cvtColor(input, YCBCr, Imgproc.COLOR_RGB2YCrCb);
        telemetry.addLine("pipeline running");

        Rect leftRect = new Rect( 1, 1, 319, 359);
        Rect rightRect = new Rect(320, 1, 319, 359);

        input.copyTo(output);
        Imgproc.rectangle(output, leftRect, rectColor, 2);
        Imgproc.rectangle(output, rightRect, rectColor, 2);

        leftCrop = YCBCr.submat(leftRect);
        leftCrop = YCBCr.submat(rightRect);

        Core.extractChannel(leftCrop, leftCrop, 2);
        Core.extractChannel(rightCrop, rightCrop, 2);

        Scalar leftavg = Core.mean(leftCrop);
        Scalar rightavg = Core.mean(rightCrop);

        leftavgfin = leftavg.val[0];
        rightavgfin = rightavg.val[0];

        if (leftavgfin > rightavgfin){

            telemetry.addLine( "Left");

        }
        else {
                telemetry.addLine("Right");

        return(output);
    }
        return input;
    }
}
    }

    private class examplePipeline extends OpenCvPipeline {
        @Override
        public Mat processFrame(Mat input) {
            return input;
        }
    }
}

