package com.gromyk.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;

    private TextView textView;
    private Button startLightActivityButton;
    private Button startProximityActivityButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSensorComponents();
        initView();
    }

    private void initSensorComponents() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    private void initView() {
        textView = findViewById(R.id.textView);
        textView.setText("");
        textView.setMovementMethod(new ScrollingMovementMethod());
        startLightActivityButton = findViewById(R.id.startLightActivityButton);
        startProximityActivityButton = findViewById(R.id.startProximityActivityButton);
        startLightActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LightSensorActivity.class);
            }
        });
        startProximityActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ProximitySensorActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        testSensor();
    }

    private void testSensor() {
        List<Sensor> sensors = new ArrayList<>(sensorManager.getSensorList(Sensor.TYPE_ALL));
        String sensorInfo;
        for (Sensor sensor : sensors) {
            sensorInfo = sensor.getName() + "\n"
                    + sensor.getVendor() + "; "
                    + sensor.getResolution() + "; "
                    + sensor.getPower() + "; "
                    + sensor.getReportingMode() + ".\n";
            textView.append(sensorInfo);
        }
    }

    private void startActivity(Class<? extends AppCompatActivity> activityToStart) {
        Intent lightSensorActivity = new Intent(this, activityToStart);
        startActivity(lightSensorActivity);
    }
}
