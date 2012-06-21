package kr.co.taemu.myfood.shopcmd;

import kr.co.taemu.myfood.ShopDAO;
import kr.co.taemu.myfood.ShopDTO;
import android.util.Log;

public class BulkInsertShop extends ShopCommand {
	
	private StringBuilder minlat;
	private StringBuilder minlon;
	private StringBuilder maxlat;
	private StringBuilder maxlon;
	private int insertAmount=0;
	private static int generatedItemCount=0;

	public BulkInsertShop(ShopDAO dao,
							StringBuilder minlat, 
							StringBuilder minlon, 
							StringBuilder maxlat, 
							StringBuilder maxlon,
							int insertAmount
			) {
		super(dao, null, null, null);
		this.minlat = minlat;
		this.minlon = minlon;
		this.maxlat = maxlat;
		this.maxlon = maxlon;
		this.insertAmount = insertAmount;
	}

	public void exec() {
		double dminlat = Double.parseDouble(minlat.toString());
		double dminlon = Double.parseDouble(minlon.toString());
		double dmaxlat = Double.parseDouble(maxlat.toString());
		double dmaxlon = Double.parseDouble(maxlon.toString());
		
		for(int i =generatedItemCount; i < generatedItemCount+insertAmount; ++i) {
			String randomLat = Double.toString((dminlat + Math.random()*(dmaxlat - dminlat)));
			String randomLon = Double.toString((dminlon + Math.random()*(dmaxlon - dminlon)));
					
			String name = "test"+Integer.toString(i);
			ShopDTO shop = new ShopDTO( name,
									    "010-1111-2222",
									    "/sdcard/food/test",
									    "this is test",
									    randomLat,randomLon);	
			if (dao.insertShop(shop)) {
				Log.e("BulkInsert:","name=>"+name+" lat=>"+randomLat+" lon=>"+randomLon);
			}
		}
		
		generatedItemCount += insertAmount;
	}

}
