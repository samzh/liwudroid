package com.samzh.liwu.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PostHandler extends DefaultHandler {

	private StringBuilder builder;

	private List<Map<String, String>> postList = new ArrayList<Map<String, String>>();

	private Map<String, String> currentMap;
	
	public List<Map<String, String>> getPostList() {
		return this.postList;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);
		if (currentMap != null) {
			if (localName.equalsIgnoreCase("href")) {
				currentMap.put("href", builder.toString());
			} else if (localName.equalsIgnoreCase("title")) {
				currentMap.put("title", builder.toString());
			} else if (localName.equalsIgnoreCase("a")) {
				postList.add(currentMap);
			}
			builder.setLength(0);
		}
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		builder = new StringBuilder();

	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		if (localName.equalsIgnoreCase("a")) {
			currentMap = new HashMap<String, String>();
		}
	}

}
