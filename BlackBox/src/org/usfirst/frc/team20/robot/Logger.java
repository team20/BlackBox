package org.usfirst.frc.team20.robot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Logger extends Thread {
	public static final String COMPUTER_IP = "172.22.11.1";
	public static final int COMPUTER_PORT = 50000;
	boolean logging = false;
	private ArrayList<Loggable> stuffToLog = new ArrayList<>();
	String log = "";
	static DatagramSocket clientSocket;
	
	@Override
	public void run(){
		logging = true;
		while (logging) {
			this.log();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void startSocket(){
		try{
			clientSocket = new DatagramSocket(COMPUTER_PORT);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Iterates through all registered stuffToLog and calls log on them.
	 */
	public void log() {
		log = "";
		for (int i = 0; i < stuffToLog.size(); i++) {
			log += stuffToLog.get(i).log();
			log += "\n";
		}

		// Send log to DS
		try {
			sendLog();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void register(Loggable thingToLog) {
		if (!logging) {
			System.out.println("                             thingToLog: " + thingToLog);
			stuffToLog.add(thingToLog);	
		}	
	}

	/**
	 * Open a socket with the driver station
	 * Get bytes for the string 
	 * Send the bytes
	 */
	public void sendLog() throws IOException {
		try {
			InetAddress IPAddress = InetAddress.getByName(COMPUTER_IP);
			byte[] data = log.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, COMPUTER_PORT);
			clientSocket.send(sendPacket);
			System.out.println("Sent Packet!!!");
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void closeSocket() throws IOException{
		try {
			logging = false;
			InetAddress IPAddress = InetAddress.getByName(COMPUTER_IP);
			log = "disabled";
			byte[] data = log.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, COMPUTER_PORT);
			clientSocket.send(sendPacket);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		clientSocket.close();
	}
}