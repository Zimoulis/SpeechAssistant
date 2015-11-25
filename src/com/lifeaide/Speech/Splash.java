package com.lifeaide.Speech;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class Splash extends Activity{
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

	
		ImageView img = (ImageView)findViewById(R.id.testimageView1);
		img.setBackgroundResource(R.anim.rocket);

		MyAnimationRoutine mar = new MyAnimationRoutine();
		MyAnimationRoutine2 mar2 = new MyAnimationRoutine2();

		Timer t = new Timer(false);
		t.schedule(mar, 300);
		Timer t2 = new Timer(false);
		t2.schedule(mar2, 20000);

		}

		class MyAnimationRoutine extends TimerTask
		{
				MyAnimationRoutine(){}
		
				public void run()
					{
					ImageView img = (ImageView)findViewById(R.id.testimageView1);
					// Get the background, which has been compiled to an AnimationDrawable object.
					AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
					// Start the animation (looped playback by default).
					frameAnimation.start();
					}
		}

		class MyAnimationRoutine2 extends TimerTask
			{
				MyAnimationRoutine2(){}
	
				public void run()
				{
					ImageView img = (ImageView)findViewById(R.id.testimageView1);
					// Get the background, which has been compiled to an AnimationDrawable object.
					AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
					// stop the animation (looped playback by default).
					frameAnimation.stop();
				}
			}

//		Thread timer = new Thread(){
//				public void run(){
//					try{
//						sleep(5000); //5 seconds
//						//sleep(0);
//					}catch(InterruptedException e){
//							e.printStackTrace();
//					}finally{
//					Intent openStartingPoint = new Intent("com.lifeaide.Speech.Menu");
//					startActivity(openStartingPoint);
//					}
//					
//					
//				}
//			};
//			timer.start();
		
	

	@Override
	protected void onPause() {
		super.onPause();
		finish(); 
	}
	
	
	
	
	}