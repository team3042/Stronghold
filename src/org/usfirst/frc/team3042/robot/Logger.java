package org.usfirst.frc.team3042.robot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Logger extends FileIO {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd-hhmmss";
	
	String dir = "log/";
	
	boolean useConsole;
	boolean useFile;
	int level;
	String cls;
	
	public Logger(boolean useConsole, boolean useFile, int level) {
		if(useFile) {
			//Naming file with timestamp
			Date now = new Date();
			SimpleDateFormat fileTimeStamp = new SimpleDateFormat(DATE_FORMAT);
			fileTimeStamp.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
			String filename = "logs/" + fileTimeStamp.format(now);
			
			String path = "logs/";
			this.openFile(path, filename);
		}
		
		this.useConsole = useConsole;
		this.useFile = useFile;
		this.level = level;
	}
	
	public void log(String message, int level) {
		//Getting and adding class name to log
		cls = Thread.currentThread().getStackTrace()[2].getClassName();
		int i = cls.lastIndexOf(".") + 1;
		cls = cls.substring(i);
		
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
