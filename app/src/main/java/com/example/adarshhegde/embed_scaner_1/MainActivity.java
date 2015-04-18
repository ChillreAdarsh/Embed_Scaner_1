package com.example.adarshhegde.embed_scaner_1;

        import android.app.Activity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Display;
        import android.widget.FrameLayout;

public class MainActivity extends Activity {
    private CameraPreview mPreview;
    private CameraManager mCameraManager;
    private HoverView mHoverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Cam", "Entered create");
        Display display = getWindowManager().getDefaultDisplay();
        Log.i("Cam", "Before hoverview");
        mHoverView = (HoverView)findViewById(R.id.hover_view);
        mHoverView.update(display.getWidth(), display.getHeight());

        Log.i("Cam", "After hoverview");
        mCameraManager = new CameraManager(this);
        Log.i("Cam", "before cam preview");
        mPreview = new CameraPreview(this, mCameraManager.getCamera());
        Log.i("Cam", "After cam preview");
        mPreview.setArea(mHoverView.getHoverLeft(), mHoverView.getHoverTop(), mHoverView.getHoverAreaWidth(), display.getWidth());
        Log.i("Cam", "After set area");
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        Log.i("Cam", "Before action bar");
        //getActionBar().hide();

        Log.i("Cam", "After action bar");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreview.onPause();
        mCameraManager.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mCameraManager.onResume();
        mPreview.setCamera(mCameraManager.getCamera());
    }
}

