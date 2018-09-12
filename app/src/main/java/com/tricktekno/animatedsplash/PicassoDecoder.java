package com.tricktekno.animatedsplash;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.davemorrissey.labs.subscaleview.decoder.ImageDecoder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by root on 1/9/18.
 */

public class PicassoDecoder implements ImageDecoder
{
    private String tag;
    private Picasso picasso;

    public PicassoDecoder(String tag, Picasso picasso) {
        this.tag = tag;
        this.picasso = picasso;
    }

    @Override
    public Bitmap decode(Context context, Uri uri) throws Exception {
        return picasso
                .load(uri)
                .tag(tag)
                .config(Bitmap.Config.RGB_565)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .get();
    }
}