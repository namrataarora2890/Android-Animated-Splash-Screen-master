package com.tricktekno.animatedsplash;

/**
 * Created by root on 17/2/18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ScreenSlidePagerActivity extends FragmentActivity implements FirstFragment.OnFragmentInteractionListener,View.OnClickListener {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */


    static FirstFragment fragOne;
    static SecondFragment fragTwo;
    public static final int NUM_PAGES = 5;


   // Button butt;
    EditText mEdit;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    public ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    public  PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


     //   butt = (Button) findViewById(R.id.btnSu);
     //   butt.setOnClickListener(this);
        setContentView(R.layout.activity_screen_slide);
        fragOne = new FirstFragment();
        fragTwo = new SecondFragment();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {

                Log.i("Hi hu haaa", "touched down");
            }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //Log.i("hehe", "touched down");
            }

            public void onPageSelected(int position) {
                Log.i("lolz", "touched down");
                // Check if this is the page you want.
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            this.startActivity(new Intent(ScreenSlidePagerActivity.this, SecondActivity.class));

            super.onBackPressed();

        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    @Override
    public void onFragmentInteraction(String name, String desc) {
        //mPagerAdapter.onFragmentInteraction(name, desc);
        Log.i("Nameezzz", name);


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                //mEdit   = (EditText)findViewById(R.id.etxtName);
                //Log.v("EditText value=", mEdit.getText().toString());

                //Replace default fragment

                break;

        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence .
     */
    public static  class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter implements FirstFragment.OnFragmentInteractionListener {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new FirstFragment();

                case 1:
                    return new FragmentThree();

                case 2:
                    return new FragmentFour();

                case 3:
                    return new FragmentFive();
                case 4:

                    return new FragmentSix();
            }
            return null;
        }


        @Override
        public int getCount() {
            return NUM_PAGES;
        }
        @Override
        public void onFragmentInteraction(String name, String desc) {
            //fragTwo.onFragmentInteraction(name, desc);
            Log.i("Inside *", name);

        }

    }

}

