package com.example.ml_example.request;

import java.io.InputStream;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;


public class RequestImage extends AsyncTask<String, String, Bitmap>{

	@Override
	protected Bitmap doInBackground(String... params) {
		loadImageFromURL(params[0], "img");
		return null;
	}	
	
	public Drawable loadImageFromURL(String url, String name) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, name);
	        return d;
	    } catch (Exception e) {
	        return null;
	    }
	}

}
