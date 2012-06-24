package kr.co.taemu.myfood;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TabShopView extends Activity implements OnClickListener {
	
	private String lat;
	private String lon;
	
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
	}

	@Override
  public void onClick(View v) {
		TabShopListActivityGroup tabShopListActivityGroup = (TabShopListActivityGroup)getParent();
		int id = v.getId();
		if ( id == R.id.btnBackToList) {
			tabShopListActivityGroup.startListView();
		} else if ( id == R.id.btnGoToMapView) {
			Main m = (Main)tabShopListActivityGroup.getParent();
			m.mapCenterLat = lat;
			m.mapCenterLon = lon;
			m.getTabHost().setCurrentTab(1);
			tabShopListActivityGroup.startListView();
		}
  }
}
