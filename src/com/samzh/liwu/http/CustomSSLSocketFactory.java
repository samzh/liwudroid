package com.samzh.liwu.http;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class CustomSSLSocketFactory extends org.apache.http.conn.ssl.SSLSocketFactory {
	private SSLSocketFactory FACTORY = HttpsURLConnection.getDefaultSSLSocketFactory();

	private SSLContext sslContext = SSLContext.getInstance("TLS");

	public CustomSSLSocketFactory() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
			UnrecoverableKeyException {
		super(null);
		try {
			sslContext = SSLContext.getInstance("TLS");
			TrustManager[] tm = new TrustManager[] { new FullX509TrustManager() };
			sslContext.init(null, tm, new SecureRandom());

			FACTORY = sslContext.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Socket createSocket() throws IOException {
		return FACTORY.createSocket();
	}

	public CustomSSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException,
			KeyStoreException, UnrecoverableKeyException {
		super(truststore);

		// 自己署名証明書を受け付けるカスタムSSLContextの準備
		TrustManager tm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		sslContext.init(null, new TrustManager[] { tm }, null);
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
			UnknownHostException {
		// カスタムSSLContext経由で生成したSSLソケットを返す。
		return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
	}
}