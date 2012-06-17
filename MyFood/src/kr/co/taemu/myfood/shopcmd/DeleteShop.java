package kr.co.taemu.myfood.shopcmd;

import java.util.ArrayList;

import kr.co.taemu.myfood.ShopAdapter;
import kr.co.taemu.myfood.ShopDAO;
import kr.co.taemu.myfood.ShopDTO;

public class DeleteShop extends ShopCommand {
	
	public DeleteShop(ShopDAO dao, ShopAdapter adapter,
			StringBuilder query, ArrayList<ShopDTO> shops) {
		super(dao, adapter,query, shops);
	}

	@Override
	public void exec() {
		String shopNameForQuery = query.toString();
		if (!shopNameForQuery.equals("")
				&& dao.deleteShopByName(shopNameForQuery)) {

			ArrayList<ShopDTO> allShops = dao.fetchAllShop();
			if (shops != null) {
				shops.clear();
				shops.addAll(allShops);
				adapter.notifyDataSetChanged();
			}
		}
	}
}