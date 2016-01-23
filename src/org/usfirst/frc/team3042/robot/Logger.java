package org.usfirst.frc.team3042.robot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger extends FileIO {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd-hhmmss";
	
	boolean useConsole;
	boolean useFile;
	int level;
	String cls;
	
	public Logger(boolean useConsole, boolean useFile, int level) {
		//Naming file with timestamp
		Date now = new Date();
		SimpleDateFormat fileTimeStamp = new SimpleDateFormat(DATE_FORMAT);
		String filename = "log/" + fileTimeStamp.format(now);
		this.openFile(filename);
		
		this.useConsole = useConsole;
		this.useFile = useFile;
		this.level = level;
	}
	
	public void log(String message, int level) {
		cls = Thread.currentThread().getStackTrace()[1].getClassName();
		
		message = "[" + cls + "] " + message;
		
		if(level == this.level) {
			if(useFile) {
				this.writeToFile(message);
			}
			if(useConsole) {
				System.out.println(message);
			}
		}
	}
	
}
