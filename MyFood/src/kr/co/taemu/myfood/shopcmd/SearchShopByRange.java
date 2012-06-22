package kr.co.taemu.myfood.shopcmd;

import java.util.ArrayList;

import kr.co.taemu.myfood.ShopDAO;
import kr.co.taemu.myfood.ShopDTO;

import com.google.android.maps.MapView;

public class SearchShopByRange extends ShopCommand {

	public interface OnCompleteCallback {
		void onComplete(ArrayList<ShopDTO> shops);
	}

	private OnCompleteCallback cb;

	public void setOnComplete(OnCompleteCallback cb) {
		this.cb = cb;
	}

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
		ArrayList<ShopDTO> result = dao.fetchShopByRange(sbminLat.toString(), sbminLon.toString(), sbmaxLat.toString(),
		    sbmaxLon.toString());
		if (result != null) {
			int rs = result.size();
			shops = new ArrayList<ShopDTO>();
			for (int i = 0; i < (( limit < rs ) ? limit : rs) ; ++i) {
				shops.add(result.get(i));
			}
			
			// sort by distance

			if (cb != null) {
				cb.onComplete(shops);
			}
		}
	}
}
