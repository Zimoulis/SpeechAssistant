package com.lifeaide.Speech;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/*
 THIS MENU IS NOT VISABLE OR USABLE BY THE USER IT IS STRICTLY CLASSES I USED TO DEVELOP AND TEST INDIVDUAL ACTIVITIES
 BY ANDING THI MENU TO THE MODERATOR MENU YOU CAN USE THE TOOLS I USED IF YOU NEED TO DEBUG ACTIVITIES
 OTHERWISE THIS SERVES NO PURPOSE BESIDES ERROR AND BUG DETECTION
 * */



public class Menu extends ListActivity {

	private static final String TAG = "activity for speech";
	String classes[] ={"Menu","DB_Backend","TextVoice","ExternalData",
			"WriteExternalData","SpeechAssistant"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_expandable_list_item_1, classes));
		
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String classname = classes[position];
		
		try {
	
		Class<?> ourClass = Class.forName("com.lifeaide.Speech."+ classname);
		
		Intent ourIntent = new Intent(Menu.this, ourClass);
		
		startActivity(ourIntent);
		
		finish(); 
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	

	
	
	protected void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
		
	}
	
	
}
