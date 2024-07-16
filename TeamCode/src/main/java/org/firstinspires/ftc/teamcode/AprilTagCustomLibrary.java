package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;

public class AprilTagCustomLibrary {

    public static AprilTagLibrary getSmallLibrary() {
        return new AprilTagLibrary.Builder()
                .setAllowOverwrite(true)
                .addTag(
                        7,
                        "tag 7",
                        0.127,
                        DistanceUnit.METER
                )
                .addTag(
                        10,
                        "tag 10",
                        0.127,
                        DistanceUnit.METER
                )
                .addTag(
                        8,
                        "tag 8",
                        0.0508,
                        DistanceUnit.METER
                )
                .addTag(
                        9,
                        "tag 9",
                        0.0508,
                        DistanceUnit.METER
                )
                .build();

    }
}