package com.webb.androidmosaic;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.webb.androidmosaic.generation.AnalyzedImage;
import com.webb.androidmosaic.generation.Configuration;
import com.webb.androidmosaic.generation.NewStateListener;
import com.webb.androidmosaic.generation.generator.Generator;
import com.webb.androidmosaic.generation.generator.GeneratorFactory;

public class TakePhotoActivity extends Activity {
	
	private static final int CAMERA_REQUEST = 1888;
	private ImageView imageView;
	private LinearLayout ll;
	private Bitmap image;
	private Generator generator;
	private Configuration cfg;
	@SuppressWarnings("unused")
	private List<AnalyzedImage> solution; 
	private String MosaicGeneratorTag = "Mosaic Generation";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageview);
		this.ll = (LinearLayout) this.findViewById(R.id.linearlayout1);
		this.imageView = (ImageView) this.findViewById(R.id.imageView1);
		Button photoButton = (Button) this.findViewById(R.id.button1);
		photoButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);				
			}
		});
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bitmap photo = null;
		if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			photo = (Bitmap) data.getExtras().get("data");
			imageView.setImageBitmap(photo);
			image = photo;
		}
		
		Button button = new Button(this);
		button.setText("Use this picture");
		button.setId(1);
		button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Log.i(MosaicGeneratorTag, "Now creating mosaic");
				createPhotoMosaic(image);
			}
		});
		ll.addView(button);
	}
	
	private  void createPhotoMosaic(Bitmap image) {
		generator = GeneratorFactory.getGenerator(cfg);
		generator.setTargetImage(image);
		NewStateListener generatorStateListener = new NewStateListener() {
			
			public void handle(List<AnalyzedImage> state) {
				Log.i(MosaicGeneratorTag, "New state generated");
				// TODO code for animating mosaic change
			}
		};
		generator.registerNewStateListener(generatorStateListener);
		Log.i(MosaicGeneratorTag, "Listener attached");
		generator.start();
		Log.i(MosaicGeneratorTag, "Generator has finished");
		if(generator.getSolutionTiles() != null) {
			solution = generator.getSolutionTiles();
		}
		//Code could follow here to add image to screen
		
		
	}

}
