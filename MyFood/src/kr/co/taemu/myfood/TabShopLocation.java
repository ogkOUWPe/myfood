package kr.co.taemu.myfood;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class TabShopLocation extends MapActivity implements OnClickListener {

	private MapView mapView;
	private MapController mc;
	private Drawable drawable;
	private List<Overlay> list;
	private String lat;
	private String lon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.tab_shop_location);
	  setupMap();
	  Intent intent = getIntent();
	  lat = intent.getStringExtra("lat");
	  lon = intent.getStringExtra("lon");
	  drawCenterMarker();
	  findViewById(R.id.btnReturnToDetail).setOnClickListener(this);
	}
	
	private void setupMap() {
		mapView = (MapView) findViewById(R.id.mapLocation);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		mc = mapView.getController();
		drawable = this.getResources().getDrawable(R.drawable.arrow);
		list = mapView.getOverlays();
		mc.setZoom(15); // 1 ~ 21
	}
	
	public void drawCenterMarker() {
		if ( lat != null && lon != null ) {
			ShopOverlay shopOverlay = new ShopOverlay(drawable);
			int dlat = (int)(Double.parseDouble(lat) * 1E6);
			int dlon = (int)(Double.parseDouble(lon) * 1E6);
			GeoPoint p = new GeoPoint(dlat, dlon);
			OverlayItem overlayItem = new OverlayItem(p, "", "");
			shopOverlay.addOverlay(overlayItem);
			list.add(shopOverlay);
			mc.animateTo(p);
		}
	}

	@Override
  protected boolean isRouteDisplayed() {
	  return false;
  }

	@Override
  public void onClick(View v) {
		TabShopListActivityGroup tabShopListActivityGroup = (TabShopListActivityGroup)getParent();
		int id = v.getId();
		if ( id == R.id.btnReturnToDetail ) {
			tabShopListActivityGroup.startDetailView(true);
		}
  }
}
