package kr.co.taemu.myfood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.taemu.myfood.shopcmd.BulkInsertShop;
import kr.co.taemu.myfood.shopcmd.ResetShop;
import kr.co.taemu.myfood.shopcmd.SearchShopByRange;
import kr.co.taemu.myfood.shopcmd.ShopCommand;
import kr.co.taemu.myfood.shopcmd.ShopCommand.OnCompleteCallback;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class TabShopMap extends MapActivity implements OnClickListener, OnCompleteCallback {
	Drawable drawable;

	List<Overlay> list;
	ShopOverlay shopOverlay;
	MyLocationOverlay myLocationOverlay;

	ShopDAO dao;
	HashMap<Integer, ShopCommand> cmds;
	StringBuilder sbminLat;
	StringBuilder sbminLon;
	StringBuilder sbmaxLat;
	StringBuilder sbmaxLon;

	MapView mapView;
	MapController mc;

	int clat;
	int clon;
	
	MyLocationListener listener;
	LocationManager lm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_shop_map);
		setupMap();

		findViewById(R.id.btnMyLocation).setOnClickListener(this);
		findViewById(R.id.btnSearchPlaces).setOnClickListener(this);
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

		cmds = new HashMap<Integer, ShopCommand>();
		SearchShopByRange ssbr = new SearchShopByRange(dao, mapView, sbminLat, sbminLon, sbmaxLat, sbmaxLon, 10);
		ssbr.setOnComplete(this);

		cmds.put(R.id.btnSearchPlaces, ssbr);
		cmds.put(R.id.btnGenerateData, new BulkInsertShop(dao, sbminLat, sbminLon, sbmaxLat, sbmaxLon, 1000));
		cmds.put(R.id.btnResetData, new ResetShop(dao));

		setupLocation();
	}

	@Override
	protected void onPause() {
		super.onPause();
		lm.removeUpdates(listener);
		myLocationOverlay.disableMyLocation();
		dao.close();
	}

	public void drawCenterMarker() {
		;
		ShopOverlay shopOverlay = new ShopOverlay(drawable);
		GeoPoint p = new GeoPoint(clat, clon);
		OverlayItem overlayItem = new OverlayItem(p, "", "");
		shopOverlay.addOverlay(overlayItem);
		list.add(shopOverlay);
		mc.animateTo(p);
		mapView.invalidate();
	}

	private void setupMap() {
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		mc = mapView.getController();
		drawable = this.getResources().getDrawable(R.drawable.arrow);
		list = mapView.getOverlays();
		mc.setZoom(15); // 1 ~ 21
		myLocationOverlay = new MyLocationOverlay(this,mapView);
		list.add(myLocationOverlay);
	}

	public void onClick(View v) {
		updatePositionParameter();
		if ( v.getId() == R.id.btnMyLocation) {
			GeoPoint p = new GeoPoint(clat, clon);
			mc.animateTo(p);
		} else {
			cmds.get(v.getId()).exec();
		}
	}

	private void updatePositionParameter() {
		GeoPoint center = mapView.getMapCenter();
		int centerLat = center.getLatitudeE6();
		int centerLon = center.getLongitudeE6();

		int halfLatSpan = mapView.getLatitudeSpan() / 2;
		int halfLonSpan = mapView.getLongitudeSpan() / 2;

		Double dminLat = (centerLat - halfLatSpan) / 1E6;
		Double dminLon = (centerLon - halfLonSpan) / 1E6;
		Double dmaxLat = (centerLat + halfLatSpan) / 1E6;
		Double dmaxLon = (centerLon + halfLonSpan) / 1E6;

		sbminLat.delete(0, sbminLat.length());
		sbminLon.delete(0, sbminLon.length());
		sbmaxLat.delete(0, sbmaxLat.length());
		sbmaxLon.delete(0, sbmaxLon.length());

		sbminLat.append(dminLat.toString());
		sbminLon.append(dminLon.toString());
		sbmaxLat.append(dmaxLat.toString());
		sbmaxLon.append(dmaxLon.toString());
	}

	public void onComplete(ArrayList<ShopDTO> shops) {
		if ( shopOverlay != null ) {
			list.remove(shopOverlay);
		}
		shopOverlay = new ShopOverlay(drawable);
		if (shops != null) {
			for (ShopDTO shop : shops) {
				int iLat = (int) (Double.parseDouble(shop.getLat()) * 1E6);
				int iLon = (int) (Double.parseDouble(shop.getLon()) * 1E6);
				GeoPoint p = new GeoPoint(iLat, iLon);
				OverlayItem overlayItem = new OverlayItem(p, "", "");
				shopOverlay.addOverlay(overlayItem);
			}
			list.add(shopOverlay);
		}
		mapView.invalidate();
	}

	private void setupLocation() {
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String best = lm.getBestProvider(criteria, true);
		Log.e("GPS",best);
		listener = new MyLocationListener();
		long minTime = 1000;
		float minDistance = 0;
		lm.requestLocationUpdates(best, minTime, minDistance, listener);
	}

	class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
			clat = (int) (location.getLatitude() * 1E6);
			clon = (int) (location.getLongitude() * 1E6);
			Log.e("LOC", "lat: " + clat + " lon: " + clon);
			if ( !myLocationOverlay.isMyLocationEnabled()) {
				myLocationOverlay.enableMyLocation();
			}
//			drawCenterMarker();
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

	}

}
