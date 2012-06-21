package kr.co.taemu.myfood;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShopAdapter extends ArrayAdapter<ShopDTO> {

	private ArrayList<ShopDTO> restaurants;
	private Context context;

	public ShopAdapter(Context context, List<ShopDTO> restaurants) {
		super(context, android.R.layout.simple_list_item_1, restaurants);
		this.context = context;
		this.restaurants = (ArrayList<ShopDTO>) restaurants;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(R.layout.shop_list_row, parent, false);

			holder = new ViewHolder();
			holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
			holder.txtTel = (TextView) convertView.findViewById(R.id.txtTel);
			holder.txtDetail = (TextView) convertView.findViewById(R.id.txtDetail);
			holder.imgRest = (ImageView) convertView.findViewById(R.id.imgRest);
			holder.rbRating = (RatingBar) convertView.findViewById(R.id.rbRating);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String name = restaurants.get(position).getName();
		String tel = restaurants.get(position).getTel();
		String detail = restaurants.get(position).getDetail();
		String imgpath = restaurants.get(position).getImage();
		holder.txtName.setText(name);
		holder.txtTel.setText(tel);
		holder.txtDetail.setText(detail);
		holder.rbRating.setRating((float) 3.5);
		File imgFile = new File(imgpath);
		if (imgFile.exists()) {
			Bitmap bm = BitmapFactory.decodeFile(imgpath);
			holder.imgRest.setImageBitmap(bm);
		} else {
			holder.imgRest.setImageResource(R.drawable.no_image);
		}

		return convertView;
	}

	static class ViewHolder {
		TextView txtName;
		TextView txtTel;
		TextView txtDetail;
		ImageView imgRest;
		RatingBar rbRating;
	}
}
