package com.lifeaide.Speech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/*
 * THIS CLASS HAS THREE BASIC QUERIES AND WRITE DATA
 * THIS CLASS ENTERS EITHER A NEW SPACE WORD OR FOLDER INTO THE APPROPRIATE 
 * SPACE TABLE INORDER TO PROVIDE CONSISTENCY
 * 
 * */


public class pagebuilderDB {
	private String TAG;
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;

	public pagebuilderDB(Context context)
		{
			dbHelper = new MySQLiteHelper(context);
		}
	public void open() throws SQLException
		{
			database = dbHelper.getWritableDatabase();	
		}
	public void close()
		{
			dbHelper.close();
		}
	
	//THIS IS A QUERY THAT ENTERS A NEW SPACE INTO THE DATABASE	
	public void createnewSpace(String spacename) 
			{
		
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.SPACE_NAME, spacename);	
		database.insert(MySQLiteHelper.SPACES_TABLE, null,values);
			
		Cursor cc =database.rawQuery("SELECT last_insert_rowid()", null);
		cc.moveToFirst();
		String lastid = cc.getString(0);
		Log.d("pagebuilder", lastid);
	
			for(int i =1; i< 19; i++)	
				{
				ContentValues values2 = new ContentValues();
				values2.put(MySQLiteHelper.SPACE_ID, lastid);
				values2.put(MySQLiteHelper.POSITION, i);
				values2.put(MySQLiteHelper.ICON_TYPE, "w");
				values2.put(MySQLiteHelper.ID_VALUE, 508);				
				database.insert(MySQLiteHelper.SPACES_INFO_TABLE, null,values2);
			
				}
			}	
			
			
			
	//THIS IS A QUERY THAT ENTERS A WORD INTO THE DATABASE		
	public void enternewword(int currentspacenumber, int wid, int currentpos) 
		{
		
		String Table = 	MySQLiteHelper.SPACES_INFO_TABLE;
		String sid = MySQLiteHelper.SPACE_ID;
		String pos =	MySQLiteHelper.POSITION;
		String type = MySQLiteHelper.ICON_TYPE;
		String id = MySQLiteHelper.ID_VALUE;
		

			try{
					open();
					String Query = "UPDATE "+ Table +" SET " + id +" = "+ wid +" , " + type + " = 'w' "+
							"WHERE "+sid+" = "+currentspacenumber+" and "+ pos + " = " + currentpos;
					
					
					Log.d("pgbuild", Query); 
					database.execSQL(Query);
					database.close();
				}catch(Exception e){}

			}
	
	//THIS IS A QUERY THAT ENTERS A FOLDER INTO THE DATABASE
	public void enternewfolder(int currentspacenumber, int fid, int currentpos) {

		String Table = 	MySQLiteHelper.SPACES_INFO_TABLE;
		String sid = MySQLiteHelper.SPACE_ID;
		String pos =	MySQLiteHelper.POSITION;
		String type = MySQLiteHelper.ICON_TYPE;
		String id = MySQLiteHelper.ID_VALUE;
		

			try{
					open();
					String Query = "UPDATE "+ Table +" SET " + id +" = "+ fid +" , " + type + " = 'f' "+
							" WHERE "+sid+" = "+currentspacenumber+" and "+ pos + " = " + currentpos;
					
					
					Log.d("pgbuild", Query); 
					database.execSQL(Query);
					database.close();
				}catch(Exception e){}

			}
		
	}
	
		
	
	

