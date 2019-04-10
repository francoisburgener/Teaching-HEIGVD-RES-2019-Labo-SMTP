import config.ConfigurationManager;
import model.prank.PrankGenerator;

import java.io.File;
import java.io.IOException;

public class SMTPMain {
	public static void main(String...args) throws IOException {
		
		PrankGenerator pg = new PrankGenerator(new ConfigurationManager("./config/config.properties"));
		pg.send();
		System.out.println("Finish");
		
		/*if(args.length == 1){
			File file = new File(args[0]);
			if(file.exists()){
				PrankGenerator pg = new PrankGenerator(new ConfigurationManager("../../../config/cinfig.properties");
			}else {
				System.out.println("Please check if your file exist");
			}
			
		}else{
			System.out.println("Too many arguments !");
		}*/
	}
}
