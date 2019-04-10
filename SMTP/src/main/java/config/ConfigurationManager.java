package config;

import lombok.Getter;
import model.mail.Victim;

import java.io.*;
import java.util.LinkedList;
import java.util.Properties;

public class ConfigurationManager {

	@Getter
	private String SMTPAddress;
	
	@Getter
	private int SMTPPort;
	
	@Getter
	private LinkedList<Victim> victims;
	
	@Getter
	private LinkedList<String> messages;
	
	@Getter
	private int nbreGroup;
	
	public ConfigurationManager(String fileName) throws IOException {
		loadProperties(fileName);
	}
	
	
	private void loadProperties(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);
		Properties properties = new Properties();
		properties.load(fis);
		
		SMTPAddress = properties.getProperty("SMTPAddress");
		SMTPPort = Integer.parseInt(properties.getProperty("SMTPPort"));
		nbreGroup = Integer.parseInt(properties.getProperty("nbreGroup"));
		victims = loadAddressFromFile(properties.getProperty("victimsFile"));
		messages = loadMessageFromFile(properties.getProperty("messagesFile"));
	}
	
	private LinkedList<Victim> loadAddressFromFile(String victimsFile) throws IOException {
		
		LinkedList<Victim> listVictims = new LinkedList<Victim>();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(victimsFile)));
		
		String line;
		while((line = reader.readLine()) != null){
			listVictims.add(new Victim(line));
		}
		
		return listVictims;
	
	}
	
	private LinkedList<String> loadMessageFromFile(String messageFile) throws IOException {
		LinkedList<String> listMessages = new LinkedList<String>();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(messageFile)));
		String line;
		
		while((line = reader.readLine()) != null){
			String message = line;
			while ((line = reader.readLine()) != null && !line.equals("--")){
				message += line + "\r\n";
			}
			listMessages.add(message);
		}
		
		return listMessages;
	
	}
}
