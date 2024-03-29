package corejava1;

import java.util.Scanner;

public class FactorialsException {
	public static void main (String[] args) {
	String keepGoing = "y";
	Scanner scan = new Scanner (System.in);
	while(keepGoing.equals("y") || keepGoing.equals("y") )
	{
	System.out.print("Enter an integer: ") ;
	int val = scan.nextInt () ;
	System.out.println("Factorial("+val+") ="+factorial(val));
	scan.close();
}
}
	public static int factorial (int n){
		if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        }
		else if(n>16) {
			 throw new IllegalArgumentException("Factorial is not defined for over 16.");
		}
 
        int factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }
}