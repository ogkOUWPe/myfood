package kr.co.taemu.myfood.shopcmd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kr.co.taemu.myfood.ShopDAO;
import kr.co.taemu.myfood.ShopDTO;

import com.google.android.maps.MapView;

public class SearchShopByRange extends ShopCommand {

	private StringBuilder sbminLat;
	private StringBuilder sbminLon;
	private StringBuilder sbmaxLat;
	private StringBuilder sbmaxLon;
	private int limit = 10;

	public SearchShopByRange(ShopDAO dao, MapView mapView, StringBuilder sbminLat, StringBuilder sbminLon,
	    StringBuilder sbmaxLat, StringBuilder sbmaxLon, int limit) {
		super(dao, null, null, null);
		this.sbminLat = sbminLat;
		this.sbminLon = sbminLon;
		this.sbmaxLat = sbmaxLat;
		this.sbmaxLon = sbmaxLon;
		this.limit = limit;
	}

	public void exec() {
		String sminLat = sbminLat.toString();
		String sminLon = sbminLon.toString();
		String smaxLat = sbmaxLat.toString();
		String smaxLon = sbmaxLon.toString();
		ArrayList<ShopDTO> result = dao.fetchShopByRange(sminLat, sminLon,smaxLat,smaxLon);
		if (result != null) {
			// sort by distance
			Collections.sort(result,new DistanceComparator(sminLat, sminLon,smaxLat,smaxLon));
			int rs = result.size();
			shops = new ArrayList<ShopDTO>();
			for (int i = 0; i < (( limit < rs ) ? limit : rs) ; ++i) {
				shops.add(result.get(i));
			}
			if (cb != null) {
				cb.onComplete(shops);
			}
		}
	}
	
	
	class DistanceComparator implements Comparator<ShopDTO> {
		
		private double refLat;
		private double refLon;
		
		public DistanceComparator(String sminLat,String sminLon,String smaxLat, String smaxLon) {
			this.refLat = (Double.parseDouble(smaxLat) + Double.parseDouble(sminLat))/2;
			this.refLon = (Double.parseDouble(smaxLon) + Double.parseDouble(sminLon))/2;
    }
		
		private double getDistance(double lat, double lon) {
			double p1 = Math.cos(Math.toRadians(refLon-lon));
		  double p2 = Math.cos(Math.toRadians(refLat-lat));
		  double p3 = Math.cos(Math.toRadians(refLat+lat));
		  return Math.toRadians(Math.toDegrees(Math.acos(((p1*(p2+p3))+(p2-p3))/2)))*6371;
		}
		
		public int compare(ShopDTO lhs, ShopDTO rhs) {
			double lhsLat = Double.parseDouble(lhs.getLat());
			double lhsLon = Double.parseDouble(lhs.getLon());
			double rhsLat = Double.parseDouble(rhs.getLat());
			double rhsLon = Double.parseDouble(rhs.getLon());
		  
			double lhsDistance = getDistance(lhsLat,lhsLon);
		  double rhsDistance = getDistance(rhsLat,rhsLon);
		  
		  if ( lhsDistance < rhsDistance )
		  	return -1;
		  else if ( lhsDistance > rhsDistance )
		  	return 1;
		  else 
		  	return 0;
    }
	}
	
}
