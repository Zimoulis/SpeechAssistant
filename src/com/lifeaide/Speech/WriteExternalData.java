package com.lifeaide.Speech;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
/*
 * THIS CLASS ON INSTALL WRITES WACH PICTURE IN THE DRAWABLE FOLDER TO THE APPROPRIATE PLACE IN THE 
 * EXTERNAL MEMORY IT IS ALSO THREADED WITH A LOADING BAR THUS ALLOWING THE USER TO 
 * TRACK ITS PROGRESS
 * 
 * 
 * */

public class WriteExternalData extends Activity {
	
	
	private static String TAG;
	boolean canW,canR;
	private String RootPath,WordPath,FolderPath = null;
	private File Rootdir,Folderdir,Worddir,file = null;
	private String state;	
	private Integer[] drawableArray;
	private ProgressDialog progressDialog;
	private int value = 0;
	private Handler handler;
	
	protected void onCreate(Bundle savedInstanceState) 
		{
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
				
			progressDialog = new ProgressDialog(WriteExternalData.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(false);
			progressDialog.setMax(528);
			progressDialog.show();
			progressDialog.setProgress(value);	
			
			//THREAD CREATE SO THE LOADING BAR WILL DISPLAY
			handler = new Handler();
			Runnable runnable = new Runnable() {
				public void run() {
						checkState();									//checks sd is read and writable
						getDrawabletoArray();							//stores drawable images into a global array
						createExternalDir();							//creates hidden directory for speechassistant
							try 
								{
									//THE METHODS CALLED TO PERFORM FUNCTION OF THIS CLASS
									intilize_folders_pic();					//populates folder pics
									intilize_words_pic();					//poulates  word pics								
									Intent openStartingPoint = new Intent("com.lifeaide.Speech.Alerts"); //LAUNCH THE AN ALLERT FOR THE SUCSESSFUL INSTALATION 
									startActivity(openStartingPoint);
									finish();//KILL ACTIVITY ONCE ALL IS COMPLETED
								}
									catch (Exception e) 
										{
											e.printStackTrace();
										}
						
						
						handler.post(new Runnable() {public void run() {
							progressDialog.setProgress(value);//LOADING BAR
							}
						});
				}
			};
			new Thread(runnable).start();
		
		
					
				
			
		}
	

	//THIS METHOD CHECKS TO SEE IF YOU ARE ABLE TO WRITE OUT TO THE EXTERNAL DATABASE
	private void checkState() {
		state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			canW = canR = true;
		}else if(state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
			canW = false; canR = true;
		}else{
			canW = canR = false;
		}}
	
	//THIS METHOD TAKES THE POINTERS IN THE R AND ASSIGNS THEM INTO THE AN ARRAY
	private void getDrawabletoArray()
	{
		final R.drawable drawableResources = new R.drawable();
		final Class<R.drawable> c = R.drawable.class;
		final Field[] fields = c.getDeclaredFields();
		int max = fields.length;
		Integer[] dArray = new Integer[max]; 
		
		
		for (int i =0; i < max; i++) 
			{
		    try 
			    {
			        dArray[i] = fields[i].getInt(drawableResources);
			    
			        Log.v(TAG, "darray=" + i + " :"+ dArray[i]);
			    }
		    	catch (Exception e) 
			    	{
			        continue;
			    	}
		    /* make use of resourceId for accessing Drawables here */
			}
		
		drawableArray=dArray;
		
	} 
	
	//THIS METHOD CREATES THE DIRECTORY IN WHICH THE AND SUBDIRECTORY WHERE THE DATA IS WRITEN TO
	private void createExternalDir() 
	{
		String path[] = {".SpeechAssistant","Folders","Words"};
		File exst = Environment.getExternalStorageDirectory();
		//Creates the Path names Strings
		String exstPath = exst.getPath();
	    
				RootPath   = exstPath +  "/" + path[0];
			    FolderPath = exstPath +  "/" + path[0] + "/" + path[1];
			    WordPath   = exstPath +  "/" + path[0] + "/" + path[2];
	    //Create file which creates folders in SD CARD
		    Rootdir = new File(RootPath); 		Rootdir.mkdirs();
		    Folderdir = new File(FolderPath);	Folderdir.mkdirs();
		    Worddir = new File(WordPath);		Worddir.mkdirs();	    
	}
	
	//LOOPS THROUGH THE POINTERS AND WRITES THE DRAWABLE DATA TO IN FOLDERS SUBDIRECTORY WITH CORESSPONIGN WORD ID AS ITS NAME
	private void intilize_folders_pic() throws Exception
		{
			for(int i=1; i<=21; i++)
				{
					String filename = "f"+Integer.toString(i); 
					file = new File(Folderdir,filename + ".jpg");
					writeTO_External(file,(i-1));					 
					// the (i-1) it pics from array[0] but labeles it index 1
					value++;
					progressDialog.setProgress(value);				
				}
		}
	
	//LOOPS THROUGH THE POINTERS AND WRITES THE DRAWABLE DATA TO IN WORDS SUBDIRECTORY WITH CORESSPONIGN WORD ID AS ITS NAME
	private void intilize_words_pic() throws Exception
	{	
		for(int i=1; i<=507; i++){
			String filename = "w"+Integer.toString(i); 
			file = new File(Worddir,filename + ".jpg");
			writeTO_External(file,(i+20));	
			value++;
			progressDialog.setProgress(value);
		}	
	}
	//CALLED BY THE OTHER METHOD THIS STRICTLY JUST WRITES THE FILE NAME AND THE DATA ON THE CARD THE OTHERS PROVIDE THE ONFRMATION 
	private void writeTO_External(File file, int i) throws Exception
		{
		checkState();
		if(canW == canR== true)
			{
				Folderdir.mkdirs();	
				try 
					{	
					InputStream is = getResources().openRawResource(drawableArray[i]);
					OutputStream os = new FileOutputStream(file);
					byte[] data = new byte[is.available()];
					is.read(data);
					os.write(data);
					is.close();
					os.close();				
					}
				  catch (FileNotFoundException e) {e.printStackTrace();}
				  catch (IOException e) {e.printStackTrace();}
			}
		}


}


