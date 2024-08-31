package org.firstinspires.ftc.teamcode.PathingTool;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PathLoading {

    private JSONObject jsonPathData;

    public PathLoading(Context context, String pathFileName) {
        loadJSONFromAsset(context, pathFileName);
    }

    private void loadJSONFromAsset(Context context, String pathFileName) {
        try {
            // Open the JSON file from the assets folder
            InputStream inputStream = context.getAssets().open(pathFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            inputStream.close();

            // Parse the JSON content into a JSONObject
            jsonPathData = new JSONObject(sb.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJsonPathData() {
        return jsonPathData;
    }
}
