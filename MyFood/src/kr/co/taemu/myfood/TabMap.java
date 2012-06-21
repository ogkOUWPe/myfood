package kr.co.taemu.myfood;

import java.util.HashMap;
import java.util.List;

import kr.co.taemu.myfood.shopcmd.BulkInsertShop;
import kr.co.taemu.myfood.shopcmd.ResetShop;
import kr.co.taemu.myfood.shopcmd.ShopCommand;
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

public class TabMap extends MapActivity implements OnClickListener{
	Drawable drawable;
	ShopOverlay shopOverlay;
	List<Overlay> list;
	
	ShopDAO dao;
	HashMap<Integer,ShopCommand> cmds;
	StringBuilder sbminLat;
	StringBuilder sbminLon;
	StringBuilder sbmaxLat;
	StringBuilder sbmaxLon;
	
	MapView mapView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_map);
		
		setupMap();
		findViewById(R.id.btnGenerateData).setOnClickListener(this);
		findViewById(R.id.btnResetData).setOnClickListener(this);
		
		sbminLat = new StringBuilder();
		sbminLon = new StringBuilder();
		sbmaxLat = new StringBuilder();
		sbmaxLon = new StringBuilder();
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		dao = new ShopDAO(this);
		dao.open();
		
		cmds = new HashMap<Integer,ShopCommand>();
		cmds.put(R.id.btnGenerateData, new BulkInsertShop(dao,  sbminLat, sbminLon, sbmaxLat, sbmaxLon, 100));
		cmds.put(R.id.btnResetData, new ResetShop(dao));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	private void setupMap() {
		mapView = (MapView)findViewById(R.id.mapView);
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

	public void onClick(View v) {
		updatePositionParameter();
		cmds.get(v.getId()).exec();
	}
	
	private void updatePositionParameter() {
		GeoPoint center = mapView.getMapCenter();
		int centerLat = center.getLatitudeE6();
		int centerLon = center.getLongitudeE6();
		
		int halfLatSpan = mapView.getLatitudeSpan()/2;
		int halfLonSpan = mapView.getLongitudeSpan()/2;
		
		Double dminLat = (centerLat-halfLatSpan)/ 1E6;
		Double dminLon = (centerLon-halfLonSpan)/ 1E6;
		Double dmaxLat = (centerLat+halfLatSpan)/ 1E6;
		Double dmaxLon = (centerLon+halfLonSpan)/ 1E6;

		sbminLat.delete(0, sbminLat.length());
		sbminLon.delete(0, sbminLon.length());
		sbmaxLat.delete(0, sbmaxLat.length());
		sbmaxLon.delete(0, sbmaxLon.length());
		
		sbminLat.append(dminLat.toString());
		sbminLon.append(dminLon.toString());
		sbmaxLat.append(dmaxLat.toString());
		sbmaxLon.append(dmaxLon.toString());
	}
}
