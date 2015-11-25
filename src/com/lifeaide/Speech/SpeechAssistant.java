package com.lifeaide.Speech;


import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class SpeechAssistant extends Activity implements OnGesturePerformedListener{
	
	private Resource_Manager datasource;
	private GridView gridview; 
	
	SpeechBarHandler SpeechClass = new SpeechBarHandler();
	TextToSpeech tts;
	
	private ArrayList<Bitmap> ImageList = new ArrayList<Bitmap>();
	private ArrayList<String> SpeechBar = new ArrayList<String>();
	
	private Bitmap[] GridPictureArray;
	private GestureLibrary mLibrary;	
	
	private int NumberofSPaces;
	private int currentspacenumber=7;
	private TextView textview;
	private boolean  InFolder = false; 
	private String CurrentFolderID = ""; //watch when changing folders no reset implemented
	private AlertDialog alert_password;
	private AlertDialog alert_wrong_pass;
	protected String PasswordValue ="";
	
	public void onCreate(Bundle savedInstanceState) 
		{    
			super.onCreate(savedInstanceState);
		    setContentView(R.layout.gui_gridview);
		    gridview = (GridView) findViewById(R.id.gridview);
		    textview =(TextView) findViewById(R.id.TVspace_name);
		    
		    		    
		    
		    //Load Gestures
			    GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
		        gestures.addOnGesturePerformedListener(this);
			    mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		        if (!mLibrary.load()){finish();}
	    
		        		
					    //are the main components of Grid
					    datasource = new Resource_Manager(this);
						datasource.open();
						NumberofSPaces =datasource.GetNumberofSpaces();
						setGRIDVIEW_and_TITLE();
						SpeechClass.startTTS(); 
						
								//main components of gallery
								gridview.setOnItemClickListener(new OnItemClickListener() 
								   	{
										public void onItemClick(AdapterView<?> parent, View v, int position, long id) {	        	
								        	//Update gallery view
								        	position= position+1;//offset counting from zero 
								        	Toast.makeText(SpeechAssistant.this, "" + (position), Toast.LENGTH_SHORT).show();
								        	
								        	
								        	if(InFolder == true) // IF THE PROGRAM IS IN THE FOLDER INVOKE METHODS THAT UPDATE FOLDER INFO 
							        			{
								           			String wid = datasource.getwordidfrom_folder(CurrentFolderID, position);								        		
								        			upadateGalleryfromFolder(datasource.wordimage(wid));
								        			SpeechBar.add(datasource.getwordfromid(wid));
								        			datasource.updatewordwount(wid);
							        			}								        	
								        	if(InFolder == false && currentspacenumber != 7) //IF THE PROGRAM IS NOT IN THE FOLDER INVOKE METHODS TO UPDATE SPACE METHODS 
								        			{
								        			String type =datasource.retieve_type(currentspacenumber, position); 
								        			if(type.equals("f"))	//IF DATABASE RETURNS AN F RETRIEVE FOLDER 
									           			{				           			
									           				
								        					String value = datasource.retieve_value(currentspacenumber, position);
								        					CurrentFolderID =value;
								        					setGRIDVIEW_and_TITLEforFolder(datasource.retieve_folderitems(value));				           	
									           			} 
								        			else if(type.equals("w")) //IF DATABASE RETURNS A W RETRIEVE WORD
									           			{
										           			upadateGallery(position-1);
										           			datasource.retieve_word(currentspacenumber, position);
										                 	SpeechBar.add(datasource.retieve_word(currentspacenumber, position));
										                 	//Log.d("Update Check", datasource.getwidfromspace(currentspacenumber, position));
										                 	datasource.updatewordwount(datasource.getwidfromspace(currentspacenumber, position));  
									           			}								        			
								        			  }
										     if(currentspacenumber == 7)//IF IN FOLDER AND ON SPACE 7 UPDATE THE PROGRAM TO FAVORITES METHODS 
										         	{
										       		int x = Integer.parseInt(datasource.getfavword_id(position));
										       		upadateGalleryfromFavorits(x);
										           	SpeechBar.add(datasource.getfavword(position));
										        	}	
								       }});
				
		}

	
	
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}







	// THIS CHECKS THE GESTURES AND IF THEY ARE PERFORMED THE PERFORM AND ACTION SUCH BRING TO HOME PAGE  SWIPE LEFT OR SWIPE RIGHT 
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
		if (predictions.size() > 0) {					// We want at least one prediction
		Prediction prediction = predictions.get(0);			
			if (prediction.score > 1.0) {				// We want at least some confidence in the result
				
				String action = predictions.get(0).name;
					//Gesture Actions
						if ("swipe right".equals(action)) 		//SWIPE RIGHT UPDATES FIELD VAIABLE TO ADD ON AND SETS YOU OUT SIDE OF FOLDER 
							{
								if(currentspacenumber < NumberofSPaces)
									{
									currentspacenumber+=1;
									setGRIDVIEW_and_TITLE();
									InFolder= false; // exit folder reset
									}
									Toast.makeText(this, "Swipe Right", Toast.LENGTH_SHORT).show(); 
									
							} 
						else if ("swipe left".equals(action)) //SWIPE LEFT UPDATES FIELD VAIABLE TO MINUS 1 AND SETS YOU OUT SIDE OF FOLDER 
							{
								if(currentspacenumber > 1 && currentspacenumber<= NumberofSPaces)
									{
										currentspacenumber-=1;
										setGRIDVIEW_and_TITLE();
										InFolder= false; // exit folder reset
									}
									Toast.makeText(this, "Swipe Left", Toast.LENGTH_SHORT).show();
							} 
						else if ("home".equals(action)) //BRINGS YOU TO THE DEFAULT HOME PAGE WICH IS FAVORITES 
							{
								currentspacenumber=7;
								setGRIDVIEW_and_TITLE();
								Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
								InFolder= false; // exit folder reset
							}
						else if ("refresh".equals(action))  // THIS SQUIGGLE SPEECH THE SENTANNCE BY INVOKING TEXT TO SPEECH 
							{
								Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
								SpeechClass.SpeaktheSentance();
							}
						else if ("backspace".equals(action)) //REMOVES LAST WORD FROM THE SPEECH LIST AND THE BITMAP LIST
						{
							Toast.makeText(this, "backspace", Toast.LENGTH_SHORT).show();
							SpeechClass.backspace();
							upadateGallery_Backspace();
						}
				
				
				
		      }
		}
	}
	//UPDATES THE GRID ADAPTER XML BY MAKING CALLS TO THE DATABASE AND REVIEVING THE NESSARY PICTURES AND WORDS FOR PAGES 
	private void setGRIDVIEW_and_TITLE()
		{
		if(currentspacenumber == 7)// THIS IS AN EXEPTIONS FOR FAVORITS 
			{
				gridview.setAdapter(new GridAdapter(this,datasource.getfavoritespics(),datasource.getfavoriteswords()));
				textview.setText("Favorites");
			}else //FOR USER CREATED PAGES 
				{
				String[] GRIDWORDS = datasource.getwordList(currentspacenumber);
				GridPictureArray = datasource.getImagefromSpace(currentspacenumber);
				if(GRIDWORDS.length == 18)
					{
					gridview.setAdapter(new GridAdapter(this,GridPictureArray,GRIDWORDS)); //SETS THE GRID VIEW TO THE ARRAYS INPUTES 
					textview.setText(datasource.GetSpaceName(currentspacenumber));	//SETS THE TILTE OF PAGE TO THE SPACE TITLE 
					}
				else
					{
					gridview.setAdapter(null);
					textview.setText(datasource.GetSpaceName(currentspacenumber));
					}
				
//				
				}
		}
	//UPDATES THE GRID ADAPTER XML BY MAKING CALLS TO THE DATABASE AND REVIEVING THE NESSARY PICTURES AND WORDS FOR FOLDER
	private void setGRIDVIEW_and_TITLEforFolder(Bitmap[] bitmaps)
		{
		String[] GRIDWORDS = datasource.getwordListforFolder(CurrentFolderID);
		gridview.setAdapter(new GridAdapter(this,bitmaps, GRIDWORDS));
		textview.setText("Folder");
		InFolder=true;	//sets so program know wheaher in folder or space
		}
	//UPDATES THE GALLERY OF THE SLECTED ITEM IN GRIDVIEW 
	protected void upadateGallery(int position) {
		ImageList.add(GridPictureArray[position]); // only one image needs dynamic
		Bitmap[] SpeechStringImage = ImageList.toArray(new Bitmap[ImageList.size()]);		
		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setAdapter(new GalleryAdapter(this,SpeechStringImage));
		gallery.setSelection(SpeechStringImage.length-1);
	}
	//UPDATES FROM SELECTED ITEM IN A FOLDER 
	private void upadateGalleryfromFolder(Bitmap bitmap) {
		ImageList.add(bitmap); 
		Bitmap[] SpeechStringImage = ImageList.toArray(new Bitmap[ImageList.size()]);		
		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setAdapter(new GalleryAdapter(this,SpeechStringImage));
		gallery.setSelection(SpeechStringImage.length-1);
	}
	//UPDATES GALLEREY FROM THE FAVORITES PAGE (RECENTLY SELECETED WORDS)
	protected void upadateGalleryfromFavorits(int wid) 
		{
			String id =Integer.toString(wid);
			datasource.getwordfromid(id);
			 
			ImageList.add(datasource.getpicfromwid(id)); // only one image needs dynamic
			Bitmap[] SpeechStringImage = ImageList.toArray(new Bitmap[ImageList.size()]);		
			Gallery gallery = (Gallery) findViewById(R.id.gallery);
			gallery.setAdapter(new GalleryAdapter(this,SpeechStringImage));
			gallery.setSelection(SpeechStringImage.length-1);
		}
	
	//REMOVES LAST SELECT WORD ITEM IN THE LIST 
	protected void upadateGallery_Backspace() 
		{
		if(ImageList.size() > 0)
			{
				ImageList.remove(ImageList.size()-1); // only one image needs dynamic
				Bitmap[] SpeechStringImage = ImageList.toArray(new Bitmap[ImageList.size()]);		
				Gallery gallery = (Gallery) findViewById(R.id.gallery);
				gallery.setAdapter(new GalleryAdapter(this,SpeechStringImage));
				gallery.setSelection(SpeechStringImage.length-1);
			}
		}
	//REMOVES ALL WORD ITEMS FROM LIST 
	protected void CLEAR_GalleryandSpeech() 
	{
	if(ImageList.size() > 0)
		{
			ImageList.removeAll(ImageList);
			Bitmap[] SpeechStringImage = ImageList.toArray(new Bitmap[ImageList.size()]);		
			Gallery gallery = (Gallery) findViewById(R.id.gallery);
			gallery.setAdapter(new GalleryAdapter(this,SpeechStringImage));
			gallery.setSelection(SpeechStringImage.length-1);
			SpeechClass.clearall();
		}
	}
	
	
	


	// Options for the settings button
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
		{
			switch(item.getItemId())
			{
			case R.id.speller:
				startActivity(new Intent(this, TextVoice.class));
				break;
			case R.id.backspace:
				SpeechClass.backspace();
				upadateGallery_Backspace();
				break;
			case R.id.speak:
				SpeechClass.SpeaktheSentance();
				break;
			case R.id.settings:
				createAlerts();
				alert_password.show();
				break;
				
			case R.id.clear:
				CLEAR_GalleryandSpeech();
				break;
			}
			
			
			return false;
		}

//THIS BUILDS THE NESSASRRY ALLERTS SUCH FOR A PROMTEDED PASSWORD	
private void createAlerts() {
	 final Intent myIntent = new Intent(this, ModeratorMenu.class);	
	 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	 final String x = prefs.getString("password", "IF THERE IS NO VALUE");
	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle("Moderator Settings");
		 builder.setMessage("Please enter your password");
		 final EditText input = new EditText(this); 
		 builder.setView(input);
		 builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				String value = input.getText().toString();
				PasswordValue  = value;
				
				if(PasswordValue.equals("test")|| PasswordValue.equals(x)) //CHECKS THE PASSWORD IN USER PREFRENCES 
					{
						startActivity(myIntent);
					
					}
				else
					{
					alert_wrong_pass.show(); //ELSE IF WRONG PASSWORD ALERT THE USER IN NEW DIALOG 
					}

			}
		});
		 builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			 public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();	 
			 }
		 });
		 alert_password = builder.create();
		 
		 AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
		builder2.setMessage("The Password You Entered is invalid. Please Re-enter Your Password.")
		       .setCancelable(false)
		       .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		alert_wrong_pass = builder2.create(); 
		
	}

	
	//ON PAUSE AND ON RESUME MUST MAKE SURE THAT THE TEXT TO SPEECH IS CLOSED AND ACORDINGLY 
	@Override
	protected void onPause() {
		super.onPause();
		if(tts != null){
			tts.stop();
			tts.shutdown();			
		}
		datasource.close();	
		finish();// DESTROY ACTIVITY 
		}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		datasource.open();
		NumberofSPaces =datasource.GetNumberofSpaces(); //not sure if needed yet
	}
	
	
	
	
	//INNER CLASS THIS PROSCESS THE STRINGS BASED ON USER SPEECH AND GENERATES TEXT TO SPEECH
	private class SpeechBarHandler
		{			
			public void startTTS()//STATS TEST TO SPEECH ENGINE
						{
							tts = new TextToSpeech(SpeechAssistant.this,new TextToSpeech.OnInitListener() {
								
								public void onInit(int status) {
									if(status != TextToSpeech.ERROR){
										tts.setLanguage(Locale.US); //SET VOICE TO US
									}
									
								}
							});
						}
		
			public void SpeaktheSentance()//READS THE SENTANCE
					{	
						tts.speak(Build_Sentence(), TextToSpeech.QUEUE_FLUSH, null);	
					}
		
			public void backspace() //DELETES THE LAST WORD INPUTED INTO LIST
					{
					if (SpeechBar.size()>0){
						SpeechBar.remove(SpeechBar.size()-1);}
					}
			
			public void clearall() //CLEARS ALL THE WORDS IN LIS
			{
			if (SpeechBar.size()>0){
				SpeechBar.removeAll(SpeechBar);}
			}
			

			private String Build_Sentence() //RETURNS THE CONCATANETED SENTANCE
				{ 
				
				String Sentence="";
				   for(int i=0; i<=((SpeechBar.size())-1); i++)
				   		{
						   Sentence = Sentence + " " + SpeechBar.get(i).toString();
						   Log.d("Type Check", Sentence);
						   Log.d("Type Check", "HUH");
				   		}   

				   return Sentence;
				}
			
			
			
		
		}
	
	
}
