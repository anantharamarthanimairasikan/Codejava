package corejava1;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileSearchusingExtension {
	public static void main(String[] args) throws IOException {
	Scanner sc= new Scanner(System.in);
	System.out.println("Enter the Directory: ");
	String path = sc.nextLine();
	
	System.out.println("Enter the Extentsion");
	String filename=sc.nextLine();
	File file = new File(path);
	if(file.isDirectory()) {
		File []files=file.listFiles();
		for(File file1:files) {
			if(file1.isFile()&& file1.getName().equals(filename))
				System.out.println("File founded");
				System.out.println(file1.getName());
				break;
		}
	}
	else{
		System.out.println("The Directory is not Found");
	}
	
	sc.close();
}
}
