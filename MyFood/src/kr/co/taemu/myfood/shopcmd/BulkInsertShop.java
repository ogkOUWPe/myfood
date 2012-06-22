package kr.co.taemu.myfood.shopcmd;

import java.io.File;
import java.util.Random;

import kr.co.taemu.myfood.ShopDAO;
import kr.co.taemu.myfood.ShopDTO;
import android.os.Environment;
import android.util.Log;

public class BulkInsertShop extends ShopCommand {

	private StringBuilder minlat;
	private StringBuilder minlon;
	private StringBuilder maxlat;
	private StringBuilder maxlon;
	private int limit = 0;
	private static int generatedItemCount = 0;
	File[] flist;

	public BulkInsertShop(ShopDAO dao, StringBuilder minlat, StringBuilder minlon, StringBuilder maxlat,
	    StringBuilder maxlon, int limit) {
		super(dao, null, null, null);
		this.minlat = minlat;
		this.minlon = minlon;
		this.maxlat = maxlat;
		this.maxlon = maxlon;
		this.limit = limit;

		File sdCardRoot = Environment.getExternalStorageDirectory();
		File yourDir = new File(sdCardRoot, "/food");
		flist = yourDir.listFiles();
	}

	public void exec() {
		double dminlat = Double.parseDouble(minlat.toString());
		double dminlon = Double.parseDouble(minlon.toString());
		double dmaxlat = Double.parseDouble(maxlat.toString());
		double dmaxlon = Double.parseDouble(maxlon.toString());

		for (int i = generatedItemCount; i < generatedItemCount + limit; ++i) {
			String randomLat = Double.toString((dminlat + Math.random() * (dmaxlat - dminlat)));
			String randomLon = Double.toString((dminlon + Math.random() * (dmaxlon - dminlon)));

			String name = "test" + Integer.toString(i);
			String imagePath = null;
			try {
				if (flist != null) {
					imagePath = flist[1 + (new Random()).nextInt(flist.length - 1)].getCanonicalPath();
				} else {
					imagePath = "/sdcard/food/test";
				}
			} catch (Exception e) {
				Log.e("BulkInsert:", e.getMessage());
			}

			ShopDTO shop = new ShopDTO(name, "010-1111-2222", imagePath, "this is test", randomLat, randomLon);
			if (dao.insertShop(shop)) {
				Log.e("BulkInsert:", "name=>" + name + " lat=>" + randomLat + " lon=>" + randomLon);
			}
		}

		generatedItemCount += limit;
	}

}
