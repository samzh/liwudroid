package com.samzh.liwu;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.samzh.liwu.http.HttpsConnectionUtils;
import com.samzh.liwu.service.LoginService;

public class LoginMain extends Activity {
	/** Called when the activity is first created. */

	private LoginService loginService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button btnLogin = (Button) findViewById(R.id.btnLogin);

		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});

		//this.bindService(new Intent("com.samzh.liwu.service.LoginService"), this.serviceConnection, BIND_AUTO_CREATE);
	}
	
	@Override
	public void onDestroy() {
		this.unbindService(serviceConnection);        
        super.onDestroy(); 
	}

	private void login() {
		String username = ((EditText) findViewById(R.id.textLogin)).getText().toString();
		
		HttpsConnectionUtils util = new HttpsConnectionUtils();
		String ret = util.login();
		System.out.println(ret);
		
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			loginService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			loginService = (LoginService) service;

			loginService.login();

		}
	};
}