package com.lifeaide.Speech;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.lifeaide.Speech.CreateWord.MyOnItemSelectedListener;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;
/*
 THIS CLASS ALLOWS THE USER TO INSERT NEW WORDS AND SPACES AND CREATE A UNIQUE SET UP FOR THEIR ENVIORMENT 
 
 * */



public class PageBuilder extends Activity implements OnGesturePerformedListener{
	
	//FIELD VARIALES
	private Resource_Manager datasource;
	private GridView gridview; 
	TextToSpeech tts;
	private ArrayList<String> SpeechBar = new ArrayList<String>();
	private Bitmap[] GridPictureArray;
	private GestureLibrary mLibrary;	
	private int NumberofSPaces;
	private TextView textview;
	private AlertDialog alert_emptyfield;
	pagebuilderDB PGBUILD;
	private AlertDialog add_item_allert;
	private AlertDialog alert_new_space;
	DataBaseCommands datasource_spin = new DataBaseCommands(this);
	private int currentspacenumber=6;
	private int currentpos;
	private int wid;
	private int savetemppos;
	private int fid;
	
	
	public void onCreate(Bundle savedInstanceState) 
		{    
			super.onCreate(savedInstanceState);
		    setContentView(R.layout.pagebuilder);
		    gridview = (GridView) findViewById(R.id.gridview);
		    textview =(TextView) findViewById(R.id.TVspace_name);
		    
		    //Load Gestures EXAMPLE SWIPE LEFT RIGHT HOME
			   	GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
		        gestures.addOnGesturePerformedListener(this);
			    mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		        if (!mLibrary.load()){finish();}

					    //are the main components of Grid
					    datasource = new Resource_Manager(this);
						datasource.open();
						NumberofSPaces =datasource.GetNumberofSpaces();
						PGBUILD = new pagebuilderDB(this);
						
						
						setGRIDVIEW_and_TITLE();//UPDATES THE GRID AND THE TITLE ON THE PAGE
								//main components of gallery
								gridview.setOnItemClickListener(new OnItemClickListener() 
								   	{
										//CLICK LISTNER FOR THE GRID
										public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
											{	        	
								        	//Update gallery view
									        	position= position+1;//offset counting from zero 
									        	Toast.makeText(PageBuilder.this, "" + (position), Toast.LENGTH_SHORT).show();								        			  
											}});
				
		}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.pagebuildsettings, menu);
		return true;
	}
	
	//THIS IS THE GESTURE LISTENER IF A GESTURE IS PERFORMED THEN IT RECOGNIZED AND METHOD IS ENABLED
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
		if (predictions.size() > 0) {					// We want at least one prediction
		Prediction prediction = predictions.get(0);			
			if (prediction.score > 1.0) {				// We want at least some confidence in the result
				
				String action = predictions.get(0).name;
					//Gesture Actions
						if ("swipe right".equals(action)) //SWIPE RIGHT ACTION
							{
								if(currentspacenumber < NumberofSPaces)
									{
									currentspacenumber+=1;
									setGRIDVIEW_and_TITLE();
									}
									Toast.makeText(this, "Swipe Right", Toast.LENGTH_SHORT).show(); 
									
							} 
						else if ("swipe left".equals(action)) //SWIPE LEFT ACTION
							{
								if(currentspacenumber > 1 && currentspacenumber<= NumberofSPaces)
									{
										currentspacenumber-=1;
										setGRIDVIEW_and_TITLE();
									}
									Toast.makeText(this, "Swipe Left", Toast.LENGTH_SHORT).show();
							} 
						else if ("home".equals(action)) //HOME ACTION
							{
								currentspacenumber=6;
								setGRIDVIEW_and_TITLE();
								Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
							}
		      }
		}
	}
	
	private void setGRIDVIEW_and_TITLE()
	{
	if(currentspacenumber == 7) //THIS IS FOR THE FAVORITES
		{
			gridview.setAdapter(new GridAdapter(this,datasource.getfavoritespics(),datasource.getfavoriteswords()));
			textview.setText("Favorites");
		}else
			{
			String[] GRIDWORDS = datasource.getwordList(currentspacenumber); //GETS ALL THE WORDS FOR A GRID
			GridPictureArray = datasource.getImagefromSpace(currentspacenumber); //GETS ALL PICTURES IN A GRID
			if(GRIDWORDS.length == 18) // TO HANDLE ERRORS OF SPACES WITH LESS THAN 18 ELEMENTS
				{
				gridview.setAdapter(new GridAdapter(this,GridPictureArray,GRIDWORDS)); //SETS THE GRID WITH WORDS AND PICS
				textview.setText(datasource.GetSpaceName(currentspacenumber));	//SETS THE TITLE OF THE PAGE
				}
			else
				{
				gridview.setAdapter(null);
				textview.setText(datasource.GetSpaceName(currentspacenumber));
				}
			}
	}
	
	//THIS IS THE OPTION MENU IT HAS TWO OPTIONS ONE TO CREATE A NEW SPACE ONE TO ADD A DIFFRENT WORD
	public boolean onOptionsItemSelected(MenuItem item) 
		{
			createAlerts();
			switch(item.getItemId())
			{
			case R.id.Create:
				alert_new_space.show(); //DISPLAY ALERT TO ENTER SPACE
				break;
			case R.id.add:
				add_item_allert.show(); //DISPLAY WEATHER WORD OR FOLDER YOU DESIRE TO INSERT
				break;
			}
			return false;
		}
	@Override
	protected void onPause()
		{
			super.onPause();
			if(tts != null){
				tts.stop();
				tts.shutdown();			
			}
			datasource.close();
			PGBUILD.close();
			finish();
		}
	protected void onStop()
		{
		super.onStop();
		datasource.close();
		PGBUILD.close();
		finish();
		}
	protected void onResume() 
		{
			// TODO Auto-generated method stub
			super.onResume();
			datasource.open();
			NumberofSPaces =datasource.GetNumberofSpaces(); //not sure if needed yet
		}
	
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
					
						
						AlertDialog.Builder Build2 = new AlertDialog.Builder(this);
						Build2.setMessage("Enter Word or Folder Item").setPositiveButton("Words", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	   spin_dialog_wordsfolderspin();
						           }
								}) 
						       .setNegativeButton("Folder", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	   spin_dialog_folders();
						           }
					  
						       }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						                dialog.cancel();
						           }});
						add_item_allert = Build2.create();
						
					//------------------------------------------
						AlertDialog.Builder newspacebuilder = new AlertDialog.Builder(this);
						 newspacebuilder.setTitle("Add Space");
						 newspacebuilder.setMessage("Please enter a name for your new space");
						 final EditText input = new EditText(this); 
						 newspacebuilder.setView(input);
						 newspacebuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								String value = input.getText().toString();
								PGBUILD.open();
								PGBUILD.createnewSpace(value);
								PGBUILD.close();
					
							}
						});
						 newspacebuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							 public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();	 
							 }
						 });
						 alert_new_space = newspacebuilder.create();
					//--------------------------
	
	
	}
	
	 //tHIS SELECTS WEATHER TO CHOOSE A WORD OR TO CHOOSE A FOLDER
	private void spin_dialog_wordsfolderspin(){
		final Dialog spin_dialog = new Dialog(this);
		
		spin_dialog.setContentView(R.layout.spinner);
		Spinner spin = (Spinner)spin_dialog.findViewById(R.id.Spinner01);
		Button okbutton = (Button) spin_dialog.findViewById(R.id.button1);
		Button cancelbutton = (Button) spin_dialog.findViewById(R.id.button2);
		spin_dialog.setTitle("Select Folder of Words");
		
		
					datasource_spin.open();
			  		List<Folders> folder_info = datasource_spin.getAll_FOLDERS();
				  	String[] foldersitems = new String[folder_info.size()];
			  			for(int i = 0; i < folder_info.size(); i++) 
						  	    {
						  	       Folders x=folder_info.get(i); 
						  	       String Foldername= x.getComment();
						  	        foldersitems[i]=Foldername;
						  	    }datasource_spin.close();

		 	  
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(PageBuilder.this,android.R.layout.simple_spinner_item, foldersitems);
					spin.setAdapter(adapter);
					spin.setOnItemSelectedListener(new MyOnItemSelectedListener4());
			
	         okbutton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	spin_dialog_select_words();
	            	spin_dialog.cancel();
	             }
	         });
	        cancelbutton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	              spin_dialog.cancel(); 
	             }
	         });
			
			
			spin_dialog.show();
			
	}
	
	//THIS IS A SPINNER OFF ALL THE FOLDER TO CHOOSE FROM 
	private void spin_dialog_folders(){
		final Dialog spin_dialog = new Dialog(this);
		
		spin_dialog.setContentView(R.layout.spinner);
		Spinner spin = (Spinner)spin_dialog.findViewById(R.id.Spinner01);
		Button okbutton = (Button) spin_dialog.findViewById(R.id.button1);
		Button cancelbutton = (Button) spin_dialog.findViewById(R.id.button2);
			
		spin_dialog.setTitle("Select Folder to insert");
		
		
		datasource_spin.open();
  		List<Folders> folder_info = datasource_spin.getAll_FOLDERS();
	  	String[] foldersitems = new String[folder_info.size()];
  			for(int i = 0; i < folder_info.size(); i++) 
			  	    {
			  	       Folders x=folder_info.get(i); 
			  	       String Foldername= x.getComment();
			  	        foldersitems[i]=Foldername;
			  	    }datasource_spin.close();

		 	  
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(PageBuilder.this,android.R.layout.simple_spinner_item, foldersitems);
			spin.setAdapter(adapter);
			spin.setOnItemSelectedListener(new MyOnItemSelectedListener4());
			
	         okbutton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	               	fid = currentpos;
	              	spin_dialog.cancel();
	              	spin_dialog_select_folder_position();
	             }
	         });
	        cancelbutton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	              spin_dialog.cancel(); 
	             }
	         });
			
			
			spin_dialog.show();
	}
	
	
	
	// THIS IS A SPINNER WITH ALL THE WORDS IN THE SELECTED FOLDER 
	private void spin_dialog_select_words(){
		final Dialog spin_dialog = new Dialog(this);
		
		spin_dialog.setContentView(R.layout.spinner);
		Spinner spin = (Spinner)spin_dialog.findViewById(R.id.Spinner01);
		Button okbutton = (Button) spin_dialog.findViewById(R.id.button1);
		Button cancelbutton = (Button) spin_dialog.findViewById(R.id.button2);
			
		spin_dialog.setTitle("Select Folder to inser");
		
		
		datasource_spin.open();
  		savetemppos = currentpos;
		List<Words> word_info = datasource_spin.getAll_win_FOLDERS(currentpos); 
	  	String[] foldersitems = new String[word_info.size()];
  			for(int i = 0; i < word_info.size(); i++) 
			  	    {
			  	       Words x=word_info.get(i); 
			  	       String Foldername= x.getComment();
			  	        foldersitems[i]=Foldername;
			  	    }datasource_spin.close();

		 	  
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(PageBuilder.this,android.R.layout.simple_spinner_item, foldersitems);
			spin.setAdapter(adapter);
			spin.setOnItemSelectedListener(new MyOnItemSelectedListener4());
			
	         okbutton.setOnClickListener(new View.OnClickListener() {
	             

				public void onClick(View v) {
					datasource_spin.open();
					List<Words> word_info = datasource_spin.getAll_win_FOLDERS(savetemppos); 
					 Words x=word_info.get(currentpos-1); 
					 wid=(int)x.getId();
					 
					 Log.d("pgbuilder",""+wid+"   "+ x.getComment());
					 datasource_spin.close();
					 
	            	spin_dialog_select_position();
	            	spin_dialog.cancel();
	             }
	         });
	        cancelbutton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	              spin_dialog.cancel(); 
	             }
	         });
			
			
			spin_dialog.show();
	}
	
	
	//THIS IS THE SELECTION OF THE POSITION YOU WISH TO ENTER THE WORD AT
	private void spin_dialog_select_position(){
		final Dialog spin_dialog = new Dialog(this);
		spin_dialog.setContentView(R.layout.spinner);
		Spinner spin = (Spinner)spin_dialog.findViewById(R.id.Spinner01);
		Button okbutton = (Button) spin_dialog.findViewById(R.id.button1);
		Button cancelbutton = (Button) spin_dialog.findViewById(R.id.button2);
			
		spin_dialog.setTitle("Select Position");
		String[] foldersitems = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"};
			 	  
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(PageBuilder.this,android.R.layout.simple_spinner_item, foldersitems);
			spin.setAdapter(adapter);
			spin.setOnItemSelectedListener(new MyOnItemSelectedListener4());
			
	         okbutton.setOnClickListener(new View.OnClickListener() {
	             

				public void onClick(View v) {
	            	//int x = currentpos;
	            	Log.d("pgbuilder"," "+currentpos);
	            	PGBUILD.enternewword(currentspacenumber,wid,currentpos);
	            	 spin_dialog.cancel();
	             }
	         });
	        cancelbutton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	              spin_dialog.cancel(); 
	             }
	         });
			
			
			spin_dialog.show();
	}
	
	
	//ALERT FOR FOLDER SELECTION WITH SPINNER 
	private void spin_dialog_select_folder_position(){
		final Dialog spin_dialog = new Dialog(this);
		spin_dialog.setContentView(R.layout.spinner);
		Spinner spin = (Spinner)spin_dialog.findViewById(R.id.Spinner01);
		Button okbutton = (Button) spin_dialog.findViewById(R.id.button1);
		Button cancelbutton = (Button) spin_dialog.findViewById(R.id.button2);
			
		spin_dialog.setTitle("Select Position");
		String[] foldersitems = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"};
			 	  
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(PageBuilder.this,android.R.layout.simple_spinner_item, foldersitems);
			spin.setAdapter(adapter);
			spin.setOnItemSelectedListener(new MyOnItemSelectedListener4());
			
	         okbutton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
	            	//int x = currentpos;
	            	Log.d("pgbuilder"," "+currentpos);
	            	PGBUILD.enternewfolder(currentspacenumber,fid,currentpos);
	            	 spin_dialog.cancel();
	             }
	         });
	        cancelbutton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	              spin_dialog.cancel(); 
	             }
	         });
			
			
			spin_dialog.show();
	}
	

//THIS UPDATES THE FIELD VARIABLE OF POSITION 
public class MyOnItemSelectedListener4 implements OnItemSelectedListener {
		
	    
		public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) 
	    		{
	    			currentpos=pos+1; //to make the folder equal to 
	    		}
	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    }
	}
	
	
	
	
	}
	
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

