package org.vt.ece4564.myitineraryapp;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class SensorActivity extends Activity implements SensorEventListener {
	  private SensorManager mSensorManager;
	  private Sensor mLight;
	  private Sensor mTemp;
	  private Sensor mLoc;
	  float temp_num;
	  float light_num;
	  float loc_num;

	  @Override
	  public final void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //setContentView(R.layout.main);

	    // Get an instance of the sensor service, and use that to get an instance of
	    // a particular sensor.
	    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    //mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
	    
	    mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
	    mTemp = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
	  }

	  @Override
	  public final void onAccuracyChanged(Sensor sensor, int accuracy) {
	    // Do something here if sensor accuracy changes.
	  }

	  @Override
	  protected void onResume() {
	    // Register a listener for the sensor.
	    super.onResume();
	    mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
	    mSensorManager.registerListener(this, mTemp, SensorManager.SENSOR_DELAY_NORMAL);
	  }

	  @Override
	  protected void onPause() {
	    // Be sure to unregister the sensor when the activity pauses.
	    super.onPause();
	    mSensorManager.unregisterListener(this);
	  }

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		//float millibars_of_pressure = SensorEvent.values[0];
		
		temp_num = arg0.values[0];
		light_num = arg0.values[1];
		
	}
	
	public float getLight(){
		return light_num;
	}
	
	public float getTemp(){
		return temp_num;
	}
}