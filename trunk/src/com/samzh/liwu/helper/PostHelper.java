package com.samzh.liwu.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PostHelper {
	public static String[] processList(String contentString) {
		Pattern listPattern = Pattern.compile("◆ <a href='.+?' title='.+?'.*>.*</a><br>");

		Matcher matches = listPattern.matcher(contentString);

		while (matches.find()) {
			String content = matches.group();

			String[] list = content.split("<br>");

			return list;
		}

		return null;
	}

	public static Map<String, String> processAPost(String source) {

		Map<String, String> map = new HashMap<String, String>();

		try {
			source = source.substring(source.indexOf("◆ ") + 1);
			Document doc = Jsoup.parseBodyFragment(source);
			Elements elements = doc.getElementsByTag("a");
			for (Element element : elements) {
				map.put("tagName", element.tagName());
				map.put("title", element.attr("title"));
				map.put("href", element.attr("href"));
				map.put("text", element.text());
			}
			return map;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
