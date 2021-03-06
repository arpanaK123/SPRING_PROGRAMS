package com.bridgeit.CICollection;

import java.util.Iterator;
import java.util.List;

public class Question {
	private int No;
	private String question;
	private List<String> answer;

	public Question(int No, String question, List<String> answer) {
		this.No = No;
		this.question = question;
		this.answer = answer;

	}

	public void displayInformation() {
		System.out.println("No: " + No + ". question: " + question);
		System.out.println("Answers are: ");
		Iterator<String> itr = answer.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}
}
