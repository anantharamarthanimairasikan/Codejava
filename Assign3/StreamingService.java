package Assign3;


	public class StreamingService implements DigitalService {
	    @Override
	    public void login(String username, String password) {
	        System.out.println("Logged in to Streaming Service with username: " + username);
	    }

	    @Override
	    public void logout() {
	    	
	        System.out.println("Logged out of Streaming Service");
	    }

		@Override
	    public void accessContent(String contentId) {
	        System.out.println("Accessing content: " + contentId + " on Streaming Service");
	    }

	    @Override
	    public void updateProfile(String firstName, String lastName, String email) {
	        System.out.println("Updated profile on Streaming Service with first name: " + firstName);
	    }
	}

