package Assign3;

import java.util.Scanner;

public class ConnectXPlatform {
	
	    public static void main(String[] args) {
	        DigitalService streamingService = new StreamingService();
	        DigitalService cloudStorageService = new CloudStorageService();
	        Scanner sc = new Scanner(System.in);
	        System.out.println("Enter The choice");
	        System.out.println("1.Streaming Service");
	        System.out.println("2.Cloud Stroage Service");
	        int choice= sc.nextInt();
	        if(choice==1) {
	        	DigitalService currentService = streamingService;
	 	        currentService.login("user@2002", "pass@2002");
	 	        currentService.accessContent("Romance");
	 	        currentService.updateProfile("John", "Doe", "johndoe@example.com");
	 	        currentService.logout();
	        }
	        else if(choice==2) {
	        	DigitalService currentService = cloudStorageService;
		        currentService.login("user@2001", "pass@2001");
		        currentService.accessContent("Games");
		        currentService.updateProfile("James", "willey", "jameswilley@example.com");
		        currentService.logout();
	        	
	        }
	        else {
	        	System.out.println("Invalid Input");
	        }
	        sc.close();
	       

	        
	    }
 

}
