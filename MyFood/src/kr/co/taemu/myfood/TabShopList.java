package kr.co.taemu.myfood;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.taemu.myfood.ShopAdapter.ViewHolder;
import kr.co.taemu.myfood.shopcmd.SearchShop;
import kr.co.taemu.myfood.shopcmd.ShopCommand;
import kr.co.taemu.myfood.shopcmd.ShopCommand.OnCompleteCallback;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TabShopList extends Activity implements OnItemClickListener, OnCompleteCallback {
	ShopDAO dao;
	ArrayList<ShopDTO> shops;
	ShopDTO shopToInsert;
	ShopAdapter adapter;
	ListView lstShops;

	HashMap<Integer, ShopCommand> cmds;

	private int index = 0;
	private int top = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_shop_list);

		lstShops = (ListView) findViewById(R.id.lstShops);
		lstShops.setOnItemClickListener(this);
		
		dao = new ShopDAO(this);
		dao.open();

		shops = new ArrayList<ShopDTO>();
		adapter = new ShopAdapter(this, shops);
		lstShops.setAdapter(adapter);
		
		
		cmds = new HashMap<Integer, ShopCommand>();
		SearchShop ss = new SearchShop(dao, adapter, null, shops);
		ss.setOnComplete(this);
		cmds.put(R.id.btnSearch, ss);

	}
	
	@Override
	protected void onDestroy() {
	  super.onDestroy();
	  dao.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		cmds.get(R.id.btnSearch).exec();
		lstShops.setSelectionFromTop(index, top);
	}

	@Override
	protected void onPause() {
		super.onPause();
		index = lstShops.getFirstVisiblePosition();
		View v = lstShops.getChildAt(0);
		top = (v == null) ? 0 : v.getTop();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this, TabShopView.class);
		ViewHolder holder = (ViewHolder) arg1.getTag();
		intent.putExtra("name", holder.txtName.getText().toString());
		intent.putExtra("tel", holder.txtTel.getText().toString());
		intent.putExtra("detail", holder.txtDetail.getText().toString());
		intent.putExtra("imagepath", holder.imgPath);
		intent.putExtra("lat", holder.lat);
		intent.putExtra("lon", holder.lon);
		TabShopListActivityGroup parent = (TabShopListActivityGroup) getParent();
		parent.setDetailIntent(intent);
		parent.startDetailView(false);
	}

	public void onComplete(ArrayList<ShopDTO> shops) {
		this.shops.clear();
		this.shops.addAll(shops);
		adapter.notifyDataSetChanged();
	}
}
