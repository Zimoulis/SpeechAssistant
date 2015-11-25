package com.lifeaide.Speech;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseCommands {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	
	//String array for values to select all values in a Table
	private String[] allColumns_in_FOLDERS = { MySQLiteHelper.FOLDER_ID, MySQLiteHelper.FOLDER_NAME };
	private String[] allColumns_in_WORDS = { MySQLiteHelper.WORD_ID, MySQLiteHelper.FOLDER_ID,MySQLiteHelper.WORD_NAME};
	private String[] allColumns_in_SPACE = { MySQLiteHelper.SPACE_ID,MySQLiteHelper.POSITION,MySQLiteHelper.ICON_TYPE,MySQLiteHelper.ID_VALUE};
	private String TAG;

	
	
	public DataBaseCommands(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	
			//creates a folder in database when given a string value
			public void createFolder(String foldername_text) 
				{
				ContentValues values = new ContentValues();
				values.put(MySQLiteHelper.FOLDER_NAME, foldername_text);
				database.insert(MySQLiteHelper.FOLDERS_TABLE, null,	values);	
				}
			//creates word in the database from a string a and folder id
			public void createWord (String wordname_text, int folder_id) 
				{
					ContentValues values = new ContentValues();
						values.put(MySQLiteHelper.FOLDER_ID, folder_id);
						values.put(MySQLiteHelper.WORD_NAME, wordname_text);			
					database.insert(MySQLiteHelper.WORDS_TABLE, null,values);
				}
				
			//this returns the primary key of the last inserted piece of data
			public String getidOfCreatedword()
					{
					Cursor cc =database.rawQuery("SELECT last_insert_rowid()", null);
					cc.moveToFirst();
					String lastid = cc.getString(0);
					return lastid;
					}
			
			
			
			
			//creates a space name in database
			public void createSPACE(String spacename_text) 
			{
				ContentValues values = new ContentValues();
					values.put(MySQLiteHelper.SPACE_NAME, spacename_text);
				database.insert(MySQLiteHelper.SPACES_TABLE, null,values);
			}
			
			//creayes all formating variable for spaces in the database
			public void Enter_Position_Space(int s_id, int pos, String itype, int idval) 
			{
				ContentValues values = new ContentValues();
					values.put(MySQLiteHelper.SPACE_ID, s_id);
					values.put(MySQLiteHelper.POSITION, pos);
					values.put(MySQLiteHelper.ICON_TYPE, itype);
					values.put(MySQLiteHelper.ID_VALUE, idval);				
				database.insert(MySQLiteHelper.SPACES_INFO_TABLE, null,values);
			}
	//retrieves all the folder names and stores it in a list of ojects
	public List<Folders> getAll_FOLDERS() {
		List<Folders> folders = new ArrayList<Folders>();
		
		Cursor cursor = database.query(MySQLiteHelper.FOLDERS_TABLE,allColumns_in_FOLDERS, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Folders folder = cursorToFolders(cursor);
			Log.d(TAG, "cursor------=" + folder);
			
			folders.add(folder);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return folders;
	}
	//retrieves all the words names and stores it in a list of ojects
	public List<Words> getAll_WORDS() {
		List<Words> words = new ArrayList<Words>();
		
		Cursor cursor = database.query(MySQLiteHelper.WORDS_TABLE,allColumns_in_WORDS, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Words word = cursorToWords(cursor);
			Log.d(TAG, "cursor------=" + word);
			words.add(word);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return words;
	}
	
	//retrieves all the spaces and stores it in a list of ojects
	public List<Spaces> getAll_WORDSSPACEINFO() {
		List<Spaces> spaces = new ArrayList<Spaces>();
		
		Cursor cursor = database.query(MySQLiteHelper.SPACES_INFO_TABLE,allColumns_in_SPACE, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Spaces space = cursorToSpace(cursor);
			Log.d(TAG, "cursor------=" + space);
			spaces.add(space);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return spaces;
	}
	
	//Gets all the words in a selected folder 
	public List<Words> getAll_win_FOLDERS(int currentfolder) {
		List<Words> words = new ArrayList<Words>();
		
		String query = "SELECT * FROM "+ dbHelper.WORDS_TABLE+ " WHERE "+dbHelper.FOLDER_ID+ " = "+currentfolder;
		Cursor cursor = database.rawQuery(query, null);
		Log.d("pgbuild", query);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Words word = cursorToWords(cursor);
			Log.d(TAG, "cursor------=" + word);
			words.add(word);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return words;
	
	}
	
	
	//seter for the spaces classs
	private Spaces cursorToSpace(Cursor cursor) {
		Spaces space = new Spaces();
		space.set_SpaceId(cursor.getLong(0));
		space.set_position(cursor.getLong(1));
		space.set_iconvalue(cursor.getString(2));
		space.set_type_value(cursor.getLong(3));
		return space;
	}	
	//seters for folders classs
	private Folders cursorToFolders(Cursor cursor) {
		Folders folder = new Folders();
		folder.setId(cursor.getLong(0));
		folder.setComment(cursor.getString(1));
		return folder;
	}
	//setters for words class
	private Words cursorToWords(Cursor cursor) {
		Words word = new Words();
		word.setId(cursor.getLong(0));
		word.set_refrence_id((cursor.getLong(1)));
		word.setComment(cursor.getString(2));
		
		return word;
	}


	
	
	
	
	
	
	
	
	
}
