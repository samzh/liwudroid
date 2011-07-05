package com.samzh.liwu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.samzh.liwu.R;

public class PostMain extends Activity {

	private final String mimeType = "text/html";

	private final String encoding = "utf-8";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_main);
		String dataBase = "https://www.253874.com/new/";
		Bundle bundle = this.getIntent().getExtras();
		String href = bundle.getString("href");
		try {
			WebView view = (WebView) findViewById(R.id.webview_post_main);
			Log.v(PostMain.class.getSimpleName(), dataBase + href);
			view.loadData(dataBase + href, mimeType, encoding);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
