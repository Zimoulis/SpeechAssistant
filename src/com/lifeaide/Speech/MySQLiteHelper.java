package com.lifeaide.Speech;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
THIS CLASS IS THE DDL FOR SQLITE IT IS MAINLY PUBLIC STRINGS THE REASON FOR THIS IS IT MAKES IT EASY TO HAVE CONSISTENSY THROUGHOUT YOUR PROGRAM 
IF YOU WITH TO REFRACTOR THE STRING VALUES. fURTHERMORE ALL THE STRINGS ARE THE QUERYS NEED TO EXECUTE THE DATABASE

*/


public class MySQLiteHelper extends SQLiteOpenHelper {

	
	//VARIABLES FOR FOLDERS TABLE
	public static final String FOLDERS_TABLE = "FOLDERS";
	public static final String FOLDER_ID = "f_id";
	public static final String FOLDER_NAME = "folder_name";
	
	//VARIABLE FOR WORDS TABLE
	public static final String WORDS_TABLE = "WORDS";
	public static final String WORD_ID = "w_id";
	public static final String WORD_NAME = "word_name";
	public static final String WORD_COUNT_TABLE = "WORDS_COUNT";
	public static final String WORD_COUNT = "WORDS_COUNT";
	
	//VARIABLES FOR USER_TABLE
	public static final String SPACES_TABLE = "SPACES";
	public static final String SPACES_INFO_TABLE = "SPACES_INFO";
	public static final String SPACE_ID = "space_id";
	public static final String SPACE_NAME = "space_name";
	public static final String ICON_TYPE = "icon_type";
	public static final String ID_VALUE = "id_value";
	public static final String POSITION = "position";
	
	//DATABASE NAME AND SETTINGS
	private static final String DATABASE_NAME = "Talk_Aide.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statements
	private static final String DATABASE_CREATE_TABLE_WORDS = 
			"create table " + 	WORDS_TABLE + "( " +
				WORD_ID 	+ 	" integer primary key autoincrement, " 	+
				FOLDER_ID 	+ 	" integer default '1', " 				+
		        WORD_NAME 	+ 	" text not null unique, " 				+
		        	"FOREIGN KEY (F_ID) REFERENCES FOLDERS_TABLE (F_ID));";
	
	private static final String DATABASE_CREATE_TABLE_FOLDERS = 
			"create table " 	+  	FOLDERS_TABLE + "( " + 
				FOLDER_ID 		+ 	" integer primary key autoincrement, " +
				FOLDER_NAME 	+ 	" text not null unique);";	
	
	private static final String DATABASE_CREATE_TABLE_SPACES = 
			"create table " +   SPACES_TABLE + "( " 	+ 
				SPACE_ID 	+ 	" integer primary key autoincrement, " 	+
				SPACE_NAME 	+ 	" text not null unique);";
	
	private static final String DATABASE_CREATE_TABLE_SPACES_INFO = 
			"create table " +   SPACES_INFO_TABLE + "( " 												+ 
					SPACE_ID 	+ 	" integer, " 													+
					POSITION 	+ 	" integer 					CHECK(POSITION<20),"				+
					ICON_TYPE 	+ 	" text 		NOT NULL 		CHECK(ICON_TYPE IN('w','f')), " 	+ 
					ID_VALUE 	+ 	" integer 	NOT NULL, "											+
					"PRIMARY KEY (SPACE_ID, POSITION));";	
	
	private static final String DATABASE_CREATE_WORD_COUNT = 
			"create table " +   WORD_COUNT_TABLE + "( " 											+ 
					WORD_ID 	+ 	" integer primary key, " 										+
					WORD_COUNT 	+ 	" integer not null," 											+
					"FOREIGN KEY (W_ID) REFERENCES WORDS_COUNT (W_ID));";	
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		
		//execute the strings as queries create DDL
		database.execSQL("PRAGMA foreign_keys = ON;");
		database.execSQL(DATABASE_CREATE_TABLE_FOLDERS);
		database.execSQL(DATABASE_CREATE_TABLE_WORDS);
		database.execSQL(DATABASE_CREATE_TABLE_SPACES);
		database.execSQL(DATABASE_CREATE_TABLE_SPACES_INFO);
		database.execSQL(DATABASE_CREATE_WORD_COUNT);
		
		
		//Populate the database
		initialize_database insertDB = new initialize_database(database);
		insertDB.initializeDB();
		
	}



	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS" + WORDS_TABLE);
		onCreate(db);
	}

}

