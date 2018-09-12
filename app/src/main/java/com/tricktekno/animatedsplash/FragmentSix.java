package com.tricktekno.animatedsplash;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tricktekno.animatedsplash.FirstFragment.coverage;
import static com.tricktekno.animatedsplash.FirstFragment.downspeed;
import static com.tricktekno.animatedsplash.FirstFragment.droprate;
import static com.tricktekno.animatedsplash.FirstFragment.info_withplan;
import static com.tricktekno.animatedsplash.FirstFragment.latency;
import static com.tricktekno.animatedsplash.FragmentFive.label_easy;
import static com.tricktekno.animatedsplash.FragmentFive.label_enough;
import static com.tricktekno.animatedsplash.FragmentFive.label_everything;
import static com.tricktekno.animatedsplash.FragmentFive.label_std;
import static com.tricktekno.animatedsplash.FragmentFour.label_understand;
import static com.tricktekno.animatedsplash.FragmentThree.info_easy;
import static com.tricktekno.animatedsplash.FragmentThree.info_enough;
import static com.tricktekno.animatedsplash.FragmentThree.info_useful;
public class FragmentSix extends Fragment implements View.OnClickListener{

    static String name,phone,suggestions;
    EditText f6name,f6phone,f6suggestions;
    Button mButton;
    String ip;
    String msg="Enter complete data please!";


    public FragmentSix() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment6, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Log.i("TAGmmm", FirstFragment.name);
        super.onViewCreated(view, savedInstanceState);
        f6name   = (EditText) view.findViewById(R.id.f6name);
        f6phone   = (EditText) view.findViewById(R.id.f6phone);
        f6suggestions   = (EditText) view.findViewById(R.id.f6suggestions);
        mButton   = (Button) view.findViewById(R.id.btnSu);
        mButton.setOnClickListener(this);
    }
    public void onClick(View view)
    {
        Log.v("Name", f6name.getText().toString());
        name=f6name.getText().toString();
        phone=f6phone.getText().toString();
        suggestions =f6suggestions.getText().toString();

//        Log.v("Phone", f6phone.getText().toString());
//        Log.v("coverage",Integer.toString(coverage));
//        Log.v("droprate",Integer.toString(droprate));
//        Log.v("latency",Integer.toString(FirstFragment.latency));
//        Log.v("downspeed",Integer.toString(FirstFragment.downspeed));

        ip = getString(R.string.ip);
        if(!validate())
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        else {


            new HttpAsyncTask().execute("http://" + ip + "/app_feedback/");
            Toast.makeText(getActivity(), "Thank You for Your Feedback",
                    Toast.LENGTH_LONG).show();
            final Intent intent = new Intent(getActivity(), SecondActivity.class);

            //startActivity(intent);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    getActivity().startActivity(intent);

                }
            }, 1500);

        }



    }


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            //Log.v("do in bckd",Integer.toString(FirstFragment.downspeed));
            return POST(urls[0]);
            //return POST("http://"+ip+"/app_feedback/");
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            Log.v("Posted App Feedback::",Integer.toString(FirstFragment.downspeed));
        }
    }


    public static String POST(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("coverage",coverage);
            jsonObject.accumulate("latency", latency);
            jsonObject.accumulate("droprate",droprate);
            jsonObject.accumulate("downspeed",downspeed);
            jsonObject.accumulate("info_withplan",info_withplan);
            jsonObject.accumulate("info_easy",info_easy);
            jsonObject.accumulate("info_useful",info_useful);
            jsonObject.accumulate("info_enough",info_enough);
            jsonObject.accumulate("label_understand",label_understand);
            jsonObject.accumulate("label_easy",label_easy);
            jsonObject.accumulate("label_everything",label_everything);
            jsonObject.accumulate("label_enough",label_enough);
            jsonObject.accumulate("label_std",label_std);
            jsonObject.accumulate("name",name);
            jsonObject.accumulate("phone",phone);
            jsonObject.accumulate("suggestions",suggestions);


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL


            HttpResponse httpResponse = httpclient.execute(httpPost);
            Log.v("http response",httpResponse.toString());


                // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private boolean validate(){
        if(f6name.getText().toString().trim().equals(""))
            return false;
        else if(!isValidPhone(f6phone.getText().toString().trim()))
        {
            msg="Enter Correct Phone number please";
            return false;
        }
        else
            return true;
    }
    public static boolean isValidPhone(String s)
    {

        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);

        return (m.find() && m.group().equals(s));
    }


}
