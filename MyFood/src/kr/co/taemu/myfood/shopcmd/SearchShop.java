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
		String shopNameForQuery = query.toString();
		if (shopNameForQuery.equals("")) {
			ArrayList<ShopDTO> allShops = dao.fetchAllShop();
			if (allShops != null) {
				shops.clear();
				shops.addAll(allShops);
				adapter.notifyDataSetChanged();
			}
		} else if (!shopNameForQuery.equals("")) {
			ArrayList<ShopDTO> matchedShops = dao.fetchShopByName(shopNameForQuery);
			if (matchedShops != null) {
				shops.clear();
				shops.addAll(matchedShops);
				adapter.notifyDataSetChanged();
			}
		}
	}
}
