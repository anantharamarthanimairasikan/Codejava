package corejava1;
public class Permutions
{
		public static void main(String[] args) 
		{
			String str = "xyz";
			char ch[]=str.toCharArray();
			for(int i=0;i<str.length();i++) 
			{
				for(int j=0;j<str.length();j++) 
				{
					for(int k=0;k<str.length();k++) 
					{
						if(i!=j && j!=k && i!=k) 
						{
							System.out.println(ch[i]+""+ch[j]+""+ch[k]);
						}
					}
				}
			}
		}

}
