package org.usfirst.frc.team3042.robot;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class FileIO {
	
	PrintStream writer;
	
	boolean fileExists;
	File file;

	public void openFile(String filename) {
		String url = "file:///" + filename;
		file = new File(url);
		
		try {
			//Checking if file exists and deleting it and recreating it to clear if it does
			fileExists = file.createNewFile();
			if(!fileExists) {
				file.delete();
				file.createNewFile();
			}
			writer = new PrintStream(file);
		} catch (IOException e) {}
	}
	
	public void writeToFile(String content) {
		writer.println(content);
		writer.flush();
	}
	
}
