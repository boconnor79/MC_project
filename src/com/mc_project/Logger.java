package com.mc_project;

import java.io.*;
import java.util.Date;

public class Logger {
	Date date;

	public Logger() {
	}

	public void logCreator(String log) {
		try {
			File file = new File("C:\\Users\\Brian\\apache-tomcat-7.0.27\\log.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter logage = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(logage);
			bufferWritter.write(log);
			bufferWritter.close();
		} catch (Exception e) {
			System.out.println("Error writing to log");
		}
	}
}
