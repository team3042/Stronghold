package org.usfirst.frc.team3042.robot;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class FileIO {
	
	PrintStream writer;
	
	boolean fileExists;

	public void openFile(String path, String filename) {
		path = "/home/lvuser/" + path;
		File dir = new File(path);
		String url = "/home/lvuser/" + filename;
		System.out.println(url);
		File file = new File(url);
		
		dir.mkdir();
		
		try {
			//Checking if file exists and deleting it and recreating it to clear if it does
			fileExists = file.createNewFile();
			if(!fileExists) {
				file.delete();
				file.createNewFile();
			}
			
			writer = new PrintStream(file);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void writeToFile(String content) {
		writer.println(content);
		writer.flush();
	}
	
}
