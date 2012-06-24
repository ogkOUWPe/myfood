package kr.co.taemu.myfood;

import java.util.HashMap;
import java.util.Map;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Main extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		loadData();
		setupTab();
	}

	private void loadData() {

	}

	private void setupTab() {
		HashMap<String, Object> extras = new HashMap<String, Object>();
		extras.put("str_a", "test1");
		extras.put("str_b", "test2");
		extras.put("str_c", "test3");
		extras.put("bool_d", true);
		extras.put("bool_e", false);
		extras.put("int_f", 999);
		tabSpecFactory(null, "tab01", TabShopListActivityGroup.class, "업소");
		tabSpecFactory(null, "tab02", TabShopMap.class, "지도");
		tabSpecFactory(null, "tab03", UnimplementedSubPage03.class, "검색");
		tabSpecFactory(extras, "tab04", UnimplementedSubPage04.class, "설정");
	}

	private void tabSpecFactory(HashMap<String, Object> extras, Object... params) {
		TabHost tabs = getTabHost();
		TabSpec spec = tabs.newTabSpec((String) params[0]);
		Intent intent = new Intent(this, (Class<?>) params[1]);
		if (extras != null) {
			for (Map.Entry<String, Object> entry : extras.entrySet()) {
				Object o = entry.getValue();
				String key = entry.getKey();
				if (o instanceof String) {
					intent.putExtra(key, (String) o);
				} else if (o instanceof Boolean) {
					Boolean b = (Boolean) o;
					intent.putExtra(key, b.booleanValue());
				} else if (o instanceof Integer) {
					intent.putExtra(key, ((Integer) o).intValue());
				} else {
					// drop unsupported intent extra
				}
			}
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		spec.setContent(intent);
		spec.setIndicator((String) params[2]);
		tabs.addTab(spec);
	}
}