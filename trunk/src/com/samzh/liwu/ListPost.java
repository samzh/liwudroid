package com.samzh.liwu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListPost extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = this.getIntent().getExtras();

		String[] postList = bundle.getStringArray("postList");

		ListView listView = new ListView(this);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_post, postList);
		listView.setAdapter(adapter);

		setContentView(listView);

	}

}
