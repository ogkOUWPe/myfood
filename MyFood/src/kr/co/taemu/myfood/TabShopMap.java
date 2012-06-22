package kr.co.taemu.myfood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.taemu.myfood.shopcmd.BulkInsertShop;
import kr.co.taemu.myfood.shopcmd.ResetShop;
import kr.co.taemu.myfood.shopcmd.SearchShopByRange;
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

public class TabShopMap extends MapActivity implements OnClickListener, SearchShopByRange.OnCompleteCallback {
	Drawable drawable;
	
	List<Overlay> list;

	ShopDAO dao;
	HashMap<Integer, ShopCommand> cmds;
	StringBuilder sbminLat;
	StringBuilder sbminLon;
	StringBuilder sbmaxLat;
	StringBuilder sbmaxLon;

	MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_shop_map);

		setupMap();

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
	}

	@Override
	protected void onPause() {
		super.onPause();
		dao.close();
	}

	private void setupMap() {
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);

		GeoPoint p = new GeoPoint(37519789, 126940308);
		MapController mc = mapView.getController();
		mc.animateTo(p);
		mc.setZoom(15); // 1 ~ 21
		list = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.arrow);
		ShopOverlay shopOverlay = new ShopOverlay(drawable);
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
		list.clear();
		ShopOverlay shopOverlay = new ShopOverlay(drawable);
		for (ShopDTO shop : shops) {
			int iLat = (int) (Double.parseDouble(shop.getLat()) * 1E6);
			int iLon = (int) (Double.parseDouble(shop.getLon()) * 1E6);
			GeoPoint p = new GeoPoint(iLat, iLon);
			OverlayItem overlayItem = new OverlayItem(p, "", "");
			shopOverlay.addOverlay(overlayItem);
		}
		list.add(shopOverlay);
		mapView.invalidate();
	}
	
}
