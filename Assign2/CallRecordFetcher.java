package Assign2;


public class CallRecordFetcher {
	public static void fetchCallRecords() {
        
        System.out.println("Fetching call records...");
        

    }

    public void updateBillingData(String data) {
       
        System.out.println("Updating billing data: " + data);
    }
    
    public static User getUserDetails(User user) {
		User user1 =new User(01, "pass@2002");
		if(user.getId()== user1.getId()){
			return user1;
		}
		return null;
		
}
    
}
