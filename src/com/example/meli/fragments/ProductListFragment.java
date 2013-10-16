package com.example.meli.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.meli.R;
import com.example.meli.adapters.MeliProductAdapter;
import com.example.meli.objects.MeliProduct;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;

public class ProductListFragment extends Fragment{
	OnArticleSelectedListener mListener;
	MeliProductAdapter meliAdapter;
	ListView listView;
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_product_list, container, false);
		
		listView = (ListView) view.findViewById(R.id.meliListView);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				MeliProduct meliProduct = (MeliProduct) meliAdapter.getItem(arg2);
				mListener.onArticleSelected(meliProduct);
				
			}
		});
		
		return view;
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
