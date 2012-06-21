package kr.co.taemu.myfood;

import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class UnimplementedSubPage04 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unimplemented_subpage04);
		Bundle b = getIntent().getExtras();
		if (b != null) {
			String passed = getExtrasString(b);
			TextView txtView = (TextView) findViewById(R.id.textView4);
			txtView.setText(passed);
		}

	}

	private String getExtrasString(Bundle b) {
		Set<String> s = b.keySet();
		Iterator<String> i = s.iterator();
		String passed = "";
		while (i.hasNext()) {
			String key = i.next();
			if (key.startsWith("str_")) {
				passed += b.getString(key);
			} else if (key.startsWith("bool_")) {
				passed += Boolean.valueOf(b.getBoolean(key)).toString();
			} else if (key.startsWith("int_")) {
				passed += "" + b.getInt(key);
			} else {
				passed += "unknown type";
			}
			passed += "\n";
		}

		return passed;
	}
}
