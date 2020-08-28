package com.example.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {


    private lateinit var sensorManager : SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        getAllSensors.setOnClickListener{

            val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

            for(sensor in sensorList){
                sensors.append(sensor.name)
                sensors.append("\n")
            }
        }

//        getLight.setOnClickListener {
//            val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
//            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
//        }

        giro.setOnClickListener {
            val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
//        lighting.text = p0?.let{
//            it.values.get(0).toString()
//        }
        if(p0 == null)
            return
        axelerometr.text = "X: ${p0.values[0] } \n Y: ${p0.values[1]} \n Z: ${p0.values[2]}"

        val params = ball.layoutParams as ConstraintLayout.LayoutParams
        params.setMargins(
            -p0.values[0].toInt() * 100,
            p0.values[1].toInt() * 100,
            p0.values[0].toInt() * 100,
            p0.values[2].toInt() * 100
        )
        ball.requestLayout()
    }
}