package com.gromyk.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LightSensorActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightSensorListener;

    private TextView lightSensorValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);
        initSensorComponents();
        initView();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(lightSensorListener);
        super.onPause();
    }

    private void initSensorComponents() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightSensorListener = new SensorEventListener() {
            private String message;

            @Override
            public void onSensorChanged(SensorEvent event) {
                message = "Got a sensor event " + event.values[0] + " SI lux units\n";
                lightSensorValueTextView.setText(message);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                message = sensor.getName() + " accuracy changed: "
                        + accuracy
                        + (accuracy == 1 ? " (LOW)" : (accuracy == 2) ? " (MED)" : " (HIGH)")
                        + "\n";
                lightSensorValueTextView.setText(message);
            }
        };
    }

    private void initView() {
        lightSensorValueTextView = findViewById(R.id.lightSensorValueTextView);
    }
}
