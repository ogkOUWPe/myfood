package kr.co.taemu.myfood;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

public class TabShopListActivityGroup extends ActivityGroup  {
	View listView;
	View detailView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  startListView();
	}
	
	public void startListView() {
		
		LocalActivityManager lam  = getLocalActivityManager(); 
		Intent intent = new Intent(this,TabShopList.class);
	  intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
	  View view = lam.startActivity("list", intent).getDecorView();
	  
	  if ( detailView != null ) {
	  	detailView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
	  }
	  listView = view;
		this.setContentView(view);
	  listView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
	}
	
	public void startDetailView(Intent intent) {
		
		LocalActivityManager lam  = getLocalActivityManager();
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	  View view = lam.startActivity("detail",intent).getDecorView();
	  
	  if ( listView != null ) {
	  	listView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_left));
	  }
	  detailView = view;
	  this.setContentView(view);
		detailView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
	}
	
	@Override
	public void onBackPressed() {
	  startListView();
	}
}

