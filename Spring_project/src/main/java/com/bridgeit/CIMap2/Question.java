package com.bridgeit.CIMap2;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Question {
	private int id;
	private String name;
	private Map<Answer, User> answer;

	public Question() {
	}

	public Question(int id, String name, Map<Answer, User> answer) {
		this.id = id;
		this.name = name;
		this.answer = answer;

	}

	public void displayInformation() {
		System.out.println("ques id:" + id);
		System.out.println("ques name: " + name);
		System.out.println("Answers are:");
		Set<Entry<Answer, User>> set = answer.entrySet();
		Iterator<Entry<Answer, User>> itr = set.iterator();

		while (itr.hasNext()) {
			Entry<Answer, User> entry = itr.next();

			Answer ans = entry.getKey();
			User user = entry.getValue();
			System.out.println("Answer Information:");
			System.out.println(ans);
			System.out.println("Posted By:");
			System.out.println(user);
		}

	}
}
