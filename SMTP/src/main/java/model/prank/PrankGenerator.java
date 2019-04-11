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

	/**
	 * Create groups of victims for the pranks
	 * @param victims The victims of the prank
	 * @param nbreGroups The number of groups
	 * @return The victims groups
	 */
	private Group[] createGroups(LinkedList<Victim> victims, int nbreGroups){

		// adjust the size of the groups if there is not enough victims
		if(victims.size()/MAX_GROUP_SIZE < nbreGroups){
			nbreGroups = victims.size()/MAX_GROUP_SIZE;
		}
		Group[] groups = new Group[nbreGroups];
		int i = 0;

		//add victims to the different groups evenly
		for(Victim v : victims){
			if(groups[i] == null){
				groups[i] = new Group();
			}
			groups[i].addVictim(v);
			i = (i+1) % nbreGroups;
		}
		
		return groups;
	}

	/**
	 * Create pranks
	 * @return A list of pranks to be sended
	 */
	private LinkedList<Prank> createPranks(){
		LinkedList<Prank> pranks = new LinkedList<Prank>();
		//create the groups of victims
		LinkedList<Victim> victims = config.getVictims();
		LinkedList<String> messages = config.getMessages();
		int numberOfGroups = config.getNbreGroup();
		Group[] groups = createGroups(victims,numberOfGroups);
		
		int cnt = 0;
		Random rdm = new Random();

		//create pranks and link them to each group of victims
		for(Group group : groups){
			LinkedList<Victim> groupVictim = group.getVictims();
			Victim sender = groupVictim.removeFirst();
			pranks.add(new Prank(sender,groupVictim,messages.get(rdm.nextInt(messages.size()))));
			cnt++;
		}
		return pranks;
	}

	/**
	 * Create the pranks, the groups and actually send the mails
	 * @throws IOException
	 */
	public void send() throws IOException {
		LinkedList<Prank> pranks = createPranks();
		System.out.println("Sending pranks");
		for(Prank prank : pranks){
			smtpClient.sendMail(prank.createMail());
		}
	}

}
