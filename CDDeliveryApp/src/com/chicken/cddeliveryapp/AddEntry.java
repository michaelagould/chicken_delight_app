package com.chicken.cddeliveryapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class AddEntry extends Activity implements OnClickListener {

	Button sqlAddEntry, sqlView;
	EditText sqlAddress, sqlTown, sqlApartmentNumber, sqlPrice, sqlAmountGiven, sqlPhoneNumber, sqlNotes;
	CheckBox sqlPaymentType;
	
	protected void onCreate(Bundle savedInstanceState){
		//TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator_display);
	
		sqlAddEntry = (Button) findViewById(R.id.bAddEntry);
		sqlAddress = (EditText) findViewById(R.id.etAddress);
		sqlTown = (EditText) findViewById(R.id.etTown);
		sqlApartmentNumber = (EditText) findViewById(R.id.etApartmentNumber);
		sqlPrice = (EditText) findViewById(R.id.etPrice);
		sqlAmountGiven = (EditText) findViewById(R.id.etAmountGiven);
		sqlPaymentType = (CheckBox) findViewById(R.id.cbPaymentType);
		sqlPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
		sqlNotes = (EditText) findViewById(R.id.etNotes);
		
		sqlView = (Button) findViewById(R.id.bSQLView);
		sqlView.setOnClickListener(this);
		sqlAddEntry.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch (arg0.getId()) {
		case R.id.bAddEntry:
			
			boolean didItWork = true;
			try{
			String address = sqlAddress.getText().toString();
			String town = sqlTown.getText().toString();
			String apartmentNumber = sqlApartmentNumber.getText().toString();
			String price = sqlPrice.getText().toString();
			String amountGiven = sqlAmountGiven.getText().toString();
			Boolean paymentType = sqlPaymentType.isChecked();
			String phoneNumber = sqlPhoneNumber.getText().toString();
			String notes = sqlNotes.getText().toString();
			
			double tip = Double.parseDouble(amountGiven) - Double.parseDouble(price);
						
			DeliveryDB entry = new DeliveryDB(AddEntry.this);
			entry.open();
			entry.createEntry(address, town, apartmentNumber, price, tip, paymentType, phoneNumber, notes);
			
			entry.close();
			
			}catch (Exception e){
					didItWork = false;
					String error = e.toString();
					Dialog d = new Dialog (this);
					d.setTitle("Did not add");
					TextView tv = new TextView(this);
					tv.setText(error);
					d.setContentView(tv);
					d.show();
			}finally{
				if (didItWork){
					Dialog d = new Dialog (this);
					d.setTitle("Entry added.");
					TextView tv = new TextView(this);
					tv.setText("Success");
					d.setContentView(tv);
					d.show();
				}
			}
			break;
		case R.id.bSQLView:
			Intent z = new Intent("com.chicken.cddeliveryapp.DeliveryView");
			startActivity(z);
			break;
		}
	}

}
