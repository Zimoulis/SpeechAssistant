package com.lifeaide.Speech;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/* THIS CLASS TAKES THE XML AND  INFLATES A STRING AND BITMAP INTO A UNIQUE ITEM
 	THAT ITEM IS THEN FORMATED AND SCALED ACORDING ON THE GRID PRESENTED IN THE XML
 */

public class GridAdapter extends BaseAdapter {
    
	private Bitmap[] pictures = null;
	private String[] texts = null;
	private Context mContext;
   
	public GridAdapter(Context c, Bitmap[] x, String[] w) {
        mContext = c;
        pictures = x; 
        texts =	   w;
    }
    public int getCount() {
        return pictures.length;
    }

    public Object getItem(int position) {
        return null;
   }

    public long getItemId(int position) {
        return 0;
    }

//    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	
        View v;
        		//THIS INFLATES THE BITMAP AND STRING AND ALLOWS THE TEXT TO APEAR UNDER THE PICTURE
	            LayoutInflater li = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            	v = li.inflate(R.layout.grid_item, null);
				            TextView tv = (TextView)	v.findViewById(R.id.icon_text);
				            	tv.setText(texts[position]);
				            ImageView iv = (ImageView)	v.findViewById(R.id.icon_image);
				            	iv.setImageBitmap(pictures[position]);

        return v;
    }


    	
    	
    	
 }
   