package kr.co.taemu.myfood;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class ShopDAO {
	private SQLiteDatabase db;
	private ShopDOH doh;
	private Context context;
	private String name = "restaurants.db";
	private int version = 1;

	public ShopDAO(Context context) {
		doh = new ShopDOH(context, name, null, version);
		this.context = context;
	}

	public void open() throws SQLException {
		db = doh.getWritableDatabase();
	}

	public void close() {
		doh.close();
	}

	public boolean insertShop(ShopDTO shop) {
		String sql = "insert into " + ShopDOH.TBL + "(" + 
				ShopDOH.NAM + "," +
				ShopDOH.TEL + "," +
				ShopDOH.IMG + "," +
				ShopDOH.CON + ")" +
				"values('" + 
				shop.getName() + "','"+ 
				shop.getTel()   + "','" +
				shop.getImage() + "','" + 
				shop.getDetail()+"');";
		Log.e("DB", sql);
		try {
			db.execSQL(sql);
			Toast.makeText(context,sql, Toast.LENGTH_SHORT).show();
			return true;
		} catch (SQLException e) {
			Log.e("DB", e.getMessage());
		}
		return false;
	}

	public boolean deleteShopByName(String name) {
		String sql = "delete from " + ShopDOH.TBL + " where " + ShopDOH.NAM
				+ "='" + name + "'";
		try {
			Log.e("DB", sql);
			db.rawQuery(sql, null).moveToFirst();
			Toast.makeText(context,sql, Toast.LENGTH_SHORT).show();
			return true;
		} catch (SQLException e) {
			Log.e("DB", e.getMessage());
		}
		return false;
	}

	public ArrayList<ShopDTO> fetchShopByName(String searchName) {
		String sql = "select "+
						ShopDOH.NAM+","+
						ShopDOH.TEL+","+
						ShopDOH.IMG+","+
						ShopDOH.CON+
						" from " + 
						ShopDOH.TBL +
						" where " + 
						ShopDOH.NAM+ "='" + searchName+"'";
		try {
			Log.e("DB", sql);
			Toast.makeText(context,sql, Toast.LENGTH_SHORT).show();
			return getShopsFromSql(sql);
		} catch (Exception e) {
			Log.e("DB", e.getMessage());
		}
		return null;
	}

	public ArrayList<ShopDTO> fetchAllShop() {
		String sql = "select " + 
						ShopDOH.NAM + "," + 
						ShopDOH.TEL + "," +
						ShopDOH.IMG + "," + 
						ShopDOH.CON + " " + 
						"from " + ShopDOH.TBL;
		try {
			Log.e("DB", sql);
			Toast.makeText(context,sql, Toast.LENGTH_SHORT).show();
			return getShopsFromSql(sql);
		} catch (Exception e) {
			Log.e("DB", e.getMessage());
		}
		return null;
	}

	private ArrayList<ShopDTO> getShopsFromSql(String sql) throws Exception {
		ArrayList<ShopDTO> shops = new ArrayList<ShopDTO>();
		Cursor c = db.rawQuery(sql, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			String name = c.getString(0);
			String tel = c.getString(1);
			String image = c.getString(2);
			String contents = c.getString(3);
			c.moveToNext();
			shops.add(new ShopDTO(name, tel, image, contents));
			Log.e("DB", "DATA name:" + name + " tel:" + tel + " image:" + image
					+ " contents:" + contents);
		}
		c.close();
		return shops;
	}

	public boolean dropShopTable() {
		String sql = "drop table if exists " + ShopDOH.TBL;
		try {
			Log.e("DB", sql);
			Toast.makeText(context,sql, Toast.LENGTH_SHORT).show();
			db.execSQL(sql);
			return true;
			
		} catch (Exception e) {
			Log.e("DB", e.getMessage());
		}
		return false;
	}

	public boolean createShopTable() {
		try {
			doh.onCreate(doh.getWritableDatabase());
			return true;
		} catch (Exception e) {
			Log.e("DB", e.getMessage());
		}
		return false;
	}
}
