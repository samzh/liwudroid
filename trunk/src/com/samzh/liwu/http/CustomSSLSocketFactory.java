package com.samzh.liwu.http;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class CustomSSLSocketFactory extends org.apache.http.conn.ssl.SSLSocketFactory {
	private SSLSocketFactory FACTORY = HttpsURLConnection.getDefaultSSLSocketFactory();

	public CustomSSLSocketFactory() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
			UnrecoverableKeyException {
		super(null);
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			TrustManager[] tm = new TrustManager[] { new FullX509TrustManager() };
			context.init(null, tm, new SecureRandom());

			FACTORY = context.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Socket createSocket() throws IOException {
		return FACTORY.createSocket();
	}
}