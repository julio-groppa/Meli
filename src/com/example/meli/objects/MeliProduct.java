package com.example.meli.objects;

import android.graphics.drawable.Drawable;

public class MeliProduct {
	
	private String title;
	private String description;
	private Double price;
	private Drawable image;
	private String url;

	public MeliProduct(){}
	
	public MeliProduct(String title, String description, Double price, Drawable image){
		this.title = title;
		this.description = description;
		this.price= price;
		this.image = image;		
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Drawable getImage() {
		return image;
	}
	public void setImage(Drawable image) {
		this.image = image;
	}
	
}
