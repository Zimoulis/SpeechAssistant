package com.lifeaide.Speech;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

/*This class has the activity for creating a new word in the database
it takes a picture and writes as jpg to the sd card and stores the string value of its name in the database and refrences it to it desired folder
*/
public class CreateWord extends Activity implements View.OnClickListener
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
					setContentView(R.layout.photo);
						//Declarations for XML
						iv = (ImageView)	findViewById(R.id.imageView1); 	iv.setOnClickListener(this);
						b  = (Button)		findViewById(R.id.bsetwall); 	b.setOnClickListener(this);
						inputb  = (Button)		findViewById(R.id.EnterWORDTODBBUTTONS); 	inputb.setOnClickListener(this);
						wordname = (EditText) findViewById(R.id.ET_WORDNAME);
						setspinnermethod();
						
						
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
					bmp = (Bitmap)extras.get("data"); 	// retrieve bitmap
					iv.setImageBitmap(bmp);				//display image
					
					}
				
				}
			
			/*This method selects all the folders in the database and provides a spinner(menu) to 
			in order to select the desired refrence number of the folder 
			*/
			private void setspinnermethod() {
				datasource.open();
				  		List<Folders> folder_info = datasource.getAll_FOLDERS(); // returns a list of objects with the folder names
					  		
				  			String[] foldersitems = new String[folder_info.size()]; //creates an array to convert list to array declaration to pass to spinner
				  			
				  			for(int i = 0; i < folder_info.size(); i++) 
							  	    {
							  	        Folders x=folder_info.get(i);  //pulls the object 
							  	        String Foldername= x.getComment(); //reads the pulled object
							  	        foldersitems[i]=Foldername; //stores sting in the array 
							  	    }
				
				  			Spinner spin = (Spinner) findViewById(R.id.spinner1);
				  			ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, foldersitems);//passes the array into a xml spinner
				  			spin.setAdapter(spinnerArrayAdapter);
				  			spin.setOnItemSelectedListener(new MyOnItemSelectedListener()); //sets listener for selected spinner item
				 datasource.close();
				
			}
			public class MyOnItemSelectedListener implements OnItemSelectedListener {
		
			    public void onItemSelected(AdapterView<?> parent,
			        View view, int pos, long id) 
			    		{
			    			currentfolder=pos+1; //to make the folder equal to this determines whar spinner is being used
			    		}
			    public void onNothingSelected(AdapterView<?> parent) {
			      // Do nothing.
			    }
			}
			
			//checks to make sure all fields are entered then submits to the database and writes to sd
			private void enter_data_to_db() 
				{
					createAlerts();
					String inputword= wordname.getText().toString();
				
		
					if(inputword.isEmpty() || bmp.equals(null)) // if a value is empty then alert to fill all fields
						{
						alert_emptyfield.show();
						}
		
					else
						{
						datasource.open();
						datasource.createWord(inputword, currentfolder); //enter word to the database and its folder 
						String id=datasource.getidOfCreatedword().toString();
							File exst = Environment.getExternalStorageDirectory();	
							String Path = exst.getPath() + "/.SpeechAssistant/Words/";
							File file = new File(Path,"w"+id+".jpg"); //it build path				 
								FileOutputStream out;
								try 
									{
										out = new FileOutputStream(file);
										bmp.compress(Bitmap.CompressFormat.PNG, 90, out); //resize image for minimal capcity
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