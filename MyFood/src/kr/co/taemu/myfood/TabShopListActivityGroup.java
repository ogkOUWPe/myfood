package kr.co.taemu.myfood;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

public class TabShopListActivityGroup extends ActivityGroup {
	View listView;
	View detailView;
	View locationView;
	private final int LISTVIEW = 1;
	private final int DETAILVIEW = 2;
	private final int LOCATIONVIEW = 3;
	private int whichView = LISTVIEW;
	private Intent detailIntent;
	private Intent locationIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startListView();
	}
	
	public void setDetailIntent(Intent intent) {
		detailIntent = intent;
	}

	public void setLocationIntent(Intent intent) {
		locationIntent = intent;
	}

	public void startListView() {
		whichView = LISTVIEW;
		LocalActivityManager lam = getLocalActivityManager();
		Intent intent = new Intent(this, TabShopList.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		View view = lam.startActivity("list", intent).getDecorView();

		if (detailView != null) {
			detailView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
		}
		listView = view;
		this.setContentView(view);
		listView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
	}

	public void startDetailView(boolean isReturnFromLocationView) {
		whichView = DETAILVIEW;
		LocalActivityManager lam = getLocalActivityManager();
		detailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		View view = lam.startActivity("detail", detailIntent).getDecorView();

		if ( isReturnFromLocationView ) {
			if ( locationView != null ) {
				locationView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
			}
			detailView = view;
			this.setContentView(view);
			detailView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
		} else {
			if (listView != null) {
				listView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_left));
			}
			detailView = view;
			this.setContentView(view);
			detailView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
		}
	}

	public void startLocationView() {
		whichView = LOCATIONVIEW;
		LocalActivityManager lam = getLocalActivityManager();
		locationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		View view = lam.startActivity("location", locationIntent).getDecorView();

		if (detailView != null) {
			detailView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_left));
		}
		locationView = view;
		this.setContentView(view);
		locationView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
	}

	@Override
	public void onBackPressed() {
		if ( whichView == DETAILVIEW ) {
			startListView();
		} else if ( whichView == LOCATIONVIEW  ) {
			startDetailView(true);
		}
	}
}
