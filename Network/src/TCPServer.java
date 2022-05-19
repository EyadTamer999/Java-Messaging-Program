import java.io.*; 
import java.net.*; 


public class TCPServer {
	
	public static void main(String argv[]) throws Exception 
	{ 
		String clientSentence; 
		String capitalizedSentence;
		String Sentence;
		int connectionCount = 0;

		ServerSocket welcomeSocket = new ServerSocket(6789); 

		
		Socket connectionSocket = welcomeSocket.accept();
		
		while(true) { 
			
			if(connectionCount == 0) {
				System.out.println("Connected");
				connectionCount++;
			}

			BufferedReader inFromUser = 
	                new BufferedReader(new InputStreamReader(System.in));
			BufferedReader inFromClient = 
					new BufferedReader(new
							InputStreamReader(connectionSocket.getInputStream())); 
			
			
			DataOutputStream  outToClient = 
					new DataOutputStream(connectionSocket.getOutputStream()); 

			if(inFromUser.ready()) {
				Sentence = inFromUser.readLine();
				outToClient.writeBytes(Sentence+  '\n');
				
			}
			if(inFromClient.ready()) {
			clientSentence = inFromClient.readLine(); 
			if(clientSentence.equals("END")) {
				welcomeSocket.close();
				connectionSocket.close();
				throw new Exception("Socket is closed");
			}
			System.out.println("FROM CLIENT: " + clientSentence);
			}
		} 
	} 
} 




