package com.lifeaide.Speech;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

/*This class has the activity for creating a new folder in the database
it takes a picture and writes as jpg to the sd card and stores the string value of its name in the database
*/
public class CreateFolder extends Activity implements View.OnClickListener
	{
		//Field variables
		private AlertDialog alert_emptyfield;	
		final static int cameraData = 0;
		int currentfolder;
		Button b,inputb; ImageView iv;
		Intent i;
		Spinner spin;
		EditText wordname;
		Bitmap bmp =null;
		DataBaseCommands datasource = new DataBaseCommands(this);
		//---------------
		@Override
			public void onCreate(Bundle savedInstanceState) 
				{
					super.onCreate(savedInstanceState);
					setContentView(R.layout.folderphoto);
						//Declarations for XML
						iv = (ImageView)	findViewById(R.id.imageView1); 	iv.setOnClickListener(this);
						b  = (Button)		findViewById(R.id.bsetwall); 	b.setOnClickListener(this);
						inputb  = (Button)		findViewById(R.id.EnterWORDTODBBUTTONS); 	inputb.setOnClickListener(this);
						wordname = (EditText) findViewById(R.id.ET_WORDNAME);
				}
			public void onClick(View v) 
					{
						switch(v.getId())
							{
								case R.id.bsetwall:		//if Camera button clicked
									i= new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
									startActivityForResult(i, cameraData);
									break;
								case R.id.EnterWORDTODBBUTTONS: // if button clicked enter data into the database
									enter_data_to_db();
									finish();
									break;
							}
					}
			
			//this method returns the camera data as bmp and sets the image to thumbnail
			protected void onActivityResult(int requestCode, int resultCode, Intent data) 
				{
				
				super.onActivityResult(requestCode, resultCode, data);
				if (resultCode == RESULT_OK)
					{
					Bundle extras = data.getExtras();
					bmp = (Bitmap)extras.get("data"); // retrieve bitmap
					iv.setImageBitmap(bmp); //display image
					
					}
				
				}
			
			//checks to make sure all fields are entered then submits to the database and writes to sd
			private void enter_data_to_db() 
				{
					createAlerts();
					String inputword= wordname.getText().toString();
				
		
					if(inputword.isEmpty() || bmp.equals(null)) //if any value is empty display alert 
						{
						alert_emptyfield.show();
						}
		
					else
						{
						datasource.open();
						datasource.createFolder(inputword);	//database input
						String id=datasource.getidOfCreatedword().toString();
							File exst = Environment.getExternalStorageDirectory(); //finds mounted path 
							String Path = exst.getPath() + "/.SpeechAssistant/Folders/"; 
							File file = new File(Path,"f"+id+".jpg");	//builds the pathname		 
								FileOutputStream out;
								try 
									{
										out = new FileOutputStream(file);
										bmp.compress(Bitmap.CompressFormat.PNG, 90, out); //resize image
									}
									catch (FileNotFoundException e) {e.printStackTrace();}
						}
					datasource.close();			
				}
			
					
			private void createAlerts()
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("Please Enter All Fields to Input the New Word")
					  .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					     public void onClick(DialogInterface dialog, int id) {
						           dialog.cancel();
						           }
						       });
						alert_emptyfield = builder.create();
		
				}
	
	}