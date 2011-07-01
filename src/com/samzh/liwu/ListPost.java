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

		ListView listView = (ListView) findViewById(R.id.post_list);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.id.post_list, postList);
		listView.setAdapter(adapter);

		setContentView(R.layout.list_post);

	}

}
