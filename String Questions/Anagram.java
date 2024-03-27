package corejava1;
import java.util.Arrays;
import java.util.Scanner;
public class Anagram {
	
	    public static void main(String[] args) {
	        Scanner input = new Scanner(System.in);

	        System.out.print("Enter the first string: ");
	        String str1 = input.nextLine().toUpperCase();

	        System.out.print("Enter the second string: ");
	        String str2 = input.nextLine().toUpperCase();

	        str1 = str1.replaceAll("\\s", "");
	        str2 = str2.replaceAll("\\s", "");
	        str1 = str1.replaceAll("[^a-zA-Z0-9]", "");
	        str2 = str2.replaceAll("[^a-zA-Z0-9]", "");

	        if (str1.length() != str2.length()) {
	            System.out.println(str1 + " and " + str2 + " are not anagrams.");
	        } else {
	            char[] charArray1 = str1.toCharArray();
	            char[] charArray2 = str2.toCharArray();

	            Arrays.sort(charArray1);
	            Arrays.sort(charArray2);

	            boolean result = Arrays.equals(charArray1, charArray2);
	            if (result) {
	                System.out.println(str1 + " and " + str2 + " are anagrams.");
	            } else {
	                System.out.println(str1 + " and " + str2 + " are not anagrams.");
	            }
	        }

	        input.close();
	    }
	}



