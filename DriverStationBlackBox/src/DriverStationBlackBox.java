import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

public class DriverStationBlackBox {
	static DatagramSocket clientSocket;
	static int matches = 1;
	static PrintWriter print;
	static Date date = new Date();

	public static void main(String[] args) throws IOException {
		try {
			clientSocket = new DatagramSocket(50000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		changeStream();
		while (true) {
	    	String toSave = getLog();
			if(toSave.contains("disabled")){
				matches++;
				changeStream();
			} else {
				print.format("%20s%2s%2s%n", date.toString(), " - ", toSave);				
			}
		}
	}

	public static void changeStream() throws FileNotFoundException{
		String fileName = "logData" + matches + "-" + date.toString() + ".txt";
		System.out.println("fileName: " + fileName);
	    PrintWriter file = new PrintWriter(fileName);
		print = file;
		System.out.println("new print stream created: " + matches);
	}
	
	public static String getLog() throws IOException {
		String socketString = "-1";
		try {
			byte[] data = new byte[2000];
			DatagramPacket receivePacket = new DatagramPacket(data, data.length);
			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			socketString = modifiedSentence;
		} catch (SocketException e) {
			System.out.println("Socket error: " + e.toString());
		}
		return socketString;
	}
}
