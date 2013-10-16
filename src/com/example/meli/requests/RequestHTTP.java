package com.example.meli.requests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.meli.fragments.ProductListFragment;
import com.example.meli.objects.MeliProduct;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class RequestHTTP extends AsyncTask<String, String, ArrayList<MeliProduct>>{

	Activity activityContext;
	ArrayList<MeliProduct> meliProducts;
	
	public RequestHTTP(Activity activity){
		activityContext = activity;
	}
	@Override
	protected ArrayList<MeliProduct> doInBackground(String... params) {
		HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        JSONObject meliResponse = null;
        
        try {        	
            response = httpclient.execute(new HttpGet("https://api.mercadolibre.com/sites/MLA/search?q="+params[0]+"&limit=20"));
//          response = httpclient.execute(new HttpGet("https://api.mercadolibre.com/sites/MLA/search?q=ipod"));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();                
                meliResponse = new JSONObject(out.toString());
                Log.d("MELI RESPONSE", meliResponse.toString());
                meliProducts = new ArrayList<MeliProduct>();
                JSONArray array = meliResponse.getJSONArray("results");        		
        		for (int i = 0; i < array.length(); i++) {
					MeliProduct product = new MeliProduct();					
					product.setTitle(array.getJSONObject(i).getString("title"));
					product.setDescription(array.getJSONObject(i).getString("subtitle"));
					product.setPrice(array.getJSONObject(i).getDouble("price"));
					product.setImage(loadImageFromURL(array.getJSONObject(i).getString("thumbnail"), ""+i));
					product.setUrl(array.getJSONObject(i).getString("permalink"));
					meliProducts.add(product);
				}
                
            } else{               
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            Log.e("ERROR", e.getMessage());
        } catch (IOException e) {
        	Log.e("ERROR", e.getMessage());
        } catch (JSONException e) {
        	Log.e("ERROR", e.getMessage());
        }
        return meliProducts;
		
	}
	
	protected void onPostExecute(ArrayList<MeliProduct> result) {
        super.onPostExecute(result);
        ProductListFragment fragment = (ProductListFragment) activityContext.getFragmentManager().findFragmentByTag("list");
        if(fragment != null){
        	fragment.setItems(result);
        }
        
    }
	
	public static Drawable loadImageFromURL(String url, String name) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, name);
	        return d;
	    } catch (Exception e) {
	    	Log.e("ERROORR", e.getMessage());
	        return null;
	    }
	}
}
