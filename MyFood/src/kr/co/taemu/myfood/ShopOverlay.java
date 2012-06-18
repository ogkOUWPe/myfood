package kr.co.taemu.myfood;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class ShopOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> alist = new ArrayList<OverlayItem>();

	public ShopOverlay(Drawable drawable) {
		super(boundCenterBottom(drawable));
	}

	@Override
	protected OverlayItem createItem(int i) {
		return alist.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return alist.size();
	}

	void addOverlay(OverlayItem overlayItem) {
		alist.add(overlayItem);
		this.populate();
	}
}
