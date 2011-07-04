package com.samzh.liwu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.samzh.liwu.http.HttpsConnectionUtils;

public class LoginMain extends Activity {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button btnLogin = (Button) findViewById(R.id.btn_login);

		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});

		// this.bindService(new Intent("com.samzh.liwu.service.LoginService"),
		// this.serviceConnection, BIND_AUTO_CREATE);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void login() {

		HttpsConnectionUtils util = new HttpsConnectionUtils();
		String content = util.login();

		if (content != null && content.length() > 0) {
			String[] postList = util.processList(content);
			
			util.processAPost(content);

			Bundle bundle = new Bundle();
			bundle.putStringArray("postList", postList);
			Intent intent = new Intent();

			intent.putExtras(bundle);
			intent.setClass(LoginMain.this, ListPost.class);
			startActivity(intent);

		}

	}

}