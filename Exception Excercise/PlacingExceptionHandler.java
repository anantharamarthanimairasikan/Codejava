package corejava1;

import java.util.Scanner;

public class PlacingExceptionHandler {
	public static void main(String[] args) {
		int val,sum=0;
		Scanner scan=new Scanner(System.in);
		
		
		System.out.println("Enter a line of text: ");
		Scanner scanLine = new Scanner(scan.nextLine());
		
		
			while(scanLine.hasNext()) {
				try {
				val=Integer.parseInt(scanLine.next());
				sum+=val;
			}catch(NumberFormatException n) {}
			
		}
		System.out.println("The sum of the line is: "+sum);
		scan.close();
	}

}
