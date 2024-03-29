package corejava1;
import java.util.Arrays;
import java.util.Scanner;
public class CoreJava7thExercise {


		    public static void main(String[] args) {
		    	Scanner Scan = new Scanner(System.in);
		    	System.out.println("Enter the no of array elements: ");
		    	int N = Scan.nextInt();
		    	System.out.println("Enter the array elements: ");
		        int[] myArray = new int[N];
		        for(int i=0;i<N;i++) {
		        	myArray[i]=Scan.nextInt();
		        }
		        int small = findSmallest(myArray);
		        System.out.println("Smallest element in array is: " +small);
		        Scan.close();
		    }

		    public static int findSmallest(int[] arr) {
		    	Arrays.sort(arr);
		    	
				return arr[0];
		        
		    }
		}




