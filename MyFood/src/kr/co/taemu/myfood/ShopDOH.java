package kr.co.taemu.myfood;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class ShopDOH extends SQLiteOpenHelper {
	public static final String TBL= "shop";
	public static final String IDX = "_id";
	public static final String NAM = "name";
	public static final String IMG = "image";
	public static final String TEL = "tel";
	public static final String SAD = "saddr";
	public static final String LON = "lon";
	public static final String LAT = "lat";
	public static final String RAT = "rating";
	public static final String CON = "contents";
	public static final String REG = "regdate";
	public static final String EDT = "editdate";
	private Context context;

	public ShopDOH(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e("DB","Upgrading db from version "+oldVersion+" to "+newVersion);
		String sql = "drop table if exists "+ TBL;
		db.execSQL(sql);
		Log.e("DB",sql);
	}
	
	private void createTable(SQLiteDatabase db) {
		String sql = "create table "+ TBL +"(" +
					IDX+" integer primary key autoincrement,"+
					NAM+" string,"+
					IMG+" string,"+
					TEL+" string,"+
					SAD+" string,"+
					LON+" float,"+
					LAT+" float,"+
					RAT+" int,"+
					CON+" text,"+
					REG+" date,"+
					EDT+" date"+
					");";
		try {
			db.execSQL(sql);
			Log.e("DB",sql);
			Toast.makeText(context,sql, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Log.e("DB",e.getMessage());
		}
	}
}
