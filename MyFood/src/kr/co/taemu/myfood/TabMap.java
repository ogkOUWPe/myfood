package kr.co.taemu.myfood;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class TabMap extends MapActivity {
	Drawable drawable;
	ShopOverlay shopOverlay;
	List<Overlay> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_map);
		MapView mapView = (MapView)findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		
		GeoPoint p = new GeoPoint(37519789, 126940308);
		MapController mc = mapView.getController();
		mc.animateTo(p);
		mc.setZoom(15); // 1 ~ 21
		list = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.arrow);
		shopOverlay = new ShopOverlay(drawable);
		OverlayItem overlayItem = new OverlayItem(p, "", "");
		shopOverlay.addOverlay(overlayItem);
		list.add(shopOverlay);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
