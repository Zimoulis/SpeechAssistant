package com.lifeaide.Speech;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*
 THIS IS THE MENU THAT THE MODERATOR IS PRESENETED WITH IN ORDER TO EDIT THEIR ENVIORMENT OF THE SPEECH ASSISTENT
 THIS METHOD IS JUST A SWITHCH THAT STARTS THE INTENT OF OTHER ACTIVITIES
 * */



public class ModeratorMenu extends ListActivity {

	private static final String TAG = "activity for speech";
	
	String classes[] ={"SpeechAssistant","CreateWord","CreateFolder","PageBuilder","","","","",""}; //THESE ARE THE CLASSES THAT ARE CALLED
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(ModeratorMenu.this, android.R.layout.simple_expandable_list_item_1, classes));// THE ARRAY IS ADAPTED AND THE GUI IS FORMED
//		ListView lv = getListView();
//		lv.setBackgroundColor(Color.BLACK);
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String classname = classes[position];
		
		try {
	
		Class<?> ourClass = Class.forName("com.lifeaide.Speech."+ classname);
		
		Intent ourIntent = new Intent(ModeratorMenu.this, ourClass);
		
		startActivity(ourIntent); //STARTS THE INTENT FOR WHATEVER ACTIVITY IS SELECTED
		
		//finish(); 
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	

	
	
	protected void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
		
	}
	
	
}
