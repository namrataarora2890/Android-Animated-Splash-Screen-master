package com.tricktekno.animatedsplash;



/**
 * Created by root on 29/11/17.
 */

public class Spaceship {
    /*
    SPACESHIP PROPERTIES
     */
    private int rating;

    private String name;
    private int    image;
    private float whatsapp_rating;
    private float fb_rating;
    private float webbrowsing_rating;
    private float gmpas_rating;
    private float youtube_rating;
    private float hd_rating;

    private float latency ;
    private float droprate;
    private float avg_throughput;
    private float min_download_speed;

    public float getLatency() {
        return latency;
    }

    public void setLatency(float latency) {
        this.latency = latency;
    }

    public float getDroprate() {
        return droprate;
    }

    public void setDroprate(float droprate) {
        this.droprate = droprate;
    }

    public float getAvg_throughput() {
        return avg_throughput;
    }

    public void setAvg_throughput(float avg_throughput) {
        this.avg_throughput = avg_throughput;
    }

    public float getMin_download_speed() {
        return min_download_speed;
    }

    public void setMin_download_speed(float min_download_speed) {
        this.min_download_speed = min_download_speed;
    }






    public float getWhatsapp_rating() {
        return whatsapp_rating;
    }

    public void setWhatsapp_rating(float whatsapp_rating) {
        this.whatsapp_rating = whatsapp_rating;
    }

    public float getFb_rating() {
        return fb_rating;
    }

    public void setFb_rating(float fb_rating) {
        this.fb_rating = fb_rating;
    }

    public float getWebbrowsing_rating() {
        return webbrowsing_rating;
    }

    public void setWebbrowsing_rating(float webbrowsing_rating) {
        this.webbrowsing_rating = webbrowsing_rating;
    }

    public float getGmpas_rating() {
        return gmpas_rating;
    }

    public void setGmpas_rating(float gmpas_rating) {
        this.gmpas_rating = gmpas_rating;
    }

    public float getYoutube_rating() {
        return youtube_rating;
    }

    public void setYoutube_rating(float youtube_rating) {
        this.youtube_rating = youtube_rating;
    }

    public float getHd_rating() {
        return hd_rating;
    }

    public void setHd_rating(float hd_rating) {
        this.hd_rating = hd_rating;
    }




    /*
    GETTERS AND SETTERTS
     */
    public float getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }
}

