package com.example.meli.fragments;

import com.example.meli.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;

public class ProductFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.fragment_product, container, false);
		Bundle extra = this.getArguments();
		if (extra != null){
			WebView webView = (WebView) view.findViewById(R.id.meliWebView);
			webView.loadUrl(extra.getString("url"));
		}
		
		return view;
	}
	
}
