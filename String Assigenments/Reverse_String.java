package corejava1;

import java.util.Scanner;

public class Reverse_String {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a String: ");
        String input = scanner.nextLine();
        char[] string = input.toCharArray();
        int left = 0, right = string.length - 1;  
        while (left < right) 
        {  
            char temp = string[left];  
            string[left++] = string[right];  
            string[right--] = temp;  
        }
        System.out.println(string);
        scanner.close();
}
}
