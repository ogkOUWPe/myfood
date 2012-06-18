package kr.co.taemu.myfood;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.taemu.myfood.shopcmd.SearchShop;
import kr.co.taemu.myfood.shopcmd.ShopCommand;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class TabShopList extends Activity implements OnClickListener {
	ShopDAO dao;
	ArrayList<ShopDTO> shops;
	ShopDTO shopToInsert;
	ShopAdapter adapter;
	ListView lstShops;
	EditText edtSearch;
	StringBuilder query;

	HashMap<Integer,ShopCommand> cmds;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_shop_list);
		
		edtSearch = (EditText) findViewById(R.id.editText1);
		lstShops = (ListView) findViewById(R.id.lstShops);

		((Button) findViewById(R.id.btnSearch)).setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		dao = new ShopDAO(this);
		dao.open();
		
		ArrayList<ShopDTO> allShops = dao.fetchAllShop();
		if (allShops != null) {
			shops = allShops;
		} else {
			shops = new ArrayList<ShopDTO>();	
		}
		adapter = new ShopAdapter(this, shops);
		lstShops.setAdapter(adapter);
		
		query = new StringBuilder();
		cmds = new HashMap<Integer,ShopCommand>();
		cmds.put(R.id.btnSearch, new SearchShop(dao,adapter,query,shops));
	}

	@Override
	protected void onPause() {
		super.onPause();
		dao.close();	
		edtSearch.setText("");
	}

	
	public void onClick(View v) {
		query.delete(0, query.length());
		query.append(edtSearch.getText().toString());
		cmds.get(v.getId()).exec();
	}

}
