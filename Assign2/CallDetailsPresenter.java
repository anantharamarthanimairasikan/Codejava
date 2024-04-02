package Assign2;

import java.util.Scanner;

public class CallDetailsPresenter {

    public void calculateBill() {
    	Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user id...");
        User user = new User();
        user.setId(scanner.nextInt());
        user.setPassword(scanner.next());
        String message = CallDetailsPresenter.verifyuseridAndPassword(user);
        System.out.println(message);
        System.out.println("Calculating bill...");
        CallRecordFetcher.fetchCallRecords();
        scanner.close();
        
    }

    public static void generateInvoice() {
    	Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user id...");
        User user = new User();
        user.setId(scanner.nextInt());
        user.setPassword(scanner.next());
        String message = CallDetailsPresenter.verifyuseridAndPassword(user);
        System.out.println(message);
        scanner.close();
    	
        System.out.println("Generating invoice...");
    }

	public static void displayCallDetails() {
	
		 System.out.println("Fetching Call Logs...");
		
	}
	public static String verifyuseridAndPassword(User user) {
		User user1 = CallRecordFetcher.getUserDetails(user);
		if(user.getPassword().equals(user1.getPassword())) {
			return "User logged in successfully";
		}
		return "User details mismatch";
	}
	
}
