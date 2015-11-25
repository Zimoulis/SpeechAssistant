package com.lifeaide.Speech;

import java.util.Locale;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
/*
 * THIS CLASS JUST INVOKES THE SPEECH API OF ANDROID THAT PROCESSES SPEECH TO TEXT
 * THE PROGRAM RENDERS ANY STRING TYPED IN AND THEN VERBALLY COMMUNICATES IT 
 * 
 * */

public class TextVoice extends Activity implements OnClickListener{

	
	TextToSpeech tts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textvoice);
		
		
		findViewById(R.id.Custom_tts).setOnClickListener(this);
		
		tts = new TextToSpeech(TextVoice.this,new TextToSpeech.OnInitListener() {
			
			public void onInit(int status) {
				if(status != TextToSpeech.ERROR){
					tts.setLanguage(Locale.US);
				}
				
			}
		});
		
	}
	@Override
	protected void onPause() {	
		if(tts != null){
			tts.stop();
			tts.shutdown();			
		}
		super.onPause();
	}

	  public void onClick(View v) {
	    
	      switch (v.getId()) {
	      	case R.id.Custom_tts: 
	      			final EditText edittext = (EditText) findViewById(R.id.Custom_Text);
	      			String custom =edittext.getText().toString();
	      			tts.speak(custom, TextToSpeech.QUEUE_FLUSH, null);
	    	  break;
	      	
	      }
	  
	  }
}
	
