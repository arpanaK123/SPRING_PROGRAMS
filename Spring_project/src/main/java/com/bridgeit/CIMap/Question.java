package com.bridgeit.CIMap;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Question {
	private int id;
	private String name;
	private Map<String, String> answer;

	public Question(int id, String name,Map<String,String> answer) {
		this.id = id;
		this.name = name;
		this.answer = answer;

	}

	public Question() {
	}
	

	public void duisplayInformation() {
		System.out.println("Question id:" + id);
		System.out.println("Question name:" + name);
		System.out.println("Answer are");
		Set<Entry<String, String>> set = answer.entrySet();
		Iterator<Entry<String, String>> itr = set.iterator();

		while (itr.hasNext()) {
			Entry<String, String> entry = itr.next();
			System.out.println("Answer:" + entry.getKey() + " Posted By:" + entry.getValue());
		}

	}

}
