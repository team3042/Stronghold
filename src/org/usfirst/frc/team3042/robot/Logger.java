package org.usfirst.frc.team3042.robot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Logger extends FileIO {
	
	private static final String FILE_DATE_FORMAT = "yyyy-MM-dd-hhmmss";

	boolean useConsole;
	boolean useFile;
	int level;
	String cls;

	Timer timer = new Timer();
	
	public Logger(boolean useConsole, boolean useFile, int level) {
		if(useFile) {
			//Naming file with timestamp
			Date now = new Date();
			SimpleDateFormat fileTimeStamp = new SimpleDateFormat(FILE_DATE_FORMAT);
			fileTimeStamp.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
			String filename = "logs/" + fileTimeStamp.format(now);
			
			String path = "logs/";
			this.openFile(path, filename);
		}
		
		this.useConsole = useConsole;
		this.useFile = useFile;
		this.level = level;
		
		timer.start();
	}
	
	public void log(String message, int level) {
		
		this.level = (int) SmartDashboard.getNumber("Logger Level");
		
		if(level <= this.level) {
			//Getting and adding class name to log
			cls = Thread.currentThread().getStackTrace()[2].getClassName();
			int i = cls.lastIndexOf(".") + 1;
			cls = cls.substring(i);
			
			message = "[" + cls + "] " + message;
			
			//Adding timestamp to log
			//String time = String.format("%10.3",timer.get());
			
			message = timer.get() + "\t" + message;
			
			if(useFile) {
				this.writeToFile(message);
			}
			if(useConsole) {
				System.out.println(message);
			}
		}
	}
	
}
