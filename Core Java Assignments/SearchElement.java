package corejava1;
import java.util.Scanner;

public class SearchElement extends IntSearch {
	
	public static void main(String[]args) {
		Scanner Scan = new Scanner(System.in);
		System.out.println("Enter the length of Array:");
		int N= Scan.nextInt();
		int arr[]=new int[N];
		System.out.println("Enter the Elements of Array:");
		for(int i=0;i<N;i++) {
			arr[i]=Scan.nextInt();
		}
		System.out.println("Enter Searching Element:");
		int Element= Scan.nextInt();
		int index= IntSearch.findIndex(arr,Element);
		if(index==-1) {
			System.out.println("The Element was not Found");
		}
		else {
			System.out.println("The Element was Found at "+(index+1)+" Place");
		}
		Scan.close();
		
	}

}
