package com.tricktekno.animatedsplash;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by root on 14/1/18.
 */

public class ImageMapTestActivity extends Activity {
    ImageMap mImageMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapper_activity);

        // find the image map in the view
        mImageMap = (ImageMap)findViewById(R.id.map);
        mImageMap.setOnTouchListener(handleTouch);


        //getLocationOnScreen(findViewById(R.id.map));

        // add a click handler to react when areas are tapped

    }
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //ImageView img = (ImageView) findViewById(R.id.img);
            mImageMap = (ImageMap)findViewById(R.id.map);
            getLocationOnScreen(mImageMap);

        }
        mImageMap.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {

            @Override
            public void onImageMapClicked(int id, ImageMap mImageMap) {
                // when the area is tapped, show the name in a
                // text bubble
                mImageMap.showBubble(id);
                Log.i("DoubleTap  !!", "image clicked");
            }

            @Override
            public void onBubbleClicked(int id) {
                // react to info bubble for area being tapped
            }
        });
    }



    private Rect getLocationOnScreen(View mView) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mView.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mView.getWidth();
        mRect.bottom = location[1] + mView.getHeight();
        Log.i("DoubleTap........", "image clicked" +String.valueOf(location[0])+String.valueOf(location[1])+String.valueOf(mRect.right)+String.valueOf(mRect.bottom));

        return mRect;
    }

    private View.OnTouchListener handleTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i("TAG", "touched down");
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i("TAG", "moving: (" + x + ", " + y + ")");
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i("TAG", "touched up");
                    break;
            }

            Log.i("Touch Event........", "image clicked" +String.valueOf(x)+"     "+String.valueOf(y)+"     "+String.valueOf(getColour(x,y)));

            return true;
        }
    };
    private int getColour( int x, int y)
    {
        ImageView img = (ImageView) findViewById(R.id.map);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots=Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }

}

