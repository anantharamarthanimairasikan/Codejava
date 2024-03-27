package corejava1;

public class IntSearch {

	 public static int findIndex(int[] arr, int target) {
	        if (arr == null) {
	            return -1;
	        }        
	        int len = arr.length;
	        for (int i = 0; i < len; i++) {
	            if (arr[i] == target) {
	                return i;
	            }        }
			return -1;
	        
	}
}

	

