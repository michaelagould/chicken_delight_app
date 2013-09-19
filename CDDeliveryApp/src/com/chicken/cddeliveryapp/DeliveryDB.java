package com.chicken.cddeliveryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DeliveryDB {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_ADDRESS = "addresses";
	public static final String KEY_TOWN = "town";
	public static final String KEY_APT = "apt";
	public static final String KEY_PRICE = "price";
	public static final String KEY_PAYMENTTYPE = "cashOrCredit";
	public static final String KEY_TIP = "tip";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_NOTES = "notes";
	
	private static final String DATABASE_NAME = "Deliverydb";
	private static final String DATABASE_TABLE = "deliveryTable";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_ADDRESS + " TEXT NOT NULL, " +
					KEY_TOWN + " TEXT NOT NULL, " +
					KEY_APT + " TEXT NOT NULL, " +
					KEY_PRICE + " TEXT NOT NULL, " +
					KEY_PAYMENTTYPE + " TEXT NOT NULL, " +
					KEY_TIP + " TEXT NOT NULL, " +
					KEY_PHONE + " TEXT NOT NULL, " +
					KEY_NOTES + " TEXT NOT NULL);"
			);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	
	public DeliveryDB(Context c){
		ourContext = c;
	}
	
	public DeliveryDB open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	public void close(){
		ourHelper.close();
	}

	public long createEntry(String address, String town,
			String apartmentNumber, String price, double tip,
			Boolean paymentType, String phoneNumber, String notes) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_ADDRESS, address);
		cv.put(KEY_TOWN, town);
		cv.put(KEY_APT, apartmentNumber);
		cv.put(KEY_PRICE, price);
		cv.put(KEY_TIP, tip);
		if(paymentType)
			cv.put(KEY_PAYMENTTYPE, "credit");
		else
			cv.put(KEY_PAYMENTTYPE, "cash");
		cv.put(KEY_PHONE, phoneNumber);
		cv.put(KEY_NOTES, notes);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
		
	}

	public String getViewData() {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_ADDRESS, KEY_PRICE, KEY_TIP};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String result = "";
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iAddress = c.getColumnIndex(KEY_ADDRESS);
		int iPrice = c.getColumnIndex(KEY_PRICE);
		int iTip = c.getColumnIndex(KEY_TIP);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			result = result + c.getString(iRow) + " " + c.getString(iAddress) + " " + c.getString(iPrice) + " " + c.getString(iTip) + "\n";
		}
		return result;
	}
}
