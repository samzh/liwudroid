package com.samzh.liwu.activity;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.samzh.liwu.R;
import com.samzh.liwu.helper.PostHelper;

public class PostList extends Activity {

	private String[] postHrefs;

	private String[] postTexts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = this.getIntent().getExtras();

		String[] postList = bundle.getStringArray("postList");

		postTexts = new String[postList.length];
		postHrefs = new String[postList.length];
		for (int i = 0; i < postList.length; i++) {
			Map<String, String> map = PostHelper.processAPost(postList[i]);
			if (map != null) {
				postTexts[i] = map.get("text");
				postHrefs[i] = map.get("href");
			}
		}

		ListView listView = new ListView(this);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.post_list_item_textview, postTexts);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (postHrefs.length > 0) {
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putString("href", postHrefs[position]);
					intent.putExtras(bundle);
					intent.setClass(PostList.this, PostMain.class);
					startActivity(intent);
				}
			}
		});
		setContentView(listView);
	}

}
