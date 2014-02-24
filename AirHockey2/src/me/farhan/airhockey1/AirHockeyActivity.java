package me.farhan.airhockey1;

import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.view.Menu;
import android.widget.Toast;

public class AirHockeyActivity extends Activity {

	private GLSurfaceView glSurfaceView;
	private boolean renderder = false;
	private Context context;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        
        glSurfaceView = new GLSurfaceView(this);
       
        context = AirHockeyActivity.this;
        
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000
        		|| (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
        		&& (Build.FINGERPRINT.startsWith("generic")
        		|| Build.FINGERPRINT.startsWith("unknown")
        		|| Build.MODEL.contains("google_sdk")
        		|| Build.MODEL.contains("Emulator")
        		|| Build.MODEL.contains("Android SDK built for x86")));
        
        if(supportsEs2)
        {
        	glSurfaceView.setEGLContextClientVersion(2);
        	glSurfaceView.setRenderer(new AirHockeyRenderer(context));        	
        	renderder = true;
        }else {
			Toast.makeText(this, "Opengles2 not supported", Toast.LENGTH_LONG).show();
			return;
        }   
        setContentView(glSurfaceView);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	
    	if(renderder)
    		glSurfaceView.onPause();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	if(renderder)
    		glSurfaceView.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
