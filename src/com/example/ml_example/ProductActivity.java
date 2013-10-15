package com.example.ml_example;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;

public class ProductActivity extends Activity {	
	 WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
		      finish();
		      return;
		    }
		setContentView(R.layout.activity_product);
		Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	      String s = extras.getString("url");
	      Log.d("URL", s);
	      webView = (WebView) findViewById(R.id.webView);
	      webView.loadUrl(s);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		getMenuInflater().inflate(R.menu.product, menu);
		return true;
	}

}
