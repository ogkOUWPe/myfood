package kr.co.taemu.myfood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TabShopView extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_shop_view);
		
		Intent intent =  getIntent();
		String name = intent.getStringExtra("name");
		String tel = intent.getStringExtra("tel");
		String detail = intent.getStringExtra("detail");
		String imagepath = intent.getStringExtra("imagepath");
		
		TextView txtName  = (TextView)findViewById(R.id.txtShopName);
		TextView txtTel  = (TextView)findViewById(R.id.txtShopTel);
		TextView txtDetail  = (TextView)findViewById(R.id.txtShopDetail);
		TextView txtImagePath  = (TextView)findViewById(R.id.txtShopImagePath);
		
		txtName.setText(name);
		txtTel.setText(tel);
		txtDetail.setText(detail);
		txtImagePath.setText(imagepath);
		
		Bitmap bm = BitmapFactory.decodeFile(imagepath);
		((ImageView)findViewById(R.id.imageView1)).setImageBitmap(bm);
	}
}
