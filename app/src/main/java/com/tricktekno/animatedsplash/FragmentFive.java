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
public class FragmentFive extends Fragment implements View.OnClickListener {


    static CheckBox f5q5s1cb1, f5q5s1cb2,f5q5s1cb3  ;
    static CheckBox f5q5s2cb1, f5q5s2cb2,f5q5s2cb3  ;
    static CheckBox f5q5s3cb1, f5q5s3cb2,f5q5s3cb3  ;
    static CheckBox f5q5s4cb1, f5q5s4cb2,f5q5s4cb3  ;
    static int label_easy=-1,label_everything=-1,label_enough=-1,label_std=-1;



    public FragmentFive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment5, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Log.i("TAGmmm", FirstFragment.name);

        super.onViewCreated(view, savedInstanceState);
        f5q5s1cb1 = (CheckBox) view.findViewById(R.id.f5q5s1cb1);
        f5q5s1cb1.setOnClickListener(this);

        f5q5s1cb2 = (CheckBox) view.findViewById(R.id.f5q5s1cb2);
        f5q5s1cb2.setOnClickListener(this);

        f5q5s1cb3 = (CheckBox) view.findViewById(R.id.f5q5s1cb3);
        f5q5s1cb3.setOnClickListener(this);

        f5q5s2cb1 = (CheckBox) view.findViewById(R.id.f5q5s2cb1);
        f5q5s2cb1.setOnClickListener(this);

        f5q5s2cb2 = (CheckBox) view.findViewById(R.id.f5q5s2cb2);
        f5q5s2cb2.setOnClickListener(this);

        f5q5s2cb3 = (CheckBox) view.findViewById(R.id.f5q5s2cb3);
        f5q5s2cb3.setOnClickListener(this);


        f5q5s3cb1 = (CheckBox) view.findViewById(R.id.f5q5s3cb1);
        f5q5s3cb1.setOnClickListener(this);


        f5q5s3cb2 = (CheckBox) view.findViewById(R.id.f5q5s3cb2);
        f5q5s3cb2.setOnClickListener(this);


        f5q5s3cb3 = (CheckBox) view.findViewById(R.id.f5q5s3cb3);
        f5q5s3cb3.setOnClickListener(this);

        f5q5s4cb1 = (CheckBox) view.findViewById(R.id.f5q5s4cb1);
        f5q5s4cb1.setOnClickListener(this);


        f5q5s4cb2 = (CheckBox) view.findViewById(R.id.f5q5s4cb2);
        f5q5s4cb2.setOnClickListener(this);


        f5q5s4cb3 = (CheckBox) view.findViewById(R.id.f5q5s4cb3);
        f5q5s4cb3.setOnClickListener(this);




    }


    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.f5q5s1cb1:

                f5q5s1cb2.setChecked(false);
                f5q5s1cb3.setChecked(false);
                if (f5q5s1cb1.isChecked())
                    label_easy=1;
                else
                    label_easy=-1;

                break;

            case R.id.f5q5s1cb2:

                f5q5s1cb1.setChecked(false);
                f5q5s1cb3.setChecked(false);
                if (f5q5s1cb2.isChecked())
                    label_easy=0;
                else
                    label_easy=-1;


                break;

            case R.id.f5q5s1cb3:

                f5q5s1cb1.setChecked(false);
                f5q5s1cb2.setChecked(false);

                if (f5q5s1cb3.isChecked())
                    label_easy=2;
                else
                    label_easy=-1;


                break;


            case R.id.f5q5s2cb1:

                f5q5s2cb2.setChecked(false);
                f5q5s2cb3.setChecked(false);
                if (f5q5s2cb1.isChecked())
                    label_everything=1;
                else
                    label_everything=-1;


                break;

            case R.id.f5q5s2cb2:

                f5q5s2cb1.setChecked(false);
                f5q5s2cb3.setChecked(false);
                if (f5q5s2cb2.isChecked())
                    label_everything=1;
                else
                    label_everything=-1;

                break;

            case R.id.f5q5s2cb3:

                f5q5s2cb1.setChecked(false);
                f5q5s2cb2.setChecked(false);

                if (f5q5s2cb3.isChecked())
                    label_everything=2;
                else
                    label_everything=-1;

                break;


            case R.id.f5q5s3cb1:

                f5q5s3cb2.setChecked(false);
                f5q5s3cb3.setChecked(false);

                if (f5q5s3cb1.isChecked())
                    label_enough=1;
                else
                    label_enough=-1;

                break;

            case R.id.f5q5s3cb2:

                f5q5s3cb1.setChecked(false);
                f5q5s3cb3.setChecked(false);

                if (f5q5s3cb2.isChecked())
                    label_enough=0;
                else
                    label_enough=-1;

                break;

            case R.id.f5q5s3cb3:

                f5q5s3cb1.setChecked(false);
                f5q5s3cb2.setChecked(false);

                if (f5q5s3cb3.isChecked())
                    label_enough=2;
                else
                    label_enough=-1;

                break;

            case R.id.f5q5s4cb1:

                f5q5s4cb2.setChecked(false);
                f5q5s4cb3.setChecked(false);


                if (f5q5s4cb1.isChecked())
                    label_std=1;
                else
                    label_std=-1;

                break;

            case R.id.f5q5s4cb2:

                f5q5s4cb1.setChecked(false);
                f5q5s4cb3.setChecked(false);

                if (f5q5s4cb2.isChecked())
                    label_std=0;
                else
                    label_std=-1;

                break;

            case R.id.f5q5s4cb3:

                f5q5s4cb1.setChecked(false);
                f5q5s4cb2.setChecked(false);


                if (f5q5s4cb3.isChecked())
                    label_std=2;
                else
                    label_std=-1;

                break;


        }

    }

    }
