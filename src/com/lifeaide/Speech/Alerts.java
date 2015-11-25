package com.lifeaide.Speech;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/*This class provides the alert that the program has been sucsefully completed.
this alert has it own class as way ensure all previous activities are finished and to provide a transperent background for
astheteics 
*/
public class Alerts extends Activity{

	private AlertDialog alert_completed;

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		completed_alert();
		alert_completed.show(); //shows the created build
	}
	
	//Builds the alert using AlertDialog 
	private void completed_alert()
	{
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage("Installation Successful, Press Ok to begin using Speech Assistant.")
	       .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   Intent openStartingPoint = new Intent("com.lifeaide.Speech.Speech_Grid_FrontEnd");
				   startActivity(openStartingPoint);
				   finish();
	           }
	       });
	alert_completed = builder.create();
	}
	
	
}
