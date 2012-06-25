package kr.co.taemu.myfood;

import java.util.ArrayList;

import kr.co.taemu.myfood.ShopAdapter.ViewHolder;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TabShopView extends Activity implements OnClickListener, OnGesturePerformedListener {
	
	private String lat;
	private String lon;
	
	private GestureLibrary gestureLib;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_shop_view);
		
		Intent intent =  getIntent();
		String name = intent.getStringExtra("name");
		String tel = intent.getStringExtra("tel");
		String detail = intent.getStringExtra("detail");
		String imagepath = intent.getStringExtra("imagepath");
		lat = intent.getStringExtra("lat");
		lon = intent.getStringExtra("lon");
		
		TextView txtName  = (TextView)findViewById(R.id.txtShopName);
		TextView txtTel  = (TextView)findViewById(R.id.txtShopTel);
		TextView txtDetail  = (TextView)findViewById(R.id.txtShopDetail);
		TextView txtImagePath  = (TextView)findViewById(R.id.txtShopImagePath);
		
		txtName.setText(name);
		txtTel.setText(tel);
		txtDetail.setText(detail);
		txtImagePath.setText(imagepath+"\n");
		txtImagePath.append("lat: "+lat+"\n");
		txtImagePath.append("lon: "+lon+"\n");
		
		
		Bitmap bm = BitmapFactory.decodeFile(imagepath);
		((ImageView)findViewById(R.id.imageView1)).setImageBitmap(bm);
		
		findViewById(R.id.btnBackToList).setOnClickListener(this);
		findViewById(R.id.btnGoToMapView).setOnClickListener(this);
		
		GestureOverlayView gestureOverlayView = (GestureOverlayView)findViewById(R.id.gestureOverlay);
		gestureOverlayView.addOnGesturePerformedListener(this);
		gestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!gestureLib.load()) {
			finish();
		}
	}
	
	@Override
	protected void onDestroy() {
	  super.onDestroy();
	}

	@Override
  public void onClick(View v) {
		TabShopListActivityGroup tabShopListActivityGroup = (TabShopListActivityGroup)getParent();
		int id = v.getId();
		if ( id == R.id.btnBackToList) {
			tabShopListActivityGroup.startListView();
		} else if ( id == R.id.btnGoToMapView) {			
			Intent intent = new Intent(this,TabShopLocation.class);
			intent.putExtra("lat",lat);
			intent.putExtra("lon",lon);
			TabShopListActivityGroup parent = (TabShopListActivityGroup)getParent();
			parent.setLocationIntent(intent);
			parent.startLocationView();
		}
  }
	
	@Override
  public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
		for (Prediction prediction : predictions) {
			if (prediction.score > 1.0) {
				if ( prediction.name.equals("left")) {
					TabShopListActivityGroup tabShopListActivityGroup = (TabShopListActivityGroup)getParent();
					tabShopListActivityGroup.startListView();
				} else if ( prediction.name.equals("right")) {
					Intent intent = new Intent(this,TabShopLocation.class);
					intent.putExtra("lat",lat);
					intent.putExtra("lon",lon);
					TabShopListActivityGroup parent= (TabShopListActivityGroup)getParent();
					parent.setLocationIntent(intent);
					parent.startLocationView();
				}
			}
		}
  }
}
