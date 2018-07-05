package com.bridgeit.CIMap2;

import java.util.Date;

public class Answer {
	private int id;
	private String answer;
	private Date posteddate;

	public Answer() {
	}

	public Answer(int id, String answer, Date posteddate) {
		super();
		this.id = id;
		this.answer = answer;
		this.posteddate = posteddate;
	}

	public String toString() {
		return "id: " + id + " " + "answer: " + answer + " " + "posted date: " + posteddate;

	}

}
