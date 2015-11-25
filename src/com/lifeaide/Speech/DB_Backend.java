package com.lifeaide.Speech;


import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
/*
 This class is unimportant to the overall project but I use it to check values in my database
 such as primary keys in case of errors
 This class is unavalible to the user. 
 If you whan to use this class you must add it's intent to the Moderator Menu 
 its already declared in the Manifest
 * */
public class DB_Backend extends Activity implements OnClickListener {
	private DataBaseCommands datasource;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		datasource = new DataBaseCommands(this);
		datasource.open();
		
	
		
		
		
		findViewById(R.id.db_button).setOnClickListener(this);	
		findViewById(R.id.enter_table_button).setOnClickListener(this);
		findViewById(R.id.enter_table_words).setOnClickListener(this);
		findViewById(R.id.button_show_words).setOnClickListener(this);
		findViewById(R.id.button_show_spaces).setOnClickListener(this);

	}


	public void onClick(View v) {
		
		
		
		switch (v.getId()) {
	      	case R.id.enter_table_button: 
	      		final EditText edittext = (EditText) findViewById(R.id.TablenameText);
	      		String custom =edittext.getText().toString();
	      		datasource.createFolder(custom);
	      		
	      		 
	    	  break;
	      	
	      	case R.id.db_button: 
		      
	      			final EditText edittext2 = (EditText) findViewById(R.id.words_display);
		      		String b= "Index   Folder Name";
		      		//String z= "Index   Folder Name";
		      		
		      		List<Folders> folder_info = datasource.getAll_FOLDERS();
		      		//List<Words> word_info = datasource.getAll_WORDS();
		      		
		      		
		      		 for(int i = 0; i < folder_info.size(); i++) 
		      	    {
		      	        Folders x=folder_info.get(i); 
		      	        b= b + "\n" + x.getId() +"  \t\t\t"+ x.getComment();
		      	        
		      	        
		      	        edittext2.setText(b);
		      	    }
	
		      		break;
	      	

	      	case R.id.enter_table_words: 		
	      		final EditText wordsname = (EditText) findViewById(R.id.Word_nameText);
	      		final EditText wordref = (EditText) findViewById(R.id.WOrds_folderrefrence);
			      		String x = wordsname.getText().toString();
			      		String k = wordref.getText().toString();
			      		int y = Integer.parseInt(k);
		      		datasource.createWord(x, y);
	      		break;
		      		
		      		
	      	case R.id.button_show_words:
	      		final EditText editwordtext = (EditText) findViewById(R.id.words_display);
		      	
	      		List<Words> word_info = datasource.getAll_WORDS();
	      		
	      		String j = "id Folder# Word\n";
	      		 for(int i = 0; i < word_info.size(); i++) 
		      	    {
		      	        Words kz= word_info.get(i); 
		      	        j= j + "\n" + kz.getId() +"  \t\t\t"+ kz.get_refrence_id()+"  \t\t\t"+ kz.getComment();
		      	        
		      	        
		      	      editwordtext.setText(j);
		      	    }
		      	break;
		      		
	      	case R.id.button_show_spaces:		  	
	      		final EditText editwordtext3 = (EditText) findViewById(R.id.words_display);
	      		List<Spaces> space_info = datasource.getAll_WORDSSPACEINFO();
	      		
	      		String sinfo = "Space Table info";
	      		
		      		for(int i = 0; i < space_info.size(); i++) 
		      	    {
		      	        Spaces kz= space_info.get(i); 
		      	        sinfo= sinfo + "\n" + kz.getSpaceId() +"  \t\t\t"+ kz.getPosition()+"  \t\t\t"+ kz.geticon_value() + "  \t\t\t" +kz.gettype_value();
		      	        
		      	        
		      	        
		      	    }	
		      		editwordtext3.setText(sinfo);
		      		
//	      		datasource.createSPACE("hellhfafdsfdfds");
//	      		datasource.Enter_Position_Space(5, 11, "w", 3);    		
	      		break;
		      		
	      }
	}
	

	
	
	
	
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

}
	