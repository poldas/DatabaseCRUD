package com.example.databaseexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.text.TextUtils;

public class MainActivity extends Activity {

	EditText name, amount, details_text;
	DBAdapter dbhelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		name = (EditText) findViewById(R.id.name);
		amount = (EditText) findViewById(R.id.amount);
		details_text = (EditText) findViewById(R.id.details_text);
		dbhelper = new DBAdapter(this);
	}
	
	public void addUser(View view) {
		String name = this.name.getText().toString();
		String amount = this.amount.getText().toString();
		long id = dbhelper.insertData(name, amount);
		if(id < 0) {
			Info.message(this, "Nie udało się dodać usera");
		} else {
			Info.message(this, "Sukces");
		}
	}
	
	public void getDetails(View view) {
		String nameText = name.getText().toString().toLowerCase();
		String data = dbhelper.getData(nameText);
		Info.message(this, data);
	}
	
	public void viewDetails(View view) {
		String data = dbhelper.getAllData();
		Info.message(this, data);
	}
	
	public void updateAmount(View view) {
		String nazwaToUpdate = details_text.getText().toString();
		String newName = name.getText().toString();
		String newAmount = amount.getText().toString();
		int count = 0 ;
		if (!TextUtils.isEmpty(newAmount)) {
			count = dbhelper.updateAmount(nazwaToUpdate, newAmount);
		} 
		if(!TextUtils.isEmpty(nazwaToUpdate)) {
			count += dbhelper.updateName(nazwaToUpdate, newName);
		}
		Info.message(this, "Zaktualizowano: " + Integer.toString(count) + " wierszy.");
	}
	
	public void deleteRow(View view) {
		// TODO Auto-generated method stub
		EditText detailsView = (EditText) findViewById(R.id.details_text);
		String row = detailsView.getText().toString();
		int count = dbhelper.deleteRow(row);
		Info.message(this, "Usunięto: " + Integer.toString(count) + " wierszy.");
	}
}
