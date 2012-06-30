package kr.co.taemu.myfood;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.taemu.myfood.shopcmd.SearchShop;
import kr.co.taemu.myfood.shopcmd.ShopCommand;
import kr.co.taemu.myfood.shopcmd.ShopCommand.OnCompleteCallback;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UnimplementedSubPage03 extends Activity implements OnClickListener, OnCompleteCallback {
	EditText edtSearch;
	StringBuilder query;
	HashMap<Integer, ShopCommand> cmds;
	ShopDAO dao;

	TextView txtSelectedShops;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unimplemented_subpage03);

		edtSearch = (EditText) findViewById(R.id.editText1);
		((Button) findViewById(R.id.btnSearch)).setOnClickListener(this);

		txtSelectedShops = (TextView) findViewById(R.id.txtSelectedShops);

		dao = new ShopDAO(this);
		dao.open();

		query = new StringBuilder();
		cmds = new HashMap<Integer, ShopCommand>();
		SearchShop ss = new SearchShop(dao, null, query, null);
		ss.setOnComplete(this);
		cmds.put(R.id.btnSearch, ss);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dao.close();
	}

	@Override
	public void onClick(View v) {
		query.delete(0, query.length());
		query.append(edtSearch.getText().toString());
		cmds.get(v.getId()).exec();
	}

	@Override
	public void onComplete(ArrayList<ShopDTO> shops) {
		for (ShopDTO s : shops) {
			String msg = "name: " + s.getName() + " lat: " + s.getLat() + " lon: " + s.getLon()+"\n";
			Log.e("ShopSearch", msg);
			txtSelectedShops.append(msg);
		}
	}

}
