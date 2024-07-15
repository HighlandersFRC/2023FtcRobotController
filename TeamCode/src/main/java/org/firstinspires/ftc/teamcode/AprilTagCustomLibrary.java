package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;

public class AprilTagCustomLibrary {

    public static AprilTagLibrary getSmallLibrary() {
return new AprilTagLibrary.Builder()
        .addTag(
                7,
                "tag 7",
                0.127,
                DistanceUnit.METER
        )
        .build();

    }
}