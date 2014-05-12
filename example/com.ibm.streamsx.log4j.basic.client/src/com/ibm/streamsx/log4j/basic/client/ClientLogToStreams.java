package com.ibm.streamsx.log4j.basic.client;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.NDC;
public class ClientLogToStreams {
	private static Logger logger = Logger.getLogger(ClientLogToStreams.class.getPackage().getName());
	private String userName = null;
	private double balance;

	/** Creates a new instance of AdvancedLogging */
	public ClientLogToStreams(String user) {
		this.userName = user;
	}

	/**
	 * Deposit some amount
	 */
	public void deposit(double amount) {

		NDC.push(String.format("Deposit;%f", amount));		
		balance += amount;
		logger.info("Deposited " + amount + " new balance: " + balance);
		NDC.pop();
	}

	/**
	 * withdraw some amount
	 */
	public void withdraw(double amount) {
		if (balance >= amount) {
			balance -= amount;
			NDC.push(String.format("BALANCE:%f", balance));
			logger.info("Withdrawn " + amount + " new balance: " + balance);
		} else {
			NDC.push(String.format("BALANCE:%f", balance));			
			logger.error("Failed to withdraw: balance: " + balance
					+ " attempted withdraw: " + amount);
		}
		NDC.pop();
	}

	public static void main(String args[]) throws InterruptedException {
		ClientLogToStreams demo = new ClientLogToStreams("value passed via NDC");
		MDC.put("clientIP", "this the client IP or anything else");
		for (int k=0; k!=100; k++) {
			demo.deposit(Math.random() * 50 + 1);
			Thread.sleep(500);
			demo.withdraw(Math.random() * 50 + 1);			
			Thread.sleep(500);			
		}
	}
}
