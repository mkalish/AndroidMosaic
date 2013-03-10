package com.webb.androidmosaic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.util.List;

import com.webb.androidmosaic.generation.AnalyzedImage;
import com.webb.androidmosaic.generation.Configuration;
import com.webb.androidmosaic.generation.NewStateListener;
import com.webb.androidmosaic.generation.generator.Generator;
import com.webb.androidmosaic.generation.generator.GeneratorFactory;
import com.webb.androidmosaic.generation.preprocessor.Preprocessor;
import com.webb.androidmosaic.generation.preprocessor.PreprocessorFactory;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends Activity {
	
	Button viewMosaics;	
	Button makeAMosaic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        
        viewMosaics = (Button) this.findViewById(R.id.viewMosaics);
        makeAMosaic = (Button) this.findViewById(R.id.makeMosaic);
        
        viewMosaics.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Start he view mosaics activity
				
			}
		});
        
        makeAMosaic.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent takeAPhoto = new Intent(getBaseContext(), TakePhotoActivity.class);
				startActivity(takeAPhoto);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
