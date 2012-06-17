package kr.co.taemu.myfood.shopcmd;

import java.util.ArrayList;

import kr.co.taemu.myfood.ShopAdapter;
import kr.co.taemu.myfood.ShopDAO;
import kr.co.taemu.myfood.ShopDTO;

public abstract class ShopCommand implements Command {
	protected ShopDAO dao;
	protected ShopAdapter adapter;
	protected StringBuilder query;
	protected ArrayList<ShopDTO> shops;
	
	public ShopCommand(ShopDAO dao, ShopAdapter adapter,
			StringBuilder query,ArrayList<ShopDTO> shops) {
		this.dao = dao;
		this.adapter = adapter;
		this.query = query;
		this.shops = shops;
	}
}