package corejava1;

import java.util.Scanner;

public class Vowels_Consonents {
	
	
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Please enter a String: ");
	        String input = scanner.nextLine().toUpperCase();

	        int vowels = 0;
	        int consonants = 0;
	       

	        for (int i = 0; i < input.length(); ++i) 
	        {
	            char ch = input.charAt(i);

	            if (ch >= 'A' && ch <= 'Z') 
	            {
	                if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U') 
	                {
	                    ++vowels;
	                } 
	                else 
	                {
	                    ++consonants;
	                }
	            }
	        }
	        scanner.close();  
		    System.out.println("Vowels: " + vowels);
		    System.out.println("Consonants: " + consonants);
		 }
}
