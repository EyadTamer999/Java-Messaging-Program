import java.io.*; 
import java.net.*; 

public class TCPClient {
	
	public static void main(String argv[]) throws Exception 
	{ 
		String sentence; 
		String modifiedSentence; 
		String connection;
		BufferedReader inFromUser = 
				new BufferedReader(new InputStreamReader(System.in)); 
		
		 do {

	          connection = (inFromUser.readLine()).toUpperCase();

	        }while(!connection.equals("CONNECT"));
		 
		Socket clientSocket = new Socket(InetAddress.getLocalHost(), 6789); 
		
		while(true) {
			DataOutputStream outToServer = 
				new DataOutputStream(clientSocket.getOutputStream()); 
		
			BufferedReader inFromServer = 
				new BufferedReader(new
						InputStreamReader(clientSocket.getInputStream())); 

			 if(inFromUser.ready()) {
		        	sentence= inFromUser.readLine();
		        	outToServer.writeBytes(sentence+'\n');
		        	if((sentence.toUpperCase()).equals("END")) {
		    			clientSocket.close();
		    			throw new Exception("Socket is closed");
		    		}
		        } 
			 if(inFromServer.ready()) {
				 modifiedSentence = inFromServer.readLine(); 

					System.out.println("FROM SERVER: " + modifiedSentence); 
			 }
		}
	} 
} 



