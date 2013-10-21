package com.example.meli;

import org.json.JSONObject;

import com.example.meli.fragments.ProductFragment;
import com.example.meli.fragments.ProductListFragment;
import com.example.meli.fragments.ProductListFragment.OnArticleSelectedListener;
import com.example.meli.objects.MeliProduct;
import com.example.meli.requests.RequestHTTP;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnArticleSelectedListener{
	RequestHTTP request;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		request = new RequestHTTP(this);
		handleIntent(getIntent());
		ProductFragment productFragment = new ProductFragment();
		ProductListFragment listFragment = new ProductListFragment();		
		
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			transaction.add(R.id.fragment_container1, listFragment, "list");
			transaction.add(R.id.fragment_container2, productFragment, "product");		
		}else{
			transaction.add(R.id.fragment_container2, listFragment, "list");
		}	
		transaction.commit();
		
	}
	
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
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			ProductFragment productFragment = (ProductFragment) getFragmentManager().findFragmentByTag("product");
			if(productFragment != null){
				WebView webView = (WebView) productFragment.getView().findViewById(R.id.meliWebView);
				if(webView != null){
					webView.loadUrl(meliProduct.getUrl());
				}				
			}
		}else{
			Bundle extras = new Bundle();
			extras.putString("url", meliProduct.getUrl());
			ProductFragment productFragment = new ProductFragment();
			productFragment.setArguments(extras);
			FragmentManager manager = getFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();			
			transaction.replace(R.id.fragment_container2, productFragment, "product");
			transaction.commit();			
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
