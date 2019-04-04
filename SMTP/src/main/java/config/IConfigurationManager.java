package config;

import model.mail.Victim;

import java.util.LinkedList;

public interface IConfigurationManager {
	public String getSMTPAddress();
	public int getSMTPPort();
	public LinkedList<Victim> getVictims();
	public LinkedList<String> getMessages();
	
	
}
