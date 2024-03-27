package corejava1;

import java.util.Scanner;

public class BufferTest {

	public static void main(String[]args) {
		StringBuffer s = new StringBuffer("Able was I ere I saw Elba.");
		System.out.println(s.capacity());
		String hannah = "Did Hannah see bees? Hannah did.";
		StringBuffer s1 = new StringBuffer(hannah);
		System.out.println(s1.length());
		System.out.println(s1.charAt(12));
		System.out.println(s1.indexOf("b"));
		s1.append( "Was it a car or a cat I saw?",9, 12);
		System.out.println(s1);
		String original = "software";
        StringBuffer result = new StringBuffer("hi");
        int index = original.indexOf('a');

/*1*/   result.setCharAt(0, original.charAt(0));
/*2*/   result.setCharAt(1, original.charAt(original.length()-1));
/*3*/   result.insert(1, original.charAt(4));
/*4*/   result.append(original.substring(1,4));
/*5*/   result.insert(3, (original.substring(index, index+2) + " "));

        System.out.println(result);
        String hi = "Hi, ";
        String mom = "mom.";
        hi=hi.concat(mom);
        System.out.println(hi);
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the Full name: ");
        String Name = scan.nextLine();
		int index1 = Name.indexOf(" ");
		System.out.println(Name.charAt(index1+1));
		scan.close();
		
}
}