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
public class FragmentFour extends Fragment implements View.OnClickListener {

    static  CheckBox f4q4cb1, f4q4cb2, f4q4cb3;
    static int label_understand=-1;

    public FragmentFour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment4, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Log.i("TAGmmm", FirstFragment.name);


        super.onViewCreated(view, savedInstanceState);
        f4q4cb1 = (CheckBox) view.findViewById(R.id.f4q4cb1);
        f4q4cb1.setOnClickListener(this);

        f4q4cb2 = (CheckBox) view.findViewById(R.id.f4q4cb2);
        f4q4cb2.setOnClickListener(this);

        f4q4cb3 = (CheckBox) view.findViewById(R.id.f4q4cb3);
        f4q4cb3.setOnClickListener(this);

    }


    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.f4q4cb1:

                f4q4cb2.setChecked(false);
                f4q4cb3.setChecked(false);
                if(f4q4cb1.isChecked())
                    label_understand=1;
                else
                    label_understand=-1;

                break;

            case R.id.f4q4cb2:

                f4q4cb1.setChecked(false);
                f4q4cb3.setChecked(false);
                if(f4q4cb1.isChecked())
                    label_understand=0;
                else
                    label_understand=-1;


                break;

            case R.id.f4q4cb3:

                f4q4cb1.setChecked(false);
                f4q4cb2.setChecked(false);
                if(f4q4cb1.isChecked())
                    label_understand=2;
                else
                    label_understand=-1;



                break;
        }

    }


}
