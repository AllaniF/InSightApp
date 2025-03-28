package model;

import android.os.Parcel;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Weather implements Serializable {
    private String sol;
    private double avgTemp, minTemp, maxTemp;
    private double avgPressure, minPressure, maxPressure;
    private Map<Integer, WindData> windDirections;

    public Weather(String sol, JSONObject data) throws JSONException {
        this.sol = sol;
        this.windDirections = new HashMap<>();


        if (data.has("AT")) {
            JSONObject at = data.getJSONObject("AT");
            this.avgTemp = at.optDouble("av", 0);
            this.minTemp = at.optDouble("mn", 0);
            this.maxTemp = at.optDouble("mx", 0);
        }


        if (data.has("PRE")) {
            JSONObject pre = data.getJSONObject("PRE");
            this.avgPressure = pre.optDouble("av", 0);
            this.minPressure = pre.optDouble("mn", 0);
            this.maxPressure = pre.optDouble("mx", 0);
        }


        if (data.has("WD")) {
            JSONObject wd = data.getJSONObject("WD");
            Iterator<String> keys = wd.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                if (!key.equals("most_common")) {
                    JSONObject windData = wd.getJSONObject(key);
                    int degrees = windData.optInt("compass_degrees", 0);
                    int force = windData.optInt("ct", 0);
                    windDirections.put(degrees, new WindData(degrees, force));
                }
            }
        }
    }

    public String getSol() {
        return sol;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getAvgPressure() {
        return avgPressure;
    }

    public double getMinPressure() {
        return minPressure;
    }

    public double getMaxPressure() {
        return maxPressure;
    }

    public Map<Integer, WindData> getWindDirections() {
        return windDirections;
    }


    protected Weather(Parcel in) {
        sol = in.readString();
        avgTemp = in.readDouble();
        minTemp = in.readDouble();
        maxTemp = in.readDouble();
        avgPressure = in.readDouble();
        minPressure = in.readDouble();
        maxPressure = in.readDouble();

        int size = in.readInt();
        windDirections = new HashMap<>();
        for (int i = 0; i < size; i++) {
            int key = in.readInt();
            WindData value = new WindData(in.readInt(), in.readInt());
            windDirections.put(key, value);
        }
    }

    public static class WindData implements Serializable {
        private int compassDegrees;
        private int force;

        public WindData(int compassDegrees, int force) {
            this.compassDegrees = compassDegrees;
            this.force = force;
        }

        public int getCompassDegrees() {
            return compassDegrees;
        }

        public int getForce() {
            return force;
        }
    }
}
