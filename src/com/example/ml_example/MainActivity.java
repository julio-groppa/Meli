package com.example.ml_example;

import org.json.JSONObject;

import com.example.ml_example.fragments.ProductFragment;
import com.example.ml_example.fragments.ProductListFragment;
import com.example.ml_example.fragments.ProductListFragment.OnArticleSelectedListener;
import com.example.ml_example.meli.MeliProduct;
import com.example.ml_example.request.RequestHTTP;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnArticleSelectedListener {
	RequestHTTP request;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		request = new RequestHTTP(this);
		handleIntent(getIntent());

	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			if (haveNetworkConnection()) {
				String query = intent.getStringExtra(SearchManager.QUERY);
				query = query.replaceAll(" ", "+");
				request.execute(query);
			} else {
				Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		return true;
	}

	@Override
	public void onArticleSelected(MeliProduct meliProduct) {
		ProductFragment fragment = (ProductFragment) this.getFragmentManager()
				.findFragmentById(R.id.productFragment);
		if (fragment != null && fragment.isInLayout()) {
			fragment.loadUrl(meliProduct.getUrl());
		} else {
			Intent intent = new Intent(getApplicationContext(),	ProductActivity.class);
			intent.putExtra("url", meliProduct.getUrl());
			startActivity(intent);
		}

	}

	private boolean haveNetworkConnection() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}

}
