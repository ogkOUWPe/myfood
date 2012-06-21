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
	private String name = "myfood.db";
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
				ShopDOH.CON + "," +
				ShopDOH.LAT + "," +
				ShopDOH.LON + ")" +
				"values(" + 
				"'"+shop.getName()   +"'" +","+ // String type "'"+ ... +"'" 
				"'"+shop.getTel()    +"'" +","+ // String type "'"+ ... +"'" 
				"'"+shop.getImage()  +"'" +","+ // String type "'"+ ... +"'" 
				"'"+shop.getDetail() +"'" +","+ // String type "'"+ ... +"'" 
				    shop.getLat()         +","+ // Float type + ... +
				    shop.getLon()         + // Float type + ... + and Last Item
				");";
		
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
						ShopDOH.CON+","+
						ShopDOH.LAT+","+
						ShopDOH.LON+
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
	
	
	public ArrayList<ShopDTO> fetchShopByRange(String minLat,String minLon,String maxLat, String maxLon) {
		String sql = "select "+
						ShopDOH.NAM+","+
						ShopDOH.TEL+","+
						ShopDOH.IMG+","+
						ShopDOH.CON+","+
						ShopDOH.LAT+","+
						ShopDOH.LON+
						" from " + 
						ShopDOH.TBL +
						" where " + 
						ShopDOH.LAT+ " > " + minLat+" and "+
						ShopDOH.LON+ " > " + minLon+" and "+
						ShopDOH.LAT+ " < " + maxLat+" and "+
						ShopDOH.LON+ " < " + maxLon;
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
						ShopDOH.CON + "," + 
						ShopDOH.LAT + "," + 
						ShopDOH.LON + " " + 
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
		int name_column = c.getColumnIndex(ShopDOH.NAM);
		int tel_column = c.getColumnIndex(ShopDOH.TEL);
		int image_column = c.getColumnIndex(ShopDOH.IMG);
		int contents_column = c.getColumnIndex(ShopDOH.CON);
		int lat_column = c.getColumnIndex(ShopDOH.LAT);
		int lon_column = c.getColumnIndex(ShopDOH.LON);
		while (!c.isAfterLast()) {
			String name = c.getString(name_column);
			String tel = c.getString(tel_column);
			String image = c.getString(image_column);
			String contents = c.getString(contents_column);
			String lat = c.getString(lat_column);
			String lon = c.getString(lon_column);
			c.moveToNext();
			shops.add(new ShopDTO(name, tel, image, contents,lat,lon));
			Log.e("DB", "DATA name:" + name + " tel:" + tel + " image:" + image
					+ " contents:" + contents+ " lat:" + lat+ " lon:" + lon);
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
