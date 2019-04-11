package model.prank;

import SMTP.ISmtpClient;
import SMTP.SmtpClient;
import config.ConfigurationManager;
import model.mail.Group;
import model.mail.Victim;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class PrankGenerator {
	
	
	private final static int MAX_GROUP_SIZE = 3;
	private ConfigurationManager config;
	private ISmtpClient smtpClient;
	
	public PrankGenerator(ConfigurationManager cm){
		this.config = cm;
		smtpClient = new SmtpClient(config.getSMTPAddress(),config.getSMTPPort());
	}
	
	private Group[] createGroups(LinkedList<Victim> victims, int nbreGroups){
		
		if(victims.size()/MAX_GROUP_SIZE < nbreGroups){
			nbreGroups = victims.size()/MAX_GROUP_SIZE;
		}
		System.out.println("Nombre de groupes: " + nbreGroups);
		Group[] groups = new Group[nbreGroups];
		int i = 0;
		for(Victim v : victims){
			if(groups[i] == null){
				groups[i] = new Group();
			}
			groups[i].addVictim(v);
			i = (i+1) % nbreGroups;
		}
		
		return groups;
	}
	
	private LinkedList<Prank> createPranks(){
		LinkedList<Prank> pranks = new LinkedList<Prank>();
		LinkedList<Victim> victims = config.getVictims();
		LinkedList<String> messages = config.getMessages();
		int numberOfGroups = config.getNbreGroup();
		Group[] groups = createGroups(victims,numberOfGroups);
		
		int cnt = 0;
		Random rdm = new Random();
		for(Group group : groups){
			LinkedList<Victim> groupVictim = group.getVictims();
			Victim sender = groupVictim.removeFirst();
			pranks.add(new Prank(sender,groupVictim,messages.get(rdm.nextInt(messages.size()))));
			cnt++;
		}
		return pranks;
	}
	
	public void send() throws IOException {
		LinkedList<Prank> pranks = createPranks();
		System.out.println("Sending pranks");
		for(Prank prank : pranks){
			smtpClient.sendMail(prank.createMail());
		}
	}

}
