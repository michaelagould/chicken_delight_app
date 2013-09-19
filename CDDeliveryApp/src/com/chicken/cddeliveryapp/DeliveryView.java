package com.chicken.cddeliveryapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DeliveryView extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delivery_view);
		TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
		DeliveryDB info = new DeliveryDB(this);
		info.open();
		String data = info.getViewData();
		info.close();
		tv.setText(data);
	}
}
