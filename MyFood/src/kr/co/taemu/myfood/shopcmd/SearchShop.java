package kr.co.taemu.myfood.shopcmd;

import java.util.ArrayList;

import kr.co.taemu.myfood.ShopAdapter;
import kr.co.taemu.myfood.ShopDAO;
import kr.co.taemu.myfood.ShopDTO;

public class SearchShop extends ShopCommand {

	public SearchShop(ShopDAO dao, ShopAdapter adapter, StringBuilder query, ArrayList<ShopDTO> shops) {
		super(dao, adapter, query, shops);
	}

	public void exec() {
		String shopNameForQuery = (query == null) ? null : query.toString();
		
		if ( shopNameForQuery == null || shopNameForQuery.equals("")) {
			ArrayList<ShopDTO> allShops = dao.fetchAllShop();
			if (allShops != null && cb != null) {
				cb.onComplete(allShops);
			}
		} else {
			ArrayList<ShopDTO> matchedShops = dao.fetchShopByName(shopNameForQuery);
			if (matchedShops != null && cb != null) {
				cb.onComplete(matchedShops);
			}
		}
	}
}
