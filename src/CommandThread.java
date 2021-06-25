import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**Thread for sending the supplied command to the server at the other end of the supplied socket, and storing the incoming results.
 * @author Douglas Tanner (N01393164)
 */
public class CommandThread extends Thread {

	private Socket connectedClient;
	private int command;
	private String results = "";
	
	/**Creates a new command-running thread with the specified name.
	 * Sends the supplied command to the server connected with the supplied socket.
	 * @param name The name of the thread.
	 * @param command The command to be run.
	 * @param client The socket to output the command to.
	 */
	public CommandThread(String name, int command, Socket client) {
		super(name);
		this.command = command;
		this.connectedClient = client;
	}//end constructor method
	
	/**Sends the thread's command to the output stream of the client socket, and stores the results from the socket's input stream. */
	@Override
	public void run() {
		
		try {
			new PrintWriter(connectedClient.getOutputStream(), true).println(command);
			BufferedReader outputReader = new BufferedReader( new InputStreamReader(connectedClient.getInputStream()) );
			
			String outputLine;
			while((outputLine = outputReader.readLine()) != null)
				this.results += (outputLine + "\n");
			
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
	
}//end class CommandThread