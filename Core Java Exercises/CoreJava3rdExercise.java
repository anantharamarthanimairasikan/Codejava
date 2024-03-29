package corejava1;

public class CoreJava3rdExercise {
	 public static void main(String[] args)
	 { 
		 int k = 5, j = 6;
		 Ex11_8 a = new Ex11_8();
		 Ex11_8 b = new Ex11_8(k*j);
		 Ex11_8 c = new Ex11_8(k,j);
	 }
	}
 class Ex11_8
	{
	 public Ex11_8(){
	 this(0);
	 System.out.println("A");
	 }
	 public Ex11_8(int k){
	 this(0,0);
	 System.out.println("B");
	 }
	 public Ex11_8(int k,int m){
	 System.out.println("C");
	 }
	}



