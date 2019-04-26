package com.gromyk.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Dictionary<String, Integer> sensorsTable;
    private List<String> sensorNames;

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSensorComponents();
        testSensor();
        initView();
    }

    private void initSensorComponents() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    private void initView() {
        listView = findViewById(R.id.sensorsListView);
        ArrayAdapter<String> sensorsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                sensorNames
        );
        listView.setAdapter(sensorsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(sensorsTable.get(sensorNames.get(position)));
            }
        });
    }


    private void testSensor() {
        List<Sensor> sensors = new ArrayList<>(sensorManager.getSensorList(Sensor.TYPE_ALL));
        sensorNames = new ArrayList<>();
        sensorsTable = new Hashtable<>();
        for (Sensor sensor : sensors) {
            sensorNames.add(sensor.getName());
            sensorsTable.put(sensor.getName(), sensor.getType());
        }
    }

    private void startActivity(int sensorType) {
        Intent lightSensorActivity = SensorActivity.getIntentWithParameters(this, sensorType);
        startActivity(lightSensorActivity);
    }
}
