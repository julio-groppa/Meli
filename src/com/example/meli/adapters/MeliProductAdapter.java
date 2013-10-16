package com.example.meli.adapters;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.meli.R;
import com.example.meli.objects.MeliProduct;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MeliProductAdapter extends ArrayAdapter<MeliProduct>{
	
	Context context;
	public MeliProductAdapter(Context context, int textViewResourceId, ArrayList<MeliProduct> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		MeliHolder holder = null;
        MeliProduct rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.meli_row, null);
            holder = new MeliHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.menu_name);
            holder.txtDesc = (TextView) convertView.findViewById(R.id.description);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.price);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_image);
            convertView.setTag(holder);
        } else
            holder = (MeliHolder) convertView.getTag();

        holder.txtDesc.setText(rowItem.getDescription());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.txtPrice.setText(String.valueOf(rowItem.getPrice()) + " ARS");
        holder.imageView.setImageDrawable(rowItem.getImage());

       
        
        return convertView;		
	}
	
	private class MeliHolder{
		ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
        TextView txtPrice;
	}

}
