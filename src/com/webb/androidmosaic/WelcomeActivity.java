package com.webb.androidmosaic;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeActivity extends Activity {

	private Button login;
	@SuppressWarnings("unused")
	private EditText username;
	@SuppressWarnings("unused")
	private EditText pw;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.login = (Button) this.findViewById(R.id.login_button);
        this.username = (EditText) this.findViewById(R.id.username);
        this.pw = (EditText) this.findViewById(R.id.password);
        login.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent moveToMainMenu = new Intent(getBaseContext(), MainMenuActivity.class);
				startActivity(moveToMainMenu);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_welcome, menu);
        return true;
    }
}
