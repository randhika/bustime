package com.yene.bustiming;

import com.yene.example.bustiming.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	 public final static String EXTRA_MESSAGE = "com.yene.BUSNUMBER";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void sendMessage (View view){
		Intent intent = new Intent(this, Direction.class);
	    EditText editText = (EditText) findViewById(R.id.busN);
	    String search_term = editText.getText().toString();
	    
	    if(search_term.length()<1 || search_term.length()>5){
	    Context context = getApplicationContext();
		CharSequence text = "Please Enter Bus Number";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	    }else{
	    	 intent.putExtra(EXTRA_MESSAGE, search_term);
	 	    startActivity(intent);
	    }
		
		
	   

		
	}

}
