package com.lifeaide.Speech;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

/*
 * sTARTING POINT OF THE PROGRAM THIS IS THE FIRST ACTIVITY TO RUN IT CHECKS THE USERS INFO TO SEE IF INSTALL IS NEEDED 
 * */

public class Splash1 extends Activity{
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String x = prefs.getString("username", "IF THERE IS NO VALUE");
		
		//iF THERERS NO VALUE LAUCH INSTALL ACTIVITIES SEQUENCE
		if(x.equals("IF THERE IS NO VALUE"))//first install
			{
			startActivity(new Intent(this, Install.class));
			}
		else//RUN THE PROGRAM AS NORMAL TO SPEECH ASSISTANT
			{
			startActivity(new Intent(this, SpeechAssistant.class));
			}
		}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish(); 
	}
	
	
	
	
	}