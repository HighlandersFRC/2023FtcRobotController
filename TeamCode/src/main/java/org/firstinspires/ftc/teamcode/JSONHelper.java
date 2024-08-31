package org.firstinspires.ftc.teamcode;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONHelper {

    public static JSONArray loadJSONArrayFromAssets(Context context, String fileName) throws IOException, JSONException {
        InputStream inputStream = context.getAssets().open(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();

        return new JSONArray(builder.toString());
    }

    public static JSONObject getJSONObjectFromArray(JSONArray jsonArray, int index) throws JSONException {
        return jsonArray.getJSONObject(index);
    }

    public static double getXFromPoint(JSONObject point) throws JSONException {
        return point.getDouble("x");
    }

    public static double getYFromPoint(JSONObject point) throws JSONException {
        return point.getDouble("y");
    }
}
