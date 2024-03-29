package corejava1;

import java.util.Scanner;

public class CodeJava6thExercise {

	    public static void main(String[] args) {
	    	Scanner Scan = new Scanner(System.in);
	    	System.out.println("Enter the no of array elements: ");
	    	int N = Scan.nextInt();
	    	System.out.println("Enter the array elements: ");
	        int[] myArray = new int[N];
	        for(int i=0;i<N;i++) {
	        	myArray[i]=Scan.nextInt();
	        }
	        int sum = calculateSum(myArray, myArray.length - 1);
	        System.out.println("Sum of array elements: " + sum);
	        Scan.close();
	    }

	    public static int calculateSum(int[] arr, int index) {
	        if (index < 0) {
	            return 0; // Base case: When index becomes negative, return 0
	        } else {
	            return arr[index] + calculateSum(arr, index - 1); // Recursive case
	        }
	    }
	}


