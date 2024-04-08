package JavaCodingAssignment;

import java.util.Scanner;

public class Number {
	public static void main(String[]args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the Number:");
		int n=scan.nextInt();
		int result = intCheckPrime(n);
		if(result==1) {
			System.out.println(n+"is Prime Number");
		}else {
			System.out.println(n+"is Not a Prime Number");
		}
		boolean result1=checkAmstrong(n);
		if(result1) {
			System.out.println(n+"is Amstrong Number");
		}else {
			System.out.println(n+"is Not a Amstrong Number");
		}
		boolean result2=checkPalindrome(n);
		if(result2) {
			System.out.println(n+"is Palindrome Number");
		}else {
			System.out.println(n+"is Not a Palindrome Number");
		}
		scan.close();
	}
	public static int intCheckPrime(int N) {
		int flag=0;
		if (N <= 1) {
            return flag;
 
		}
        for (int i = 2; i < N; i++) {
            if (N % i == 0) {
            	return flag;
            }
            
        }
        
		return flag=1;
	}
	public static boolean checkAmstrong(int N) {
		int originalNumber = N, remainder, result = 0;
        boolean status= false;

        while (originalNumber != 0) {
            remainder = originalNumber % 10;
            result += Math.pow(remainder, 3); // Cubing each digit
            originalNumber /= 10;
        }

        if (result == originalNumber) {
        	status=true;
        }
		return status;
		
	}
	public static boolean checkPalindrome(int N) {
		boolean status= false;
		int r, sum = 0, temp;
        temp = N;
        while (N > 0) {
            r = N % 10; // Get the remainder
            sum = (sum * 10) + r;
            N = N / 10;
        }
        if (temp == sum) {
            status=true;
        }
        
 
		return status;
	}

}
