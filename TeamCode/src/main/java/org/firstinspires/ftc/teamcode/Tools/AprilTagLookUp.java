package org.firstinspires.ftc.teamcode.Tools;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;

public class AprilTagLookUp {

    public static AprilTagLibrary getSmallLibrary() {
        return new AprilTagLibrary.Builder()
                .setAllowOverwrite(true)
                .addTag(7,
                        "tag 7",
                        0.152,//test
                        new VectorF(0,0),
                        DistanceUnit.METER,
                        Quaternion.identityQuaternion())
                .addTag(
                        8,
                        "tag 8",
                        0.03571875,
                        DistanceUnit.METER
                )
                .addTag(
                        9,
                        "tag 9",
                        0.03571875,
                        DistanceUnit.METER
                )
                .build();

    }
}
