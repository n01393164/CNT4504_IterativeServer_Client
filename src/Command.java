/**Enumeration of all available commands to send to the server.
 * @author Douglas Tanner (N01393164), William Hiromoto (N01452026)
 */
public enum Command {
	TESTINGRUNS(-3, "Test Mode"),
	SHUTDOWN(-2, "Shutdown"),
	QUIT(-1, "Quit"),
	DATE_AND_TIME(0, "Date and Time"), 
	UPTIME(1, "Uptime"), 
	MEMORY_USAGE(2, "Memory Usage"), 
	NETSTAT(3, "Netstat"), 
	CURRENT_USERS(4, "Current Users"), 
	RUNNING_PROCESSES(5, "Running Processes");
	
	private final int id;
	private final String name;
	
	/**Associates a given command enumeration with an ID, name, and unix command.
	 * @param id The ID of the command
	 * @param name The printed name of the command.
	 */
	private Command(int id, String name) {
		this.id = id;
		this.name = name;
	}//end constructor method
	
	/**Gets the command's ID
	 * @return The command ID
	 */
	public int getID() {
		return this.id;
	}//end method getID
	
	/**Gets the command's name
	 * @return The command name
	 */
	public String getName() {
		return this.name;
	}//end method getName
	
}//end enum Command