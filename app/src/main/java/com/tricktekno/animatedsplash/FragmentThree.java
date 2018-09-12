package com.tricktekno.animatedsplash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

/**
 * Created by root on 17/2/18.
 */
public class FragmentThree extends Fragment implements View.OnClickListener {


    static CheckBox f3q3s1cb1, f3q3s1cb2,f3q3s1cb3  ;
    static CheckBox f3q3s2cb1, f3q3s2cb2,f3q3s2cb3  ;
    static CheckBox f3q3s3cb1, f3q3s3cb2,f3q3s3cb3  ;

    static int info_easy=-1,info_useful=-1,info_enough=-1;

    public FragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment3, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Log.i("TAGmmm", FirstFragment.name);
        super.onViewCreated(view, savedInstanceState);
        f3q3s1cb1 = (CheckBox) view.findViewById(R.id.f3q3s1cb1);
        f3q3s1cb1.setOnClickListener(this);

        f3q3s1cb2 = (CheckBox) view.findViewById(R.id.f3q3s1cb2);
        f3q3s1cb2.setOnClickListener(this);

        f3q3s1cb3 = (CheckBox) view.findViewById(R.id.f3q3s1cb3);
        f3q3s1cb3.setOnClickListener(this);

        f3q3s2cb1 = (CheckBox) view.findViewById(R.id.f3q3s2cb1);
        f3q3s2cb1.setOnClickListener(this);

        f3q3s2cb2 = (CheckBox) view.findViewById(R.id.f3q3s2cb2);
        f3q3s2cb2.setOnClickListener(this);

        f3q3s2cb3 = (CheckBox) view.findViewById(R.id.f3q3s2cb3);
        f3q3s2cb3.setOnClickListener(this);


        f3q3s3cb1 = (CheckBox) view.findViewById(R.id.f3q3s3cb1);
        f3q3s3cb1.setOnClickListener(this);


        f3q3s3cb2 = (CheckBox) view.findViewById(R.id.f3q3s3cb2);
        f3q3s3cb2.setOnClickListener(this);


        f3q3s3cb3 = (CheckBox) view.findViewById(R.id.f3q3s3cb3);
        f3q3s3cb3.setOnClickListener(this);




    }

    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.f3q3s1cb1:

                f3q3s1cb2.setChecked(false);
                f3q3s1cb3.setChecked(false);
                if(f3q3s1cb1.isChecked())
                    info_easy=1;
                else
                    info_easy=0;

                break;

            case R.id.f3q3s1cb2:

                f3q3s1cb1.setChecked(false);
                f3q3s1cb3.setChecked(false);
                if(f3q3s1cb2.isChecked())
                    info_easy=0;


                break;

            case R.id.f3q3s1cb3:

                f3q3s1cb1.setChecked(false);
                f3q3s1cb2.setChecked(false);

                if(f3q3s1cb3.isChecked())
                    info_easy=2;
                else
                    info_easy=0;

                break;



            case R.id.f3q3s2cb1:

                f3q3s2cb2.setChecked(false);
                f3q3s2cb3.setChecked(false);

                if(f3q3s2cb1.isChecked())
                    info_useful=1;
                else
                    info_useful=0;

                break;

            case R.id.f3q3s2cb2:

                f3q3s2cb1.setChecked(false);
                f3q3s2cb3.setChecked(false);

                if(f3q3s2cb2.isChecked())
                    info_useful=0;


                break;

            case R.id.f3q3s2cb3:

                f3q3s2cb1.setChecked(false);
                f3q3s2cb2.setChecked(false);

                if(f3q3s2cb3.isChecked())
                    info_useful=2;
                else
                    info_useful=0;

                break;


            case R.id.f3q3s3cb1:

                f3q3s3cb2.setChecked(false);
                f3q3s3cb3.setChecked(false);

                if(f3q3s3cb1.isChecked())
                    info_enough=1;
                else
                    info_enough=0;

                break;

            case R.id.f3q3s3cb2:

                f3q3s3cb1.setChecked(false);
                f3q3s3cb3.setChecked(false);

                if(f3q3s3cb2.isChecked())
                    info_enough=0;


                break;

            case R.id.f3q3s3cb3:

                f3q3s3cb1.setChecked(false);
                f3q3s3cb2.setChecked(false);
                if(f3q3s3cb2.isChecked())
                    info_enough=2;
                else
                    info_enough=0;

                break;


        }

    }



}
