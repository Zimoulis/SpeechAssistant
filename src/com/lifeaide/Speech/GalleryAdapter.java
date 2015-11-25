package com.lifeaide.Speech;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/*
 THIS CLASS UPDATES THE TOP WHITE BAR. THIS CLASS IS ONLY RESPONSIBLE FOR DISPLAYING
 THE IMAGES IN THE ARRAY THAT IS PASSED IN
 * */
public class GalleryAdapter extends BaseAdapter {
	 	int mGalleryItemBackground;
	    private Context mContext;

	    private Bitmap[] pictures;

	    
	    public GalleryAdapter(Context c, Bitmap[] p) {
	        mContext = c;
	        pictures= p;
	        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);//sets xml to variable 
	        mGalleryItemBackground = attr.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground, 0); 
	        attr.recycle();
	    }

		public int getCount() {
	        return pictures.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    //THIS IS WHERE THE PICTURES ARE SCALED AND SET ACCORDING TO THE ARRAY 
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView = new ImageView(mContext);
	        
	        imageView.setImageBitmap(pictures[position]);//SET PICTURE
	        //Adjustments of XML
	        imageView.setLayoutParams(new Gallery.LayoutParams(160, 140));
	        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        imageView.setBackgroundResource(mGalleryItemBackground);
	       
	        
	        return imageView; //THIS RETURNS THE DATA OF WHATS DISPLAYED IN THE WHITE BOX
	        }
	}