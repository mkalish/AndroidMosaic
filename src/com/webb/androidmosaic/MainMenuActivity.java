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
				Intent cameraPreview = new Intent(getBaseContext(), CameraPreviewActivity.class);
				startActivityForResult(cameraPreview, 1234);
			}
		});
/*        //Temporary test code, TODO: remove
        PreprocessorConfiguration cfg = new PreprocessorConfiguration(5);
        Preprocessor preprocessor = PreprocessorFactory.getPreprocessor(cfg);
        List<AnalyzedImage> ais = preprocessor.analyze(some bitmaps);
        GeneratorConfiguration gcfg = new GeneratorConfiguration(ais, a target bitmap);
        Generator generator = GeneratorFactory.getGenerator(gcfg);
        NewStateListener nsl = new NewStateListener() {
        	public void handle(List<List<Bitmap>> state) {
        		System.out.println("New state received");
        	}
        };
        generator.registerNewStateListener(nsl);
        generator.start();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
