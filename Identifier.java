package corejava1;

import java.util.Scanner;

public class Identifier {

    public static void main(String[] args) {

        Scanner Sc = new Scanner(System.in);
        System.out.println("Enter the number of Strings:");
        int n = Sc.nextInt();
        Sc.nextLine();

        String[] Stringg = new String[n];
        System.out.println("Enter the Strings:");
        for (int i = 0; i < Stringg.length; i++) {
            Stringg[i] = Sc.nextLine();
        }

        for (String string : Stringg) {
            System.out.println(containsOnlyDigits(string));
        }
        Sc.close();
    }

    public static String containsOnlyDigits(String inputString) {
        if (inputString.matches("[0-9]+")) {
            return "It contains digits only";
        } else if (inputString.matches("[a-zA-Z]+")) {
            return "It contains letters only";
        } else {
            return "It contains both letters and digits";
        }
    }
}
