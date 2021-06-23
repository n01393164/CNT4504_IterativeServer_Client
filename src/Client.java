import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
	
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		Socket clientSocket = constructSocket();
		
		
		//While fetched command is not 'quit', 
//		do {
			//Print list of options (commands + quit)
			
			//Query for command
		
			//Query for number of client requests to spin up
		
			//Construct x number of threads
		
			//Construct timer
		
			//Start timer and launch threads
		
			//After last result, record/pause timer
		
			//Calculate and print average time for thread completion
//		} while(commandID != -1);
		
		scanner.close();
	}//end method main
	
	private static Socket constructSocket() {
		while(true) {
			
			try {
				return new Socket(getAddress(), getPort());
			} catch(Exception e) {
				System.out.print("Unable to create the requested socket.");
				
				while(true) {
					System.out.print("Try again? Y/N\n");
					
					switch(scanner.nextLine()) {
						case "y" :
						case "Y" : break;
						
						case "n" :
						case "N" : System.exit(0);
						default : continue;
					}//end switch
				}//end while
			}//end try-catch
			
		}//end while
		
	}//end method constructSocket

	/** Fetches the network address of the server to connect to from the user. 
	 * @return The InetAddress of the requested server */
	private static InetAddress getAddress() {
		
		while(true)
			try {
				System.out.println("Enter the address of the server you wish to connect to:");
				InetAddress requestedAddress = InetAddress.getByName(scanner.nextLine());
				
				if(requestedAddress.isLoopbackAddress())
					throw new UnknownHostException("Address is Loopback.");
				
				return requestedAddress; 
			} catch(UnknownHostException uhe) {
				System.out.println("The address entered is invalid. Please try again.\n");
			}//end try-catch
		
	}//end method getAddress
	
	/** Fetches the port number of the server to connect to from the user. 
	 * @return The port for the requested server */
	private static int getPort() {
		
		while(true)
			try {
				System.out.println("Enter the port number of the server you wish to connect to:");
				int serverPort = scanner.nextInt();
				
				return serverPort;
			} catch(InputMismatchException ime) {
				System.out.println("The port entered is invalid. Please try again.\n");
			}//end try-catch
		
	}//end method getPort

}//end class Client
