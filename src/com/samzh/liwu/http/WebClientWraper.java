package com.samzh.liwu.http;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, new SecureRandom());
			// SSLSocketFactory ssf = new SSLSocketFactory(ctx,
			// SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			//
			// ClientConnectionManager ccm = base.getConnectionManager();
			// SchemeRegistry sr = ccm.getSchemeRegistry();
			// sr.register(new Scheme("https", 443, ssf));

			SSLSocketFactory ssf = (SSLSocketFactory) base.getConnectionManager().getSchemeRegistry()
					.getScheme("https").getSocketFactory();
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
							return true;
						}
					}
					return false;
				}
			});
			return newClient;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}