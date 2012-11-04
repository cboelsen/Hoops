package org.zapto.boelsen.hang.gliding.hoops;

import java.util.Timer;
import java.util.TimerTask;

import rajawali.math.Number3D;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class HoopsActivity
    extends RajawaliExampleActivity
    implements OnSeekBarChangeListener,
               SensorEventListener
{
	private HoopsRenderer mRenderer;
	private SeekBar mSeekBarX, mSeekBarY, mSeekBarZ;
	private TextView label, label2;
	private Number3D mCameraOffset;
    private SensorManager mSensorManager;
    
    private Timer oneSecondTick;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                              WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                              WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
		mMultisamplingEnabled = true;
		mCameraOffset = new Number3D();
		super.onCreate(savedInstanceState);

		mRenderer = new HoopsRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		mRenderer.setUsesCoverageAa(mUsesCoverageAa);
		super.setRenderer(mRenderer);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
		
		LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.BOTTOM);

        label = new TextView(this);
        label.setText("Fps: " );
        label.setGravity(Gravity.CENTER);
        label.setHeight(100);
        ll.addView(label);

        label2 = new TextView(this);
        label2.setText("Score: " );
        label2.setGravity(Gravity.CENTER);
        label2.setHeight(100);
        ll.addView(label2);

        /*mSeekBarZ = new SeekBar(this);
        mSeekBarZ.setMax(40);
        mSeekBarZ.setProgress(16);
        mSeekBarZ.setOnSeekBarChangeListener(this);
        ll.addView(mSeekBarZ);
        
        mSeekBarY = new SeekBar(this);
        mSeekBarY.setMax(20);
        mSeekBarY.setProgress(13);
        mSeekBarY.setOnSeekBarChangeListener(this);
        ll.addView(mSeekBarY);
        
        mSeekBarX = new SeekBar(this);
        mSeekBarX.setMax(20);
        mSeekBarX.setProgress(10);
        mSeekBarX.setOnSeekBarChangeListener(this);
        ll.addView(mSeekBarX);*/
        
        mLayout.addView(ll);
        
        initLoader();
        
        oneSecondTick = new Timer();
        oneSecondTick.schedule(new TimerTask() {          
            @Override
            public void run() {
                TimerMethod();
            }
            
        }, 0, 1000);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		mCameraOffset.setAll(mSeekBarX.getProgress()-10, mSeekBarY.getProgress()-10, mSeekBarZ.getProgress());
		mRenderer.setCameraOffset(mCameraOffset);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mRenderer.setAccelerometerValues( event.values[1], event.values[0], 0 );
        }
    }
    
    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }
    
    private Runnable Timer_Tick = new Runnable()
    {
        public void run()
        {
            //This method runs in the same thread as the UI.
            //Do something to the UI thread here
            label.setText( "Fps: " + mRenderer.getFrames() );
            label2.setText( "Score: " + mRenderer.getScore() );
        }
    };
}
