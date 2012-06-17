package kr.co.taemu.myfood.shopcmd;

import java.util.ArrayList;

import kr.co.taemu.myfood.ShopAdapter;
import kr.co.taemu.myfood.ShopDAO;
import kr.co.taemu.myfood.ShopDTO;


public class DropShopTable extends ShopCommand {

	public DropShopTable(ShopDAO dao, ShopAdapter adapter,
			StringBuilder query, ArrayList<ShopDTO> shops) {
		super(dao, adapter, query, shops);
	}

	@Override
	public void exec() {
		if (dao.dropShopTable()) {
			adapter.notifyDataSetInvalidated();
		}
	}
}