package com.bridgeit.Utility;

public class BalanceResponce {
	private int Balance;

	

	public int getBalance() {
		return Balance;
	}

	public void setBalance(int balance) {
		Balance = balance;
	}

	@Override
	public String toString() {
		return "BalanceResponse Balance=" + Balance + "]";
	}
}
