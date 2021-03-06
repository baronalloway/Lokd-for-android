package com.baronalloway.lokdforandroid;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	String password;
	EditText passwordView;
	Button goButton;
	EncryptionKey userKey;
	List<WalletItem> myItems = new ArrayList<WalletItem>();
	ImageButton goButton1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		passwordView  = (EditText)findViewById(R.id.passwordtext);
		goButton1 = (ImageButton)findViewById(R.id.gobutton1);
		
		
		//check for wallet file and then do stuff from here
		File f = new File(getApplicationContext().getFilesDir(), "walletItem.wal");
		if(!(f.exists()))
		{
			//GO TO WELCOME POPUPS
			Intent goWelcome = new Intent(MainActivity.this, Welcome.class);
			startActivity(goWelcome);
		}
		
		goButton1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
			password = passwordView.getText().toString();
			passwordView.setText("");
			
			try {
				userKey = new EncryptionKey(password);
				
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "PLEASE INPUT A PASSWORD", 5).show();
			} 
			try
			{
			userKey.get(userKey.getKey(), userKey.getCipher(), userKey.getDCipher(), getApplicationContext());
			
			Intent walletIntent = new Intent(MainActivity.this, ViewWallet.class);
			walletIntent.putExtra("password", password);
			Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim,R.anim.anim2).toBundle();
			startActivity(walletIntent, bndlanimation);
			
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "Error! WRONG PASSWORD", 7).show();
			}
			
			
			}});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	@Override
	public void onBackPressed() {
		Toast.makeText(getApplicationContext(), "WHOOPS...KEEP MOVING FORWARD", 5).show();
	}
	
	

}
