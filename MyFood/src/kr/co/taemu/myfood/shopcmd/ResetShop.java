package kr.co.taemu.myfood.shopcmd;

import kr.co.taemu.myfood.ShopDAO;

public class ResetShop extends ShopCommand {

	public ResetShop(ShopDAO dao) {
		super(dao, null, null, null);
	}

	public void exec() {
		if ( dao.dropShopTable() ) {
			if (dao.createShopTable()) {
			}
		}
	}
}