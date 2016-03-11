package org.usfirst.frc.team3042.robot;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class FileIO {
	
	PrintStream writer;
	Scanner scanner;
	
	boolean fileExists;

	public void openFile(String path, String filename) {
		File dir = new File(path);
		String url = path + filename;
		File file = new File(url);
		
		dir.mkdir();
		
		try {
			//Checking if file exists and deleting it and recreating it to clear if it does
			fileExists = file.createNewFile();
			if (!fileExists) {
				file.delete();
				file.createNewFile();
			}
			
			writer = new PrintStream(file);
			
			scanner = new Scanner(file);
			scanner.useDelimiter(",|\\n");
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void writeToFile(String content) {
		writer.println(content);
		writer.flush();
	}
	
	public double readNextDouble() {
		return scanner.nextDouble();
	}
	
	public boolean hasNextDouble() {
		return scanner.hasNextDouble();
	}
	
	public void closeFile() {
		writer.close();
	}
}
