package com.samzh.liwu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.samzh.liwu.R;
import com.samzh.liwu.http.HttpsConnectionUtils;
import com.samzh.liwu.util.Constant;

public class PostMain extends Activity {

	private final String mimeType = "text/html";

	private final String encoding = "utf-8";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_main);
		HttpsConnectionUtils utils = new HttpsConnectionUtils();

		Bundle bundle = this.getIntent().getExtras();
		String href = bundle.getString("href");

		String content = utils.getPost(Constant.POST_MAIN_PREFIX + href);
		try {
			WebView view = (WebView) findViewById(R.id.webview_post_main);
			view.loadData(content, mimeType, encoding);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
