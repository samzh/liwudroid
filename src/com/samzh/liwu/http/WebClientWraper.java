package com.samzh.liwu.http;

import java.security.KeyStore;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.HttpContext;

public class WebClientWraper {

	public static HttpClient wrapClient(HttpClient base) {
		try {

			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory ssf = new CustomSSLSocketFactory(trustStore);

			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			ClientConnectionManager ccm = base.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));

			DefaultHttpClient newClient = new DefaultHttpClient(ccm, base.getParams());

			newClient.setRedirectHandler(new DefaultRedirectHandler() {
				@Override
				public boolean isRedirectRequested(HttpResponse response, HttpContext context) {
					boolean isRedirect = false;
					isRedirect = super.isRedirectRequested(response, context);
					if (!isRedirect) {
						int responseCode = response.getStatusLine().getStatusCode();
						if (responseCode == HttpStatus.SC_MOVED_PERMANENTLY
								|| responseCode == HttpStatus.SC_MOVED_TEMPORARILY) {
							System.out.println("Redirect:" + responseCode);
							return true;
						}
					}
					return isRedirect;
				}
			});
			return newClient;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}