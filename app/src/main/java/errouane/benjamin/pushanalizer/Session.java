package errouane.benjamin.pushanalizer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Benni on 07.11.2014.
 */
public class Session extends Observable {
    private float duration = 0;
    private float distance = 0;
    private List<Float> speeds = new ArrayList<Float>();
    private List<Float> times = new ArrayList<Float>();
    private List<Float> pushes = new ArrayList<Float>();
    private List<Float> accelerometerX = new ArrayList<Float>();
    private List<Float> accelerometerY = new ArrayList<Float>();
    private List<Float> accelerometerZ = new ArrayList<Float>();

    private Session() {}

    public void addValues(float deltaTime, float speed, int[] acc) {
        duration += deltaTime;
        times.add(duration);
        speeds.add(speed);
        accelerometerX.add((float) ((acc[0] / Math.pow(2,15))) * 15);
        accelerometerY.add((float) ((acc[1] / Math.pow(2,15))) * 15);
        accelerometerZ.add((float) ((acc[2] / Math.pow(2,15))) * 15);
    }

    public List<Float> getTimes() {
        return times;
    }

    public List<Float> getSpeeds() {
        return speeds;
    }

    public List<Float> getAccelerometerX() {
        return accelerometerX;
    }

    public List<Float> getAccelerometerY() {
        return accelerometerY;
    }

    public List<Float> getAccelerometerZ() {
        return accelerometerZ;
    }

    public void addDistance(float value) {
        distance += value;
    }

    public void addPush() {
        pushes.add(times.get(times.size() - 1));
    }

    public void reset() {
        duration = 0;
        distance = 0;
        pushes.clear();
        speeds.clear();
        times.clear();
        accelerometerX.clear();
        accelerometerY.clear();
        accelerometerZ.clear();
        hasChanged();
        notifyObservers();
        Log.e("SAD", ""+countObservers());
    }

    private static Session instance;
    public static Session getInstance() {
        if(instance == null)
            instance = new Session();

        return instance;
    }

    public String toReadableString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Duration: ");
        sb.append(duration);
        sb.append("\nDistance: ");
        sb.append(distance);
        sb.append("\n\nData (Time, Speed, Acc[x], Acc[y], Acc[z]):");

        for(int i = 0; i < speeds.size(); i++) {
            sb.append("\n");
            sb.append(times.get(i));
            sb.append("\t");
            sb.append(speeds.get(i));
            sb.append("\t");
            sb.append(accelerometerX.get(i));
            sb.append("\t");
            sb.append(accelerometerY.get(i));
            sb.append("\t");
            sb.append(accelerometerZ.get(i));
        }

        sb.append("\n\nPushes: ");
        sb.append(pushes.size());

        for(int i = 0; i < pushes.size(); i++) {
            sb.append("\n");
            sb.append(pushes.get(i));
        }

        return sb.toString();
    }

    public String toSimpleString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < speeds.size(); i++) {
            sb.append(times.get(i));
            sb.append("\t");
            sb.append(speeds.get(i));
            sb.append("\t");
            sb.append(accelerometerX.get(i));
            sb.append("\t");
            sb.append(accelerometerY.get(i));
            sb.append("\t");
            sb.append(accelerometerZ.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }
}