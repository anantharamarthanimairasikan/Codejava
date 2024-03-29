package corejava1;
import java.util.Arrays;

public class CoreJava4thExercise {
	
	    public static void main(String[] args) {
	        // Create an array of ten int values, randomly chosen from 1 to 100
	        int[] myArray = new int[10];
	        for (int i = 0; i < myArray.length; i++) {
	            myArray[i] = (int) (Math.random() * 100) + 1;
	        }

	        // Show the elements of the array on screen
	        System.out.println("Original array:");
	        System.out.println(Arrays.toString(myArray));

	        // Sort the elements of the array in increasing order
	        Arrays.sort(myArray);

	        // Show the sorted elements of the array on screen
	        System.out.println("Sorted array (increasing order):");
	        System.out.println(Arrays.toString(myArray));
	    }
	}


