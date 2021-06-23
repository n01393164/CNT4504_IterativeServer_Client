import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		Socket clientSocket = constructSocket();
		
		int numRequests;
		Command command;
		CommandThread[] commandThreads;
		 
		while(true) {
			command = getCommand();
			//If user quit, exit.
			if(command == Command.QUIT)
				break;
		
			numRequests = getNumRequests();
			
			//Construct the threads
			commandThreads = new CommandThread[numRequests];
			for(int i = 0; i < numRequests; ++i)
				commandThreads[i] = new CommandThread((command + " thread " + i), command.getCommand(), clientSocket);
			
			//Record time, launch threads and print results, record elapsed time
			long initialTime = System.currentTimeMillis();
			for(CommandThread thread : commandThreads)
				thread.start();
			
			for(CommandThread thread : commandThreads)
				System.out.print(thread.getCommandResults());
			long elapsedTime = System.currentTimeMillis() - initialTime;
			
			System.out.println("Total elapsed time: " + elapsedTime + " ms.");
			System.out.println("Average time per thread: " + ((double)elapsedTime / numRequests) + "ms.");
		}//end while
		
		System.out.println("Closing client.");
		scanner.close();
	}//end method main
	
	/**Constructs a valid client Socket, connected to the SocketServer at a requested IP address and port number.
	 * @return The Socket constructed from the requested port and address, if connection is successful.
	 */
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
	 * @return The InetAddress of the requested server. */
	private static InetAddress getAddress() {
		System.out.println("Enter the address of the server you wish to connect to:");
		
		while(true)
			try {
				InetAddress requestedAddress = InetAddress.getByName(scanner.nextLine());
				
				if(requestedAddress.isLoopbackAddress())
					throw new UnknownHostException("Address is Loopback.");
				
				return requestedAddress; 
			} catch(UnknownHostException uhe) {
				System.out.println("The address entered is invalid. Please try again:");
			}//end try-catch
		
	}//end method getAddress
	
	/** Fetches the port number of the server to connect to from the user. 
	 * @return The port for the requested server. */
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
	
	/** Fetches the requested command to be run on the server from the user. 
	 * If the user desires to quit instead, returns -1.
	 * @return The ID for the requested command. */
	private static Command getCommand() {
		System.out.println("Enter the ID of the command you wish to run.");
		System.out.println("Valid commands:");
		for(Command c : Command.values())
			System.out.println("\t" + c.getName() + " | ID: " + c.getID());
		
		while(true)
			try {
				int commandID = scanner.nextInt();
				
				for(Command c : Command.values())
					if(c.getID() == commandID)
						return c;
				
				throw new IllegalArgumentException("Requested command ID does not exist.");
			} catch(InputMismatchException | IllegalArgumentException e) {
				System.out.println("The command ID entered is invalid. Please try again:");
				scanner.nextLine();
			}//end try-catch
		
	}//end method getCommand
	
	/** Fetches the number of requests to the server to make. 
	 * Suggests to the user options of 1, 5, 10, 15, 20, and 25.
	 * @return The number of requests desired. */
	private static int getNumRequests() {
		System.out.println("Enter the number of requests to the server you want to make:");
		System.out.println("Suggested options: 1, 5, 10, 15, 20, 25");
		
		while(true)
			try {
				return scanner.nextInt();
			} catch(InputMismatchException ime) {
				System.out.println("The number of requests entered is invalid. Please try again:");
				scanner.nextLine();
			}//end try-catch
		
	}//end method getNumRequests

}//end class Client
