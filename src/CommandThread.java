import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**Thread for sending the supplied command to the server at the other end of the supplied socket, and storing the incoming results.
 * @author Douglas Tanner (N01393164)
 */
public class CommandThread extends Thread {

	private Command command;
	private InetAddress address;
	private int port;
	private String results = "";
	private double initalTime;
	private double turnAroundTime;
	

	/**Creates a new command-running thread with the specified name.
	 * Sends the ID of the supplied command to the server connected with the supplied socket.
	 * @param name The name of the thread.
	 * @param command The command to be run.
	 * @param client The socket to output the command to.
	 */
	public CommandThread(String name, Command command, InetAddress address, int port) {
		super(name);
		this.command = command;
		this.address = address;
		this.port = port;
	}//end constructor method
	
	/**Sends the thread's command to the output stream of the client socket, and stores the results from the socket's input stream. */
	@Override
	public void run() {
		
		try (Socket client = new Socket(this.address, this.port)) {
			OutputStream outputToServer = client.getOutputStream();
			initalTime = System.currentTimeMillis();				//Turn around time starts now
			
			PrintWriter writeToServer = new PrintWriter(outputToServer, true);
			
			writeToServer.println(this.command.getID());
			
			BufferedReader outputReader = new BufferedReader( new InputStreamReader(client.getInputStream()) );	
			
			String outputLine;
			while((outputLine = outputReader.readLine()) != null)		//Combine all output from server
				this.results += (outputLine + "\n");
			
			client.close();
			turnAroundTime = System.currentTimeMillis() - initalTime;	//finish calculating turn around time
			
		} catch (IOException ioe) {
			System.err.println("I/O Exception occurred during thread " + this.getName());
			ioe.printStackTrace();
		}//end try-catch
		
	}//end method run
	
	/** Gets the stored results of the last run command.
	 * @return String containing the line-by-line results of the command run.
	 */
	public String getCommandResults() {
		return this.results;
	}//end method getCommandResults
	
	public double getTurnAroundTime() {
		return turnAroundTime;
	}
	
}//end class CommandThread