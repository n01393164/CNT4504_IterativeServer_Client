/**Enumeration of all available commands to send to the server.
 * @author Douglas Tanner (N01393164)
 */
public enum Command {
	QUIT(-1, null, "Quit"),
	DATE_AND_TIME(0, "date", "Date and Time"), 
	UPTIME(1, "uptime", "Uptime"), 
	MEMORY_USAGE(2, "free", "Memory Usage"), 
	NETSTAT(3, "netstat", "Netstat"), 
	CURRENT_USERS(4, "w", "Current Users"), 
	RUNNING_PROCESSES(5, "ps", "Running Processes");
	
	private final int id;
	private final String name;
	private final String command;
	
	/**Associates a given command enumeration with an ID, name, and unix command.
	 * @param id The ID of the command
	 * @param command String containing the unix command associated.
	 * @param name The printed name of the command.
	 */
	private Command(int id, String command, String name) {
		this.id = id;
		this.command = command;
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
	
	/**Gets the unix command String
	 * @return The command String
	 */
	public String getCommand() {
		return this.command;
	}//end method getCommand
	
}//end enum Command