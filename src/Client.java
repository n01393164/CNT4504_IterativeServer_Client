import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		Socket clientSocket = constructSocket();
		int commandID = Command.QUIT.getID(); //Default: Quit
		
		//While fetched command is not 'quit', 
		do {
			commandID = getCommand();
			
			//If user quit, skip everything else
			if(commandID == Command.QUIT.getID())
				continue;
		
			//Query for number of client requests to spin up
			int numRequests = getNumRequests();
			//Construct x number of threads
		
			//Construct timer
		
			//Start timer and launch threads
		
			//After last result, record/pause timer
		
			//Calculate and print average time for thread completion
		} while(commandID != -1);
		
		scanner.close();
	}//end method main
	
	private static Socket constructSocket() {
		while(true) {
			
			try {
				return new Socket(getAddress(), getPort());
			} catch(Exception e) {
				System.out.println("Unable to create the requested socket.");
				e.printStackTrace();
				
				System.out.println("To try again, enter a key. To quit, type \"exit\".");
				scanner.nextLine();
				if(scanner.nextLine().toLowerCase().equals("exit"))
					System.exit(0);
			}//end try-catch
			
		}//end while
		
	}//end method constructSocket

	/** Fetches the network address of the server to connect to from the user. 
	 * @return The InetAddress of the requested server */
	private static InetAddress getAddress() {
		System.out.println("Enter the address of the server you wish to connect to:");
		
		while(true)
			try {
				InetAddress requestedAddress = InetAddress.getByName(scanner.nextLine());
				
//				if(requestedAddress.isLoopbackAddress())
//					throw new UnknownHostException("Address is Loopback.");
				
				return requestedAddress; 
			} catch(UnknownHostException uhe) {
				System.out.println("The address entered is invalid. Please try again:");
			}//end try-catch
		
	}//end method getAddress
	
	/** Fetches the port number of the server to connect to from the user. 
	 * @return The port for the requested server */
	private static int getPort() {
		System.out.println("Enter the port number of the server you wish to connect to:");
		
		while(true)
			try {
				return scanner.nextInt();
			} catch(InputMismatchException ime) {
				System.out.println("The port entered is invalid. Please try again:");
				scanner.nextLine();
			}//end try-catch
		
	}//end method getPort
	
	/** Fetches the command ID of the requested command to be run on the server. 
	 * If the user desires to quit instead, returns -1
	 * @return The ID for the requested command */
	private static int getCommand() {
		System.out.println("Enter the ID of the command you wish to run.");
		System.out.println("Valid commands:");
		for(Command c : Command.values())
			System.out.println("\t" + c.getName() + " | ID: " + c.getID());
		
		while(true)
			try {
				int commandID = scanner.nextInt();
				
				if(commandID < Command.QUIT.getID() || commandID > Command.values().length - 2)
					throw new IllegalArgumentException("Requested command ID does not exist.");
				
				return commandID;
			} catch(InputMismatchException | IllegalArgumentException e) {
				System.out.println("The command ID entered is invalid. Please try again:");
			}//end try-catch
		
	}//end method getCommand
	
	private static int getNumRequests() {
		return 0;
	}//end method getNumRequests

}//end class Client
