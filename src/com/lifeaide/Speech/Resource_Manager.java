package com.lifeaide.Speech;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
/*
 * WHEATHER YOUR IN A FOLDER OR IF YOUR IN A SPACE OR IN FAVORITES THERE ARE DIFFRENT QUERIES NEED IN THE RETRIEVAL OF 
 * THE PICTURES AND WORDS IN THIS CLASS EACH METHOD ACCOUNTS FOR THESE DIFFRENT EXEPTIONS
 * ALL METHOD IN THIS CLASS DEAL WITH RETRIEVING DATA USING QUERIES OR READ i/O COMANDS AND RETURNING THE DATA
 * 
 * 
 * */

public class Resource_Manager 
	{

		private String TAG;
		private SQLiteDatabase database;
		private MySQLiteHelper dbHelper;
		
		List<Spaces> spaces = new ArrayList<Spaces>();
		private String[] allColumns_in_SPACE = { MySQLiteHelper.SPACE_ID,MySQLiteHelper.POSITION,MySQLiteHelper.ICON_TYPE,MySQLiteHelper.ID_VALUE};
		private String[] allColumns_in_SPACENAME = { MySQLiteHelper.SPACE_ID,MySQLiteHelper.SPACE_NAME};
		//Global PathNames for private method
		 File exst = Environment.getExternalStorageDirectory();
		 private String WordPath = exst.getPath()+"/.SpeechAssistant/Words/";
		 private String FolderPath = exst.getPath()+"/.SpeechAssistant/Folders/";
		
					//CONSTRUCTOR AND methods OPEN AND CLOSE DATABASE
					public Resource_Manager(Context context){
							dbHelper = new MySQLiteHelper(context);
						}
					public void open() throws SQLException{
							database = dbHelper.getWritableDatabase();
							get_spaces();	//this is here to only open once and to contain all space information
						}
					public void close(){
						dbHelper.close();
						}
		
		public String[] getwordListforFolder(String folder_id)
					{
						ArrayList<String> WordList = new ArrayList<String>();	
						String Query = "SELECT " + MySQLiteHelper.WORD_NAME + " FROM "+ MySQLiteHelper.WORDS_TABLE +" WHERE " + MySQLiteHelper.FOLDER_ID + " = " + folder_id;
						Cursor cursor = database.rawQuery(Query, null);
						
						cursor.moveToFirst();
						while (!cursor.isAfterLast()) 
							{
								Log.d("Type Check", "w "+ cursor.getString(0));
								WordList.add(cursor.getString(0));
								cursor.moveToNext();
							}
						String[] WordArray = WordList.toArray(new String[WordList.size()]);
						return WordArray;	
						
					}
		public String[] getwordList(int currentspacenumber) 
					{
					ArrayList<String> WordList = new ArrayList<String>();
				
						String icon_type = MySQLiteHelper.ICON_TYPE;
						String id_value = MySQLiteHelper.ID_VALUE;
						String Table = MySQLiteHelper.SPACES_INFO_TABLE; 
						String SpaceNum = MySQLiteHelper.SPACE_ID;
				
						String Query = "SELECT " + icon_type +" , "+id_value + " FROM "+ Table +" WHERE " + SpaceNum + " = " + currentspacenumber;
						Cursor cursor = database.rawQuery(Query, null);	
						
						cursor.moveToFirst();
						Log.d("Type Check", "space num " + currentspacenumber);
						while (!cursor.isAfterLast()) 
							{
							//Log.d("Type Check",cursor.getString(0));
							if(cursor.getString(1).equals("0"))// for null space values
								{
								WordList.add("  ");
								cursor.moveToNext();
								}
							if(cursor.getString(0).equals("w"))
								{
									String Queryw = "SELECT " + MySQLiteHelper.WORD_NAME + " FROM "+ MySQLiteHelper.WORDS_TABLE +" WHERE " + MySQLiteHelper.WORD_ID + " = " + cursor.getString(1);
									Cursor cursorw = database.rawQuery(Queryw, null);
									cursorw.moveToFirst();
									String WName = cursorw.getString(0);
									WordList.add(WName);		
									Log.d("Type Check", "w "+ WName);
								}
							if(cursor.getString(0).equals("f"))
								{
									String Queryf = "SELECT " + MySQLiteHelper.FOLDER_NAME + " FROM "+ MySQLiteHelper.FOLDERS_TABLE +" WHERE " + MySQLiteHelper.FOLDER_ID + " = " + cursor.getString(1);
									Cursor cursorf = database.rawQuery(Queryf, null);
									cursorf.moveToFirst();
									String FName = cursorf.getString(0);
									WordList.add(FName);
									Log.d("Type Check", "f " + FName);								
								}
							cursor.moveToNext();
						}
						
						String[] WordArray = WordList.toArray(new String[WordList.size()]);
						return WordArray;	
					}
		public Bitmap[] getImagefromSpace(int SpaceId)
				{
					Bitmap[] x = new Bitmap[18];
						
							for(int i=0; i<=17 ;i++)
								{
									x[i]=get_picture(SpaceId, i+1); //i might need plus 1
								}
					return x; //returns all pictures from space in Array
				}
		private Bitmap get_picture(int Spaceid,int position)
				{
					Bitmap bitmap = null;
					
			        try     
			        {
			            bitmap = BitmapFactory.decodeFile(get_picture_adress(Spaceid,position)); //enters adress and gets file into bitmap
			            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //compress bitmap
			        }
			        catch(Exception ex) {}
				  
				 return bitmap;
				 
				}
		//this gets the position of the picture from the SpaceID table 
		private String get_picture_adress(int SpaceNumber, int PostionNumber)
				{
					String posadress = "ERROR";
					int space = ((SpaceNumber-1)*18); //brings to the appropriate space //poistion fine tune
					int position =(PostionNumber-1);
					Spaces specificspace =spaces.get(space + position); 
					String y = specificspace.geticon_value();
					long z = specificspace.gettype_value();			
					
					String picturename =y+z+".jpg";
		  	        
						if(y.equals("w"))
							{
							posadress=WordPath+ picturename;
							}
						else if(y.equals("f"))
							{
							posadress=FolderPath+ picturename;
							}
						else
							Log.v(TAG, "BIG BIG ERROR DUDE!");
					return posadress;
				}
		// Pulls all Space Information From the DataBase Stores in Global Variable	
		private void get_spaces() 
				{
				
					Cursor cursor = database.query(MySQLiteHelper.SPACES_INFO_TABLE,allColumns_in_SPACE, null, null, null, null, null);
					cursor.moveToFirst();
					while (!cursor.isAfterLast()) {
						Spaces space = cursorToSpace(cursor);
						spaces.add(space);
						cursor.moveToNext();
					}
					// Make sure to close the cursor
					cursor.close();		
				}
		private Spaces cursorToSpace(Cursor cursor) {
			Spaces space = new Spaces();
			space.set_SpaceId(cursor.getLong(0));
			space.set_position(cursor.getLong(1));
			space.set_iconvalue(cursor.getString(2));
			space.set_type_value(cursor.getLong(3));
			return space;
		}
		//data info return	
		public int GetNumberofSpaces()
			{			
			Cursor d=database.query(MySQLiteHelper.SPACES_TABLE,allColumns_in_SPACENAME, null, null, null, null, null);
			int NUM_OF_SPACE=d.getCount();
			return NUM_OF_SPACE;
			}
		public String GetSpaceName(int currentspacenumber)
			{		
			String Table 	=  MySQLiteHelper.SPACES_TABLE;
			String SpaceID 	=  MySQLiteHelper.SPACE_ID;
			String Query = "SELECT * FROM "+ Table +" WHERE " + SpaceID + " = " + currentspacenumber;
			Cursor cursor = database.rawQuery(Query, null);
					String x = null;	
					while (cursor.moveToNext()) 
						{
						 x=cursor.getString(1); // 0 is the first column 
						}
					//Log.d("Space Query", x);
					cursor.close();
			return x;
			}
		//returns whether item is folder or word
		public String retieve_type(int currentspacenumber, int position)
			{
			
			String Table 	= 	MySQLiteHelper.SPACES_INFO_TABLE;String SpaceId	=	MySQLiteHelper.SPACE_ID;String dbpos =  	MySQLiteHelper.POSITION;
			String Query = "SELECT * FROM "+ Table +" WHERE " + SpaceId + " = " + currentspacenumber + " AND " + dbpos+ " = "+ (position); //position comes in as zero for spot 1
			Cursor cursor = database.rawQuery(Query, null);
			String x = null;
					while (cursor.moveToNext()) 
						{
						 x=cursor.getString(2); 				 
						}
					cursor.close();
					return x;
			}
		//Reteieve values and pictures for the folder grids
		public String retieve_word(int currentspacenumber, int position)
		{
		
			String Table 	= 	MySQLiteHelper.SPACES_INFO_TABLE;
			String SpaceId	=	MySQLiteHelper.SPACE_ID;String dbpos =  	MySQLiteHelper.POSITION;
			String Query = "SELECT * FROM "+ Table +" WHERE " + SpaceId + " = " + currentspacenumber + " AND " + dbpos+ " = "+ position; 
			Cursor cursor = database.rawQuery(Query, null);
			String x = null;
					while (cursor.moveToNext()) 
						{
						 x=cursor.getString(3); //value
						}
			
			Table = MySQLiteHelper.WORDS_TABLE;
			String WordID = MySQLiteHelper.WORD_ID;
			Query = "SELECT * FROM "+ Table +" WHERE " + WordID + " = " + x;
			cursor = database.rawQuery(Query, null);		
				while (cursor.moveToNext()) 
						{
							x=cursor.getString(2); //value
						}
			cursor.close();
			return x;	
		}
		//RETURNS WORD FROM THE PAGE AND POSITION NUMBER 
		public String retieve_value(int currentspacenumber, int position)
			{
			
			String Table 	= 	MySQLiteHelper.SPACES_INFO_TABLE;String SpaceId	=	MySQLiteHelper.SPACE_ID;String dbpos =  	MySQLiteHelper.POSITION;
			String Query = "SELECT * FROM "+ Table +" WHERE " + SpaceId + " = " + currentspacenumber + " AND " + dbpos+ " = "+ position; 
			Cursor cursor = database.rawQuery(Query, null);
			String x = null;
					while (cursor.moveToNext()) 
						{
						 x=cursor.getString(3); 				 
						}
					cursor.close();
					return x;
			}
		
		//RETURNS THE BITMAPS OF ALL ITEMS IN THE FOLDER IT CALLS THE OTHER PRIVATE METHODS TO DIVIDE TASK INTO SUB TASKS
		public Bitmap[] retieve_folderitems(String folder_num)
			{
			 List<String> pathlist =new ArrayList<String>();
			
			String Table = MySQLiteHelper.WORDS_TABLE;
			String FolderID = MySQLiteHelper.FOLDER_ID;
			String Query = "SELECT * FROM "+ Table +" WHERE " + FolderID + " = " + folder_num;
			Cursor cursor = database.rawQuery(Query, null);		
			
				String x = null;
				String address= null;
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) 
					{
						x=cursor.getString(0);
						address = WordPath+"w"+x+".jpg";
						pathlist.add(address);
						cursor.moveToNext();
					}
			cursor.close();
			return getWordImagesfromFolder(pathlist);
			}
		
		//FOR A SET OF PATHS RETUN THE ARRAY OF IMAGES
		private Bitmap[] getWordImagesfromFolder(List<String> pathlist)
			{
				Bitmap[] x = new Bitmap[pathlist.size()];
				Bitmap bitmap = null;
				
				for(int i=0; i<=(pathlist.size()-1) ;i++)
					{	
				        try     
					        {	
				        		Log.d("Type Check", pathlist.get(i).toString());
				        		bitmap = BitmapFactory.decodeFile(pathlist.get(i).toString()); //enters adress and gets file into bitmap
					            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
					            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //compress bitmap
					            x[i] = bitmap;
					        }
				        catch(Exception ex) {}
						}
						
						
				return x; //returns all pictures from space in Array
			}
		
		public String getwordidfrom_folder(String folder_id, int position)
				{
					String Table = MySQLiteHelper.WORDS_TABLE;
					String FolderID = MySQLiteHelper.FOLDER_ID;
					String Query = "SELECT * FROM "+ Table +" WHERE " + FolderID + " = " + folder_id;
					Cursor cursor = database.rawQuery(Query, null);	
						
						position =position-1;
						String wid = "";
						cursor.moveToFirst();
						while (!cursor.isAfterLast()) 
						{
							if(position == cursor.getPosition())
								{
								 wid =cursor.getString(0);
								 Log.d("Type Check", wid);
								 break;				 
								}
							cursor.moveToNext();
							
						}
						 return wid;
						
				}
		//FOLDER IMAGES IT GETS FROM THE GIVE WORD ID USED IN FOLDERS
		public Bitmap wordimage(String wid) {
			String path= WordPath+"w"+wid+".jpg";
			Log.d("Type Check", path);
			Bitmap bitmap = null;
			try     
	        {
	            bitmap = BitmapFactory.decodeFile(path); //enters adress and gets file into bitmap
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //compress bitmap
	        }
	        catch(Exception ex) {}
		  
		 return bitmap;
			
		}
		//RETURNS THE WORD NAME FROM A WORD ID USED PRIMARILY IN FOLDER RETRIEVAL
		public String getwordfromid(String wid) {
			String Table = MySQLiteHelper.WORDS_TABLE;
			String WordName = MySQLiteHelper.WORD_NAME;
			String WordID = MySQLiteHelper.WORD_ID;
			
			String Query = "SELECT " + WordName + " FROM "+ Table +" WHERE " + WordID + " = " + wid;
			Cursor cursor = database.rawQuery(Query, null);	
			cursor.moveToFirst();
			WordName = cursor.getString(0);
			return WordName;
			
			
		}
		//WHEN THIS MEHTHOD IS ACTIVATED IT INCREMENTS THE WORD COUNT BY ONE SO EACH WORD IS ABLE TO QUANTIFY HOW MANY TIMES ITS BEEN CLICKED
		public void updatewordwount(String wid) {
					
			String Table = 	MySQLiteHelper.WORD_COUNT_TABLE;
			String WordID = MySQLiteHelper.WORD_ID;
			String WCount =	MySQLiteHelper.WORD_COUNT;
			String Query = "SELECT " + WCount + " FROM "+ Table +" WHERE " + WordID + " = " + wid;
			Cursor cursor = database.rawQuery(Query, null);	
			try //update 
				{		
					cursor.moveToFirst();
					int kz = cursor.getInt(0);
					kz = kz+1;
					Log.d("Update Check", " "+kz);
					String Query2 = "UPDATE "+ Table +" SET " + WCount+" = "+ kz +" WHERE w_id = "+wid+";";
					database.execSQL(Query2);
				} 
				catch (Exception exp) //insert
					{
					ContentValues values=new ContentValues();
				    values.put( WordID,wid);
				    values.put( WCount,1);
					database.insert(Table, null, values);
					}
	
				}
		
		//DETERMINES WHAT THE WID IS GIVEN A SPACE AND POSITION THIS IS USED WHEN NOT IN FOLDER
		public String getwidfromspace(int currentspacenumber, int position) {
			String Table = 	MySQLiteHelper.SPACES_INFO_TABLE;
			String idval = 	MySQLiteHelper.ID_VALUE;
			String sid = 	MySQLiteHelper.SPACE_ID;
			String pos = 	MySQLiteHelper.POSITION;
			
			String Query = "SELECT "+idval+" FROM " + Table + " Where "+ sid +" = " + currentspacenumber + " and "+ pos +" = " + position;
			Cursor cursor = database.rawQuery(Query, null);	
			cursor.moveToFirst();
			return cursor.getString(0);
		
		}
		
		//RETURNS THE ARRAY OF THE TOP 18 MOST FREQUETLY CLICKED IMAGES
		public Bitmap[] getfavoritespics() 
			{
			String Table = 	MySQLiteHelper.WORD_COUNT_TABLE;
			String WCount =	MySQLiteHelper.WORD_COUNT;
			// SELECT * FROM WORDS_COUNT ORDER BY WORDS_COUNT DESC LIMIT 2;
			String Query = "SELECT * FROM "+ Table +" ORDER BY "+ WCount +" DESC LIMIT 18";
			Cursor cursor = database.rawQuery(Query, null);	
			cursor.moveToFirst();
			
			
			
			List<String> pathlist =new ArrayList<String>();
			String x = null;String address= null;
								
								while (!cursor.isAfterLast()) 
									{
										x=cursor.getString(0);
										address = WordPath+"w"+x+".jpg";
										pathlist.add(address);
										cursor.moveToNext();
									}
							cursor.close();
							return getWordImagesfromFolder(pathlist);
			
			
			}
		
		//RETURNS AN ARRAY OF THE 18  MOST FREQUEENTLY SELECTES WORDS
		public String[] getfavoriteswords() 
		{
		String Query = "SELECT WORDS.word_name FROM WORDS JOIN WORDS_COUNT ON WORDS.w_id = WORDS_COUNT.w_id ORDER BY WORDS_COUNT DESC LIMIT 18";
		Cursor cursor = database.rawQuery(Query, null);	
		cursor.moveToFirst();
		ArrayList<String> WordList = new ArrayList<String>();
					while (!cursor.isAfterLast()) 
						{
							WordList.add(cursor.getString(0));
							cursor.moveToNext();
						}
				String[] WordArray = WordList.toArray(new String[WordList.size()]);
			return WordArray;		
		}
		//RETURNS THE PICTURE OF A SPECIFIED WORD ID
		public Bitmap getpicfromwid(String wid)
			{
			Bitmap bitmap = null;
			String path = WordPath+"w"+wid+".jpg";
			
			
	        try     
	        {
	            bitmap = BitmapFactory.decodeFile(path); //enters adress and gets file into bitmap
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //compress bitmap
	        }
	        catch(Exception ex) {}
		  
	        return bitmap;
			
			}
		//ON THE FAVORITES MENU THIS DETERMINES WHICH POSITION IS EQAUL TO WHAT FAVORITE OR CONSTENTLY CLICKED WORD
		public String getfavword(int position) 
		{
		String Query = "SELECT WORDS.word_name FROM WORDS JOIN WORDS_COUNT ON WORDS.w_id = WORDS_COUNT.w_id ORDER BY WORDS_COUNT DESC LIMIT 18";
		Cursor cursor = database.rawQuery(Query, null);	
		
		cursor.moveToPosition(position-1);
		String x=cursor.getString(0);
							
				
			return x;		
		}
		//ON THE FAVORITES MENU THIS DETERMINES WHICH POSITION IS EQAUL TO WHAT FAVORITE OR CONSTENTLY CLICKED WORD IT GETS THE wid FOR THE CORESPONDING BITMAP
		public String getfavword_id(int position) 
		{
		String Query = "SELECT WORDS.w_id FROM WORDS JOIN WORDS_COUNT ON WORDS.w_id = WORDS_COUNT.w_id ORDER BY WORDS_COUNT DESC LIMIT 18";
		Cursor cursor = database.rawQuery(Query, null);	
		
		cursor.moveToPosition(position-1);
		String x=cursor.getString(0);
							
				
			return x;		
		}
		

		
}

