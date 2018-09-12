package com.tricktekno.animatedsplash;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by root on 17/2/18.
 */



public class FirstFragment extends Fragment implements View.OnClickListener {


        static  int coverage=0, downspeed=0,droprate=0,latency=0;
        static int info_withplan=-1;
        String text;
        Bundle b;
        EditText etxtName, etxtDesc;
        Button btnSubmit;
        static String name="helo";
        static String desc="dear";

        static  CheckBox f1q1cb1, f1q1cb2, f1q1cb3, f1q1cb4;
        static  CheckBox f1q2cb1, f1q2cb2, f1q2cb3;



        private OnFragmentInteractionListener mListener;
        // public FirstFragment() {
        public  FirstFragment newInstance() {
        FirstFragment f = new FirstFragment();
        b = new Bundle();
        b.putString("msg", "msge");

        f.setArguments(b);

        return f;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment1, container, false);
    }



   /* @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etxtName = (EditText) view.findViewById(R.id.etxtName);
        etxtDesc = (EditText) view.findViewById(R.id.etxtDesc);
        btnSubmit = (Button) view.findViewById(R.id.btnSu);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etxtName.getText().toString().trim();
                desc = etxtDesc.getText().toString().trim();

                if(name == null || desc == null) {
                    //Toast.makeText(getActivity(), "Both fields required", Toast.LENGTH_SHORT).show();
                } else {
                    mListener.onFragmentInteraction(name, desc);
                    etxtName.setText("");
                    etxtDesc.setText("");
                }
            }
        });
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        f1q1cb1 = (CheckBox) view.findViewById(R.id.f1q1cb1);
        f1q1cb1.setOnClickListener(this);

        f1q1cb2 = (CheckBox) view.findViewById(R.id.f1q1cb2);
        f1q1cb2.setOnClickListener(this);

        f1q1cb3 = (CheckBox) view.findViewById(R.id.f1q1cb3);
        f1q1cb3.setOnClickListener(this);

        f1q1cb4 = (CheckBox) view.findViewById(R.id.f1q1cb4);
        f1q1cb4.setOnClickListener(this);

        f1q2cb1 = (CheckBox) view.findViewById(R.id.f1q2cb1);
        f1q2cb1.setOnClickListener(this);

        f1q2cb2 = (CheckBox) view.findViewById(R.id.f1q2cb2);
        f1q2cb2.setOnClickListener(this);

        f1q2cb3 = (CheckBox) view.findViewById(R.id.f1q2cb3);
        f1q2cb3.setOnClickListener(this);



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String name, String desc);
    }


    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.f1q1cb1:
                if(f1q1cb1.isChecked())
                    downspeed=1;
                else
                    downspeed=0;

                break;
            case R.id.f1q1cb2:
                if(f1q1cb2.isChecked())
                    coverage=1;
                else
                    coverage=0;

                break;

            case R.id.f1q1cb3:
                if(f1q1cb3.isChecked())
                    latency=1;
                else
                    latency=0;
                break;
            case R.id.f1q1cb4:
                if(f1q1cb4.isChecked())
                    droprate=1;
                else
                    droprate=0;
                break;




            case R.id.f1q2cb1:

                f1q2cb2.setChecked(false);
                f1q2cb3.setChecked(false);
                if(f1q2cb1.isChecked())
                    info_withplan=1; //yes
                else
                    info_withplan=0; //blank

                break;

            case R.id.f1q2cb2:

                f1q2cb1.setChecked(false);
                f1q2cb3.setChecked(false);
                if(f1q2cb1.isChecked())
                    info_withplan=0; //no
                else
                    info_withplan=0; //blank

                break;

            case R.id.f1q2cb3:

                f1q2cb1.setChecked(false);
                f1q2cb2.setChecked(false);
                if(f1q2cb1.isChecked())
                    info_withplan=2; //may be
                else
                    info_withplan=0; //blank
                break;
        }

    }


}