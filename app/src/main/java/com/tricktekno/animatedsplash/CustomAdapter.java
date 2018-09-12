package com.tricktekno.animatedsplash;

/**
 * Created by root on 29/11/17.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Oclemy on 12/7/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class CustomAdapter extends BaseAdapter {
    private ArrayList<Spaceship> spaceships;

    List<Integer> myColors = Arrays.asList(new Integer[]{Color.parseColor("#fce9f0"),Color.parseColor("#fbe689"),Color.parseColor("#d5c6ae"),Color.parseColor("#a7bd5b"),Color.parseColor("#ffc0cb"),Color.parseColor("#fce9f0"),Color.parseColor("#80ff66"),Color.RED,Color.BLUE,Color.GRAY,Color.GREEN,Color.YELLOW,Color.CYAN});
    private int lastPos = -1;
    private Context c;
    public CustomAdapter(Context c,ArrayList<Spaceship> spaceships) {
        this.spaceships = spaceships;
        this.c = c;
    }
    @Override
    public int getCount() {
        return spaceships.size();
    }
    @Override
    public Object getItem(int i) {
        return spaceships.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    /*
    INFLATE XML LAYOUT TO VIEW
     */
    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view= LayoutInflater.from(c).inflate(R.layout.model,viewGroup,false);
            view.setBackgroundColor(Color.CYAN);

            //
            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_list));
            //view.setBackgroundColor(myColors.get(pos));
            view.setBackgroundColor(Color.parseColor("#EDEDED"));



        }

        final Spaceship s= (Spaceship) this.getItem(pos);
        TextView nameTxt= (TextView) view.findViewById(R.id.nameTxt);
       // ImageView img= (ImageView) view.findViewById(R.id.spacecraftImage);


        TextView tvlat= (TextView) view.findViewById(R.id.latencyval);
        TextView tvdrop= (TextView) view.findViewById(R.id.droprateval);
        TextView tvspeed= (TextView) view.findViewById(R.id.speedval);
        //TextView tvthroughput= (TextView) view.findViewById(R.id.throughputval);
        tvlat.setText(String.valueOf(s.getLatency()));
        tvdrop.setText(String.valueOf(s.getDroprate()));
        tvspeed.setText(String.valueOf(s.getMin_download_speed()));
        //tvthroughput.setText(String.valueOf(s.getAvg_throughput()));


       // SimpleRatingBar ratingBar= (SimpleRatingBar) view.findViewById(R.id.ratingBarID);
        SimpleRatingBar whatsapp_ratingBar= (SimpleRatingBar) view.findViewById(R.id.whatsapp_rating);
        SimpleRatingBar gmaps_ratingBar= (SimpleRatingBar) view.findViewById(R.id.gmaps_rating);
        SimpleRatingBar facebook_ratingBar= (SimpleRatingBar) view.findViewById(R.id.facebook_rating);
        SimpleRatingBar webbrowsing_ratingBar= (SimpleRatingBar) view.findViewById(R.id.webbrowsing_rating);
        SimpleRatingBar youtube_ratingBar= (SimpleRatingBar) view.findViewById(R.id.youtube_rating);
        SimpleRatingBar hdvideo_ratingBar= (SimpleRatingBar) view.findViewById(R.id.hdvideo_rating);



        nameTxt.setText(s.getName());
        //ratingBar.setRating(s.getRating());
        //img.setImageResource(s.getImage());
        whatsapp_ratingBar.setRating(s.getWhatsapp_rating());
        facebook_ratingBar.setRating(s.getFb_rating());
        gmaps_ratingBar.setRating(s.getGmpas_rating());
        webbrowsing_ratingBar.setRating(s.getWebbrowsing_rating() );
        youtube_ratingBar.setRating(s.getYoutube_rating());
        hdvideo_ratingBar.setRating(s.getHd_rating());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(c, s.getName()+ " Rating : "+String.valueOf(s.getRating()), Toast.LENGTH_SHORT).show();
            }
        });

        //Animation animation = AnimationUtils.loadAnimation(QualitativePerformance.getAppContext(), (pos > lastPos) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //view.startAnimation(animation);
        //lastPos = pos;
        return view;
    }
}

//https://stackoverflow.com/questions/35919060/animating-display-of-items-in-listview-in-android