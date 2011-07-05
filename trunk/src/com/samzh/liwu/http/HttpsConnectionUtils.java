package com.samzh.liwu.http;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.net.Uri;
import android.util.Log;

import com.samzh.liwu.util.Constant;

public class HttpsConnectionUtils {

	private static String aspSessionId = "";

	public String login(String username, String password) {
		HttpClient httpClient = new DefaultHttpClient();

		Uri uri = Uri.parse(Constant.LOGIN);

		HttpHost host = new HttpHost(uri.getHost(), 443, uri.getScheme());
		HttpGet get = new HttpGet(uri.getPath());
		HttpPost httppost = new HttpPost(uri.getPath());
		StringBuilder out1 = new StringBuilder();
		try {
			httpClient = WebClientWraper.wrapClient(httpClient);
			HttpResponse response = httpClient.execute(host, get);
			httpClient.getConnectionManager().shutdown();

			Header[] cookies = response.getHeaders("Set-Cookie");
			for (int i = 0; i < cookies.length; i++) {
				aspSessionId += cookies[i].getValue() + ";";
			}

			httpClient = WebClientWraper.wrapClient(new DefaultHttpClient());

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("name", username));
			nameValuePairs.add(new BasicNameValuePair("pass1", password));
			nameValuePairs.add(new BasicNameValuePair("h", "4544745"));

			httppost.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.32 Safari/535.1");
			httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			httppost.setHeader("Referer", "https://www.253874.com/inyourname.asp");
			httppost.setHeader("Cookie", aspSessionId);

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse resp = httpClient.execute(host, httppost);
			httpClient.getConnectionManager().shutdown();

			Log.v(this.getClass().getName(), "Response Code: " + resp.getStatusLine().getStatusCode());

			cookies = resp.getHeaders("Set-Cookie");
			for (int i = 0; i < cookies.length; i++) {
				aspSessionId += cookies[i].getValue() + ";";
			}

			httpClient = WebClientWraper.wrapClient(new DefaultHttpClient());

			Uri postUri = Uri.parse(Constant.POST_INFO);

			HttpGet httpGet = new HttpGet(postUri.getPath());

			httpGet.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.32 Safari/535.1");
			httpGet.setHeader("Referer", Constant.POST_DZHLT);
			httpGet.setHeader("Host", Constant.HOST);
			httpGet.setHeader("Connection", "keep-alive");
			httpGet.setHeader("Cookie", aspSessionId);

			Log.v(this.getClass().getName(), "SET POST HEADER OK");
			HttpResponse listResponse = httpClient.execute(host, httpGet);
			Log.v(this.getClass().getName(), "GET POST EXECUTED");

			if (listResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream is1 = listResponse.getEntity().getContent();

				final char[] buffer1 = new char[0x10000];

				Reader in1 = new InputStreamReader(is1, "GBK");
				int read1;
				do {
					read1 = in1.read(buffer1, 0, buffer1.length);
					if (read1 > 0) {
						out1.append(buffer1, 0, read1);
					}
				} while (read1 >= 0);
				httpClient.getConnectionManager().shutdown();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return out1.toString();
	}
	
	public String getPost(String url) {
		HttpClient httpClient = new DefaultHttpClient();

		Uri uri = Uri.parse(url);

		HttpHost host = new HttpHost(uri.getHost(), 443, uri.getScheme());
		HttpGet get = new HttpGet(uri.getPath());
		StringBuilder out1 = new StringBuilder();
		try {
			httpClient = WebClientWraper.wrapClient(httpClient);
			get.setHeader("User-Agent",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.32 Safari/535.1");
			get.setHeader("Content-Type", "text/html");
			get.setHeader("Referer", Constant.POST_INFO);
			get.setHeader("Cookie", aspSessionId);
			HttpResponse response = httpClient.execute(host, get);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream is1 = response.getEntity().getContent();

				final char[] buffer1 = new char[0x10000];

				Reader in1 = new InputStreamReader(is1, "GBK");
				int read1;
				do {
					read1 = in1.read(buffer1, 0, buffer1.length);
					if (read1 > 0) {
						out1.append(buffer1, 0, read1);
					}
				} while (read1 >= 0);
				httpClient.getConnectionManager().shutdown();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return out1.toString();
	}
}