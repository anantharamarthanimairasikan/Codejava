package CollectionandGenericAssignment;

import java.util.Scanner;

public class Swapping {
	    public static <T> T[] swap(T[] list, int firstPos, int secondPos) {
	        if (firstPos < 0 || firstPos >= list.length || secondPos < 0 || secondPos >= list.length) {
	            throw new IndexOutOfBoundsException("Indexes are out of bounds.");
	        }

	        T temp = list[firstPos];
	        list[firstPos] = list[secondPos];
	        list[secondPos] = temp;

	        return list;
	    }
	    private static <T> void print(T[] arr) {
            for (T item : arr) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
	    public static void main(String[]args) {
	    	 Scanner scanner = new Scanner(System.in);
	         Integer[] numbers = {1,2,3,4,6};

	         System.out.println("Enter the First changing Postion:");
	         int fs = scanner.nextInt();
	         System.out.println("Enter the Second changing Postion:");
	         int ss = scanner.nextInt();
	         
	         
	         print(numbers);
	         print(Swapping.swap(numbers, fs, ss));

	         scanner.close();
	    }

	}
