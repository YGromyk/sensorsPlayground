package com.gromyk.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class SensorActivity extends AppCompatActivity {
    private final static String SENSOR_TYPE_KEY = "typeOfSensor";
    private final static int SENSOR_NOT_FOUND = -1;


    private int sensorType;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    private TextView sensorValueTextView;
    private TextView sensorNameTextView;

    static Intent getIntentWithParameters(Context contextPackage, int sensorsType) {
        Intent intent = new Intent(contextPackage, SensorActivity.class);
        intent.putExtra(SENSOR_TYPE_KEY, sensorsType);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        initView();
        getExtras();
        initSensorComponents();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }

    private void getExtras() {
        sensorType = getIntent().getIntExtra(SENSOR_TYPE_KEY, SENSOR_NOT_FOUND);
    }

    private void initSensorComponents() {
        if (sensorType == SENSOR_NOT_FOUND) {
            Toast.makeText(this, "Sensor is unsupported", Toast.LENGTH_LONG).show();
            return;
        }
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(sensorType);
        sensorNameTextView.setText(getString(R.string.name_of_sensor, sensor.getName()));
        sensorEventListener = new SensorEventListener() {
            private String message;

            @Override
            public void onSensorChanged(SensorEvent event) {
                message = "Got a sensor event " + Arrays.toString(event.values) + "\n";
                sensorValueTextView.setText(message);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }

    private void initView() {
        sensorValueTextView = findViewById(R.id.sensorValueTextView);
        sensorNameTextView = findViewById(R.id.sensorNameTextView);
    }
}
