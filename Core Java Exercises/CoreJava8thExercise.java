package corejava1;

import java.util.Scanner;

public class CoreJava8thExercise {
	public static void main(String[] args) {
		Scanner Scan = new Scanner(System.in);
		System.out.println("Enter the no of array elements: ");
		int N = Scan.nextInt();
		System.out.println("Enter the array elements: ");
		int[] myArray = new int[N];
		for (int i = 0; i < N; i++) {
			myArray[i] = Scan.nextInt();
		}
		int small = findSmallestelementIndex(myArray);
		System.out.println("Smallest element's Index  is: " + small);
		Scan.close();
	}

	public static int findSmallestelementIndex(int[] arr) {

		int index = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] > arr[j]) {
					index =j;
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;

				}
			}
		}
		return index;
	}
}
