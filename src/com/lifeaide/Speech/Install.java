package com.lifeaide.Speech;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/*
 THIS CLASS IS THE INITIAL SCREEN OF THE PROGRAM IF THE USER HAS NO PREVIOUS DATA
 THIS IS A FORM SO CHECK TO MAKE SURE ALL FIELDS ARE VALID
 IF THEY ARE VALID IT THEN ENTERS THE USER DATA INTO A PREFRENCES XML AND 
 LAUNCHES THE INTILIZE DATABASE ACTIVITY
 * */

public class Install extends Activity implements OnClickListener{

	
	private EditText UserName,ModName,pass1,pass2;
	private AlertDialog alert_wrongpassword,alert_emptyfield;
	private SharedPreferences preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.welcome);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
				
		//GETS XML BUTTONS AND FIELDS
		findViewById(R.id.Button_Submit_WelcomeInfo).setOnClickListener(this);	
		UserName 	= (EditText) findViewById(R.id.ETusername);
		ModName 	= (EditText) findViewById(R.id.ETmodname);
		pass1 	= (EditText) findViewById(R.id.ETpass1);
		pass2 	= (EditText) findViewById(R.id.ETpass2);
		createAlerts();
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
      	case R.id.Button_Submit_WelcomeInfo: 
      		
      		//THIS GETS ALL VALUES FROM FORM
      		String u = UserName.getText().toString();
      		String m = ModName.getText().toString();
      		String p1 = pass1.getText().toString();
      		String p2 = pass2.getText().toString();
      		
      		//CHECKS THAT ALL FIELDS HAVE INPUT AND THAT THERE ARE MATCHING PASWORDS
      		if(p1.equals(p2) && !u.isEmpty() && !m.isEmpty() && !p1.isEmpty() && !p2.isEmpty())
	  			{ 
      			updatePreferenceValue(u,m,p1);
	  			}
      		if(!p1.equals(p2))
	  			{
	  			alert_wrongpassword.show();//WRONG PASSWORD ALERT
	  			}   		
      		else if(u.isEmpty() || m.isEmpty() || p1.isEmpty() || p2.isEmpty())
      			{
      			alert_emptyfield.show(); //NOT ALL FIELDS ARE FILLED ALERT
      			}

    	  break;
		
	}}
	
	//ENTER VALUES INTO USER PREFRENCES
	private void updatePreferenceValue(String username,String modname,String password){
		Editor edit = preferences.edit();
		edit.putString("username", 	username);
		edit.putString("modname", 	modname);
		edit.putString("password", 	password);
		edit.commit();	
		
		startActivity(new Intent(this, WriteExternalData.class)); // Start the intent to write pictures to the external storage device	
	}
	
	

	//BELOW ARE THE ALERTS WHEN THE METHOD IS CALLED IT CREATES THEM. BUT IN ORDER TO DISPLAY THEM YOU HAVE TO DECLARE THE ALERT AND CALL THE .SHOW METHOD
	private void createAlerts()
		{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Please Enter All Fields to Install this Application")
		       .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		alert_emptyfield = builder.create();
		
		
		AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
		builder2.setMessage("\t\t Passwords do not match. \n\t\t\t\tPlease Re-enter")
		       .setCancelable(false)
		       .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		alert_wrongpassword = builder2.create();
		
		
		
		}
	
	//WHEN PROGRAM IS PAUSED DELETE ACTIVITY
	protected void onPause() {
		super.onPause();
		finish(); 
	}
	
	
}
