package com.webb.androidmosaic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@TargetApi(9)
public class CameraPreviewActivity extends Activity {
	
	private SurfaceView preview;
	private SurfaceHolder previewHolder;
	private Camera camera = null;
	private boolean inPreview = false;
	private boolean cameraConfigured = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_preview);
        
        preview = (SurfaceView) this.findViewById(R.id.surface_view);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    
    @SuppressWarnings("static-access")
	@TargetApi(9)
	@Override
    public void onResume() {
    	super.onResume();
    	
    	Camera.CameraInfo info = new Camera.CameraInfo();
    	for(int i=0; i < Camera.getNumberOfCameras(); i++) {
    		Camera.getCameraInfo(i, info);
    		
    		if(info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
    			camera = camera.open(i);
    		}
    	}
    	
    	if(camera == null) {
    		camera = Camera.open();
    	}
    	
    	startPreview();
    }
    
    @Override
    public void onPause() {
    	if(inPreview) {
    		camera.stopPreview();
    	}
    	
    	camera.release();
    	camera = null;
    	inPreview = false;
    	super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.camera_preview, menu);
        return true;
    }
    
    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
    	Camera.Size result = null;
    	
    	for(Camera.Size size: parameters.getSupportedPreviewSizes()) {
    		if(size.width <= width && size.height <= height) {
    			if(result == null) {
    				result=size;
    			}
    			else {
    				int resultArea = result.width*result.height;
    				int newArea = size.width*size.height;
    				
    				if(newArea > resultArea) {
    					result = size;
    				}
    			}
    		}
    	}
    	return(result);
   }
    
   private Camera.Size getSmallestPictureSize(Camera.Parameters parameters) {
	   Camera.Size result = null;
	   
	   for(Camera.Size size: parameters.getSupportedPictureSizes()) {
		   if(result == null) {
			   result = size;
		   }
		   else {
			   int resultArea = result.width*result.height;
			   int newArea = size.width*size.height;
			   
			   
			   if(newArea < resultArea) {
				   result = size;
			   }
		   }
	   }
	   return result;
   }
    
   private void initPreivew(int width, int height) {
	   if(camera != null && previewHolder.getSurface() != null) {
		   try {
			   camera.setPreviewDisplay(previewHolder);
		   }
		   catch(Throwable t) {
			   Log.e("Preview-surfaceCallBack", "Exception in setPreviewDisplay", t);
		   }
		   
		   if(!cameraConfigured) {
			   Camera.Parameters parameters = camera.getParameters();
			   Camera.Size size = getBestPreviewSize(width, height, parameters);
			   Camera.Size pictureSize = getSmallestPictureSize(parameters);
			   
			   if(size != null && pictureSize != null) {
				   parameters.setPreviewSize(size.width, size.height);
				   parameters.setPictureSize(pictureSize.width, pictureSize.height);
				   parameters.setPictureFormat(ImageFormat.JPEG);
				   camera.setParameters(parameters);
				   cameraConfigured = true;
			   }
		   }
	   }
   }
   
   private void startPreview() {
	   if(cameraConfigured && camera != null) {
		   camera.startPreview();
		   inPreview = true;
	   }
   }
   
   SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		// no-op
		
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		// no-op
		
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		initPreivew(width, height);
		startPreview();
	}
   };
   
   Camera.PictureCallback photoCallback = new Camera.PictureCallback() {
	
	public void onPictureTaken(byte[] data, Camera camera) {
		new GenerateMosaicTask().execute(data);
	}
};

    
}
