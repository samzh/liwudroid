package com.samzh.liwu.activity;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.samzh.liwu.R;
import com.samzh.liwu.helper.PostHelper;

public class PostList extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = this.getIntent().getExtras();

		String[] postList = bundle.getStringArray("postList");
		
		String[] postTexts = new String[postList.length];
		for (int i = 0; i < postList.length; i++) {
			Map<String, String> map = PostHelper.processAPost(postList[i]);
			if (map != null) {
				postTexts[i] = map.get("text");
			}
		}

		ListView listView = new ListView(this);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.post_list_item_textview, postTexts);
		listView.setAdapter(adapter);

		setContentView(listView);

	}

}
