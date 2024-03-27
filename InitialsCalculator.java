package corejava1;

public class InitialsCalculator {
    public static void main(String[] args) {
        String fullName = "John Doe";
        String[] nameParts = fullName.split(" ");

        StringBuilder initials = new StringBuilder();

        for (String part : nameParts) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
            }
        }

        System.out.println("Your initials are: " + initials.toString());
    }
}
