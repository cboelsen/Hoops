package org.zapto.boelsen.hang.gliding.hoops;

import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.zapto.boelsen.hang.gliding.hoops.AveragedValue;
import org.zapto.boelsen.hang.gliding.hoops.R;


import rajawali.BaseObject3D;
import rajawali.ChaseCamera;
import rajawali.SerializedObject3D;
import rajawali.bounds.IBoundingVolume;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.math.MathUtil;
import rajawali.math.Number3D;
import rajawali.math.Quaternion;
import rajawali.parser.ObjParser;
import rajawali.primitives.Cube;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.FloatMath;
import android.util.Log;
import android.widget.TextView;

public class HoopsRenderer extends RajawaliRenderer {
	private BaseObject3D mHoop;
	private float mTime;
	private float mLastTime;
    private Number3D mAccValues;
    
    private Player mPlayer = null;
	
	public HoopsRenderer(Context context)
	{
		super(context);
		setFrameRate(30);
        mAccValues = new Number3D();
	}

	protected void initScene() {
		DirectionalLight light = new DirectionalLight(0, -.6f, .4f);
		light.setPower(1);

		setSkybox( R.drawable.stormy_front, R.drawable.stormy_right, R.drawable.stormy_back, R.drawable.stormy_left, R.drawable.stormy_top, R.drawable.stormy_bottom );
		this.mSkybox.setScale( 100.0f );

		mPlayer = new Player( mContext, mTextureManager, light );
        addChild( mPlayer.getObject() );

        try
        {
            ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.bumptorus);
            objParser.parse();
            mHoop = objParser.getParsedObject();
            
            mHoop.setMaterial(new DiffuseMaterial());
            mHoop.addLight(light);
            //mHoop.addTexture(mTextureManager.addTexture(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.hoop_mtl)));
            mHoop.setScale(5f);
            mHoop.setShowBoundingVolume(true);
            mHoop.setRotX( 90.0f );
            addChild(mHoop);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
		
		// -- create a chase camera
		//    the first parameter is the camera offset
		//    the second parameter is the interpolation factor
		mCamera = new ChaseCamera(new Number3D(0, 0, 16), .2f);
		// -- tell the camera which object to chase
		((ChaseCamera)mCamera).setObjectToChase( mPlayer.getObject() );
		// -- set the far plane to 1000000 so that we actually see the sky sphere
		mCamera.setFarPlane(1000000);
        
		
		if( mHoop.getGeometry().getVertices()  == null )
		    Log.w( "HERE", "Nothing in vertices!" );
		
        IBoundingVolume bbox = mHoop.getGeometry().getBoundingBox();
        bbox.transform(mHoop.getModelMatrix());

        /*IBoundingVolume bbox2 = mPlayer.getObject().getGeometry().getBoundingBox();
        bbox2.transform(mPlayer.getObject().getModelMatrix());

        if( bbox.intersectsWith(bbox2) )
        {
            mPlayer.score__++;
        }*/
	}
	
	public void setCameraOffset(Number3D offset) {
		// -- change the camera offset
		((ChaseCamera)mCamera).setCameraOffset(offset);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}
	
	public void onDrawFrame(GL10 glUnused)
	{
		super.onDrawFrame(glUnused);
		
		mPlayer.update( mAccValues.x, mAccValues.y );
        
		mTime += .01f;
	}

    public void setAccelerometerValues(float x, float y, float z) {
        mAccValues.setAll( -x, -y, -z);
    }
    
    public int getFrames()
    {
        int diff = (int)( ( mTime - mLastTime ) * 100 );
        mLastTime = mTime;
        return diff;
    }
    
    public int getScore()
    {
        if( mPlayer == null )
            return 0;
        return mPlayer.score__;
    }
}
