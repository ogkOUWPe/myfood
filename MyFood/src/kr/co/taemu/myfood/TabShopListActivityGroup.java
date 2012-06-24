package kr.co.taemu.myfood;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TabShopListActivityGroup extends ActivityGroup {
	private Intent listIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  startListView();
	}
	
	public void startListView() {
		LocalActivityManager lam  = getLocalActivityManager(); 
		listIntent = new Intent(this,TabShopList.class);
	  listIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
	  View view = lam.startActivity("list",listIntent).getDecorView();
	  this.setContentView(view);
	}
	
	public void startDetailView(Intent intent) {
		LocalActivityManager lam  = getLocalActivityManager();
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	  View view = lam.startActivity("detail",intent).getDecorView();
	  this.setContentView(view);
	}
	
	@Override
	public void onBackPressed() {
	  startListView();
	}
}

