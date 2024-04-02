package Assign2;

import java.util.Scanner;

public class UserInterface {
		 
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        int Choice;
	 
	        
	            System.out.println("Welcome to Telecom Billing System");
	            System.out.println("Enter your choice:");
	            System.out.println("1. View Call Details");
	            System.out.println("2. Generate Invoice");
	            System.out.println("3. Exit");
	 
	            Choice = scanner.nextInt();
	 
	            switch (Choice) {
	                case 1:
	                    CallDetailsPresenter.displayCallDetails();
	                    break;
	                case 2:
	                	CallDetailsPresenter.generateInvoice();
	                    break;
	                case 3:
	                    break;
	                default:
	                    System.out.println("Invalid option, please choose a valid option.");
	            }
	            scanner.close();
	        }
	 
	        
	    }


