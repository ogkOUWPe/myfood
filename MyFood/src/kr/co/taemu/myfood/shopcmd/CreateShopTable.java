package kr.co.taemu.myfood.shopcmd;

import java.util.ArrayList;

import kr.co.taemu.myfood.ShopAdapter;
import kr.co.taemu.myfood.ShopDAO;
import kr.co.taemu.myfood.ShopDTO;

public class CreateShopTable extends ShopCommand {

	public CreateShopTable(ShopDAO dao, ShopAdapter adapter, StringBuilder query, ArrayList<ShopDTO> shops) {
		super(dao, adapter, query, shops);
	}

	public void exec() {
		if (dao.createShopTable()) {
			adapter.notifyDataSetInvalidated();
		}
	}
}