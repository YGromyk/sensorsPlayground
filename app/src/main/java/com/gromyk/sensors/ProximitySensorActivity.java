package com.gromyk.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ProximitySensorActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;

    private TextView proximitySensorValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_sensor);
        initSensorComponents();
        initView();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(proximitySensorListener, proximitySensor, SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(proximitySensorListener);
        super.onPause();
    }

    private void initSensorComponents() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        proximitySensorListener = new SensorEventListener() {
            private String message;

            @Override
            public void onSensorChanged(SensorEvent event) {
                message = "Got a sensor event " + event.values[0] + " units\n";
                proximitySensorValueTextView.setText(message);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                message = sensor.getName() + " accuracy changed: "
                        + accuracy
                        + (accuracy == 1 ? " (LOW)" : (accuracy == 2) ? " (MED)" : " (HIGH)")
                        + "\n";
                proximitySensorValueTextView.setText(message);
            }
        };
    }

    private void initView() {
        proximitySensorValueTextView = findViewById(R.id.proximitySensorValueTextView);
    }
}
