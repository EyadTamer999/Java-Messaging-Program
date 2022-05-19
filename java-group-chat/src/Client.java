import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String Username;
	
	public Client(Socket socket, String Username) {
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.Username = Username;
		} catch(IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
	}
	
	public void sendMessage() {
		try {
			bufferedWriter.write(Username);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			
			Scanner sc = new Scanner(System.in);
			while(socket.isConnected()) {
				String messageToSend = sc.nextLine();
				bufferedWriter.write(Username + ": " + messageToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
		} catch(IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
			}
	}
	
	public void listenForMessage() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String msgFromGC;
				
				while(socket.isConnected()) {
					try {
						msgFromGC = bufferedReader.readLine();
						System.out.println(msgFromGC);
					} catch(IOException e) {
						closeEverything(socket, bufferedReader, bufferedWriter);
					}
				}
			}
			
		}).start();
	}
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader,BufferedWriter bufferedWriter ) {
		try {
			if(bufferedReader != null)
				bufferedReader.close();
			if(bufferedWriter != null) 
				bufferedWriter.close();
			if(socket != null)
				socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your Username!");
		String username = sc.nextLine();
		Socket socket = new Socket(InetAddress.getLocalHost(), 6789);
		Client client = new Client(socket,username);
		client.listenForMessage();
		client.sendMessage();
		
	}
	
}
