import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

/**Client program for CNT4504 Iterative Socket Server assignment
 * @author Douglas Tanner (N0139164), William Hiromoto (N01452026)
 */
public class Client {
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		InetAddress serverAddress = getAddress();
		int serverPort = getPort();
		int numRequests;
		double totalTurnAroundTime = 0;
		Command command;
		CommandThread[] commandThreads;
		 
		while(true) {
			command = getCommand();
			//If user quit, exit.
			if(command == Command.QUIT)
				break;
			else if(command == Command.TESTINGRUNS)
				try {
					testingPhase(serverAddress, serverPort);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
			numRequests = getNumRequests();
			
			//Construct the threads
			commandThreads = new CommandThread[numRequests];
			for(int i = 0; i < numRequests; ++i)
				commandThreads[i] = new CommandThread((command + " " + i), command, serverAddress, serverPort);
			
			//Record time, launch threads and print results, record elapsed time
			//long initialTime = System.currentTimeMillis();
			for(CommandThread thread : commandThreads)
				thread.start();
			
			totalTurnAroundTime = 0;						//make sure the totalTurnAroundTime is 0
			for(CommandThread thread : commandThreads) {
				try {
					thread.join();							//end the threads
				} catch (InterruptedException e) {
					System.out.println("Could not rejoin thread");
					e.printStackTrace();
				}
				System.out.print(thread.getCommandResults());							//Print Thread Results
				System.out.println("[test] Turn Around Time: " + thread.getTurnAroundTime() + " ms.");	//Show the correct turn Around Time
				totalTurnAroundTime += thread.getTurnAroundTime();						//Get the totalTurnAroudnTime
				}
			
			System.out.println("Total Turn Around Time: " + totalTurnAroundTime);
			System.out.println("Average Turn Around Time: " + (totalTurnAroundTime / numRequests) + " ms.");
		}//end while
		
		//Shutdown Server from client
		System.out.println("Shutdown Server? (y/n)");
		if (scanner.next() == "y") {
			CommandThread shutdown = new CommandThread("SHUTDOWN", Command.SHUTDOWN, serverAddress, serverPort);
			shutdown.start();
			try {
				shutdown.join();
			} catch (InterruptedException e) {
				System.out.println("Could not rejoin thread");
				e.printStackTrace();
			}
		}	
		scanner.close();
	}//end method main
	
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
			//System.out.println("\t" + c.getName() + " | ID: " + c.getID());
			System.out.printf("\t %-17s | ID: %d\n", c.getName(), c.getID());
		System.out.printf("\n");
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
		System.out.println("Suggested options: 1, 5, 10, 15, 20, 25, 100");
		
		while(true)
			try {
				return scanner.nextInt();
			} catch(InputMismatchException ime) {
				System.out.println("The number of requests entered is invalid. Please try again:");
				scanner.nextLine();
			}//end try-catch
		
	}//end method getNumRequests
	
	private static void testingPhase(InetAddress serverAddress, int serverPort) throws IOException {
		//modified parts
		int[] testingRuns = {1, 5, 10, 15, 20, 25, 100};
		Command[] commands = Command.values();
				
		File output = new File("Results.csv");
		FileWriter pen = new FileWriter("Results.csv");
		
		//run through commands
		for (int k = 3; k < 9; k++) {
			//create headers for command
			pen.write(commands[k].name() + "\nRun, ");
			for (int i = 1; i <= 100; i++) {
				pen.write(i + ", ");
			}
			pen.write("Total, Average\n");
			
			//run through testing runs
			for (int i = 0; i < testingRuns.length; i++) {
				pen.write(i + ", "); 		//write the row name
				switch(testingRuns[i]) { 	//repeat runs x amount of times per value
				case 1:
					for (int j = 0; j < 100; j++) {
						threadMachine(serverAddress, serverPort, commands[k], testingRuns[i], pen);
					}
					break;
				case 5:
					for (int j = 0; j < 20; j++) {
						threadMachine(serverAddress, serverPort, commands[k], testingRuns[i], pen);
					}
					break;
				case 10:
					for (int j = 0; j < 10; j++) {
						threadMachine(serverAddress, serverPort, commands[k], testingRuns[i], pen);
					}
					break;
				case 20:
					for (int j = 0; j < 5; j++) {
						threadMachine(serverAddress, serverPort, commands[k], testingRuns[i], pen);
					}
					break;
				case 25:
					for (int j = 0; j < 4; j++) {
						threadMachine(serverAddress, serverPort, commands[k], testingRuns[i], pen);
					}
					break;
				case 100:
						threadMachine(serverAddress, serverPort, commands[k], testingRuns[i], pen);
					break;
				}//end switch
				pen.write("Done\n");
			}//end for loop
			pen.write("Done\n");
		}//end for loop		
	}//end testingPhase()
	private static void threadMachine(InetAddress serverAddress, int serverPort, Command command, int numThread, FileWriter pen) throws IOException {
		//from original
		CommandThread[] commandThreads;
		//double totalTurnAroundTime = 0;
		
		//Construct the threads
		commandThreads = new CommandThread[numThread];
		for(int j = 0; j < numThread; ++j)
			commandThreads[j] = new CommandThread((command + " " + j), command, serverAddress, serverPort);
		
		//Record time, launch threads and print results, record elapsed time
		//long initialTime = System.currentTimeMillis();
		for(CommandThread thread : commandThreads)
			thread.start();
		
		//totalTurnAroundTime = 0;						//make sure the totalTurnAroundTime is 0
		for(CommandThread thread : commandThreads) {
			try {
				thread.join();							//end the threads
			} catch (InterruptedException e) {
				System.out.println("Could not rejoin thread");
				e.printStackTrace();
			}
			pen.write(thread.getTurnAroundTime() + ", ");							//Write turn around time to file
			
			//System.out.print(thread.getCommandResults());							//Print Thread Results
			//System.out.println("[test] Turn Around Time: " + thread.getTurnAroundTime() + " ms.");	//Show the correct turn Around Time
			//totalTurnAroundTime += thread.getTurnAroundTime();						//Get the totalTurnAroudnTime
			}
		//pen.write(totalTurnAroundTime);
		//System.out.println("Total Turn Around Time: " + totalTurnAroundTime);
		//System.out.println("Average Turn Around Time: " + (totalTurnAroundTime / numThread) + " ms.");
	}

}//end class Client