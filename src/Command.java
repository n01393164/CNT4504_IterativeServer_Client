
public enum Command {
	QUIT(-1, null, "Quit"),
	DATE_AND_TIME(0, "free", "Date and Time"), 
	UPTIME(1, "uptime", "Uptime"), 
	MEMORY_USAGE(2, "free", "Memory Usage"), 
	NETSTAT(3, "netstat", "Netstat"), 
	CURRENT_USERS(4, "w", "Current Users"), 
	RUNNING_PROCESSES(5, "ps", "Running Processes");
	
	private final int id;
	private final String name;
	private final String command;
	
	private Command(int id, String command, String name) {
		this.id = id;
		this.command = command;
		this.name = name;
	}//end constructor method
	
	public int getID() {
		return this.id;
	}//end method getID
	
	public String getName() {
		return this.name;
	}//end method getName
	
	public String getCommand() {
		return this.command;
	}//end method getCommand
	
}//end enum Command
