package com.tricktekno.animatedsplash;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import android.util.Log;

public class CustomOnItemSelectedListener extends SecondActivity implements OnItemSelectedListener  {

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener::: : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
                Log.w("Selected item circle ::",parent.getItemAtPosition(pos).toString());
                if (parent.getItemAtPosition(pos).toString()!=null)
                {
                addItemsOnSpinner2();
                }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
                 Log.e("hell you item ::","");
        // TODO Auto-generated method stub
    }


}
