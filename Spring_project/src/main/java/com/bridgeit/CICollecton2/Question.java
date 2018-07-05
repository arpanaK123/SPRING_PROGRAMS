package com.bridgeit.CICollecton2;

import java.util.Iterator;
import java.util.List;

public class Question {
	private int no;
	private String question;
	private List<Answer> answer;

	public Question(int no, String question, List<Answer> answer) {
		this.no = no;
		this.question = question;
		this.answer = answer;

	}

	public void displayInformation() {
		System.out.println("no:" + no + " " + question);
		System.out.println("Answer are:");
		Iterator<Answer> itr = answer.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}
}
