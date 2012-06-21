package kr.co.taemu.myfood.shopcmd;

import java.util.ArrayList;

import kr.co.taemu.myfood.ShopAdapter;
import kr.co.taemu.myfood.ShopDAO;
import kr.co.taemu.myfood.ShopDTO;
import android.content.Context;

public class InsertShop extends ShopCommand {
	private Context context;
	private StringBuilder name;
	private StringBuilder tel;
	private StringBuilder image;
	private StringBuilder contents;
	private StringBuilder lat;
	private StringBuilder lon;

	public InsertShop(ShopDAO dao, ShopAdapter adapter,
			StringBuilder name, 
			StringBuilder tel, 
			StringBuilder image, 
			StringBuilder contents, 
			StringBuilder lat, 
			StringBuilder lon, 
			ArrayList<ShopDTO> shops, Context context) {
		super(dao, adapter, null, shops);
		this.context = context;
		this.name = name;
		this.tel = tel;
		this.image = image;
		this.contents = contents;
		this.lat = lat;
		this.lon = lon;
	}

	public void exec() {
		ShopDTO shop = new ShopDTO( name.toString(), 
									tel.toString(),
									image.toString(),
									contents.toString(),
									lat.toString(),
									lon.toString());
		if (dao.insertShop(shop)) {
			if (shops.add(shop)) {
				adapter.notifyDataSetChanged();
			}
		}
	}

}
