package com.example.ml_example.fragments;

import java.util.zip.Inflater;

import com.example.ml_example.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class ProductFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.product_detail, container, false);
		return view;
	}
	
	public void loadUrl(String url){
		WebView webView = (WebView) getView().findViewById(R.id.webView);
		webView.loadUrl(url);
	}
	
}
