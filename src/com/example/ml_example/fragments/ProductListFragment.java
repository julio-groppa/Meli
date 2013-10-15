package com.example.ml_example.fragments;

import java.util.ArrayList;

import org.json.JSONObject;

import com.example.ml_example.R;
import com.example.ml_example.adapters.MeliProductAdapter;
import com.example.ml_example.meli.MeliProduct;
import com.example.ml_example.request.RequestHTTP;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ProductListFragment extends Fragment{
	
	OnArticleSelectedListener mListener;
	private ListView listView;    
    private MeliProductAdapter meliAdapter;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);			
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.product_list, null);
		
		listView = (ListView) rootView.findViewById(R.id.meliListView);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				MeliProduct meliProduct = (MeliProduct) meliAdapter.getItem(arg2);
				mListener.onArticleSelected(meliProduct);
				
			}
		});
		
        return rootView;		
	}
	
	public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnArticleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }
	
	public interface OnArticleSelectedListener {
        public void onArticleSelected(MeliProduct meliProduct);
    }
	
	public void setItems(ArrayList<MeliProduct> meliProducts){		
		meliAdapter = new MeliProductAdapter(getActivity(), R.id.meliListView, meliProducts);
        listView.setAdapter(meliAdapter);
	}
	
	

}
