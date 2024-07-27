package org.firstinspires.ftc.teamcode;
import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
public class JsonImporter {

    private Context context;

    public JsonImporter() {
        this.context = context;
    }


    public AssetManager JsonImport(){
        try{
            InputStream inputStream = context.getAssets().open("ParabolicPath.java");
            int size = inputStream.available();
            byte [] buffer = new byte [size];
            inputStream.read(buffer);
            inputStream.close();

            String json;
            int max;
            double time;
            double x;
            double y;
            double angle;
            double x_velocity;
            double y_velocity;
            double angular_velocity;
            double x_acceleration;
            double y_acceleration;
            double angular_acceleration;

            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);
            max = jsonArray.length();

            for(int i = 0; i<max ; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                time = jsonObject.getDouble("time");
                x = jsonObject.getDouble("x");
                y = jsonObject.getDouble("y");
                angle = jsonObject.getDouble("angle");
                x_velocity = jsonObject.getDouble("x_velocity");
                y_velocity = jsonObject.getDouble("y_velocity");
                angular_velocity = jsonObject.getDouble("angular_velocity");
                x_acceleration = jsonObject.getDouble("x_acceleration");
                y_acceleration = jsonObject.getDouble("y_acceleration");
                angular_acceleration = jsonObject.getDouble("angular_acceleration");
                System.out.printf(" time:%f%n x:%f%n y:%f%n angle:%f%n x_velocity:%f%n y_velocity:%f%n angular_velocity:%f%n x_acceleration:%f%n y_acceleration:%f%n angular_acceleration:%f%n",time,x,y,angle,x_velocity,y_velocity,angular_velocity,x_acceleration,y_acceleration,angular_acceleration);

            }
        }

        catch (Exception e){
            System.out.println("error"+ e);
        }


        return null;
    }
    }

