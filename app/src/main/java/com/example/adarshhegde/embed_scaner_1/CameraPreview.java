package com.example.adarshhegde.embed_scaner_1;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private static final String TAG = "camera";
    private int mWidth, mHeight;
    private Context mContext;
    private MultiFormatReader mMultiFormatReader;
    private AlertDialog mDialog;
    private int mLeft, mTop, mAreaWidth, mAreaHeight;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        Parameters params = mCamera.getParameters();
        
        mWidth = 640;
        mHeight = 480;
        
        params.setPreviewSize(mWidth, mHeight); 
        mCamera.setParameters(params);
        
        mMultiFormatReader = new MultiFormatReader();
        
        mDialog =  new AlertDialog.Builder(mContext).create();
    }

    @Override
	public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
	public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        if (mHolder.getSurface() == null){
          return;
        }

        try {
            mCamera.stopPreview();
            
        } catch (Exception e){

        }

        try {
            mCamera.setPreviewCallback(mPreviewCallback);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
    
    public void setCamera(Camera camera) {
    	mCamera = camera;
    }
    
    public void onPause() {
    	if (mCamera != null) {
    		mCamera.setPreviewCallback(null);
    		mCamera.stopPreview();
    	}
    }
    
    private PreviewCallback mPreviewCallback = new PreviewCallback() {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
        	
        	if (mDialog.isShowing())
        		return;
            Log.i("CamPreCallback","left" + mLeft);
            Log.i("CamPreCallback","top" + mTop);
            Log.i("CamPreCallback","width" + mWidth);
            Log.i("CamPreCallback","Areawidth" + mAreaWidth);
            Log.i("CamPreCallback","AreaHeight" + mAreaHeight);
        	LuminanceSource source = new PlanarYUVLuminanceSource(data, mWidth, mHeight, mLeft, mTop, mAreaWidth, mAreaHeight, false);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(
              source));
            Result result;
          
            try {
				result = mMultiFormatReader.decode(bitmap, null);
				if (result != null) {
					mDialog.setTitle("Result");
					mDialog.setMessage(result.getText());
					mDialog.show();
				}
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    };
    
    public void setArea(int left, int top, int areaWidth, int width) {
        Log.i("ICamPreSA","left" + left);
        Log.i("ICamPreSA","top" + top);
        Log.i("ICamPreSA","width" + width);
        Log.i("ICamPreSA","Areawidth" + mAreaWidth);
        double ratio = width / mWidth;
    	mLeft = (int) (left / (ratio + 1));
    	mTop = (int) (top / (ratio + 1));
        //mTop = top;
    	mAreaHeight = mAreaWidth = mWidth - mLeft * 2;
        Log.i("CamPreSA","left" + mLeft);
        Log.i("CamPreSA","top" + mTop);
        Log.i("CamPreSA","width" + mWidth);
        Log.i("CamPreSA","Areawidth" + mAreaWidth);
        //mLeft = left;
        //mTop = top;
        //mAreaHeight = mAreaWidth = areaWidth;
        Log.i("CamPre","Area set");
    }
    
}
