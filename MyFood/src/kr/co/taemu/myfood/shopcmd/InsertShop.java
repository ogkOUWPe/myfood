package kr.co.taemu.myfood.shopcmd;

import java.util.ArrayList;

import kr.co.taemu.myfood.R;
import kr.co.taemu.myfood.ShopAdapter;
import kr.co.taemu.myfood.ShopDAO;
import kr.co.taemu.myfood.ShopDTO;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class InsertShop extends ShopCommand {

	private Context context;
	private AlertDialog dlg;

	public InsertShop(ShopDAO dao, ShopAdapter adapter, StringBuilder query,
			ArrayList<ShopDTO> shops, Context context) {
		super(dao, adapter, query, shops);
		this.context = context;
		createInputDialog();
	}

	public void exec() {
		dlg.show();
	}

	class InputDialogListener implements OnClickListener {
		private EditText edtDlgName;
		private EditText edtDlgTel;
		private EditText edtDlgImage;
		private EditText edtDlgContents;

		public InputDialogListener(View layout) {
			edtDlgName = (EditText) layout.findViewById(R.id.edtName);
			edtDlgTel = (EditText) layout.findViewById(R.id.edtTel);
			edtDlgImage = (EditText) layout.findViewById(R.id.edtImage);
			edtDlgContents = (EditText) layout.findViewById(R.id.edtContents);
		}

		public void onClick(View v) {
			String name = edtDlgName.getText().toString();
			String tel = edtDlgTel.getText().toString();
			String image = edtDlgImage.getText().toString();
			String contents = edtDlgContents.getText().toString();
			if (v.getId() == R.id.btnSubmit) {
				ShopDTO shop = new ShopDTO(name, tel, image, contents);
				if (dao.insertShop(shop)) {
					if (shops.add(shop)) {
						adapter.notifyDataSetChanged();
					}
				}
			}
			dlg.dismiss();
		}
	}

	private void createInputDialog() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.shop_input_dialog, null);

		Button btnSubmit = (Button) layout.findViewById(R.id.btnSubmit);
		Button btnCancel = (Button) layout.findViewById(R.id.btnCancel);

		InputDialogListener dlgListener = new InputDialogListener(layout);
		btnSubmit.setOnClickListener(dlgListener);
		btnCancel.setOnClickListener(dlgListener);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(layout);
		dlg = builder.create();
	}

}
