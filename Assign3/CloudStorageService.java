package Assign3;

public class CloudStorageService implements DigitalService {
    @Override
    public void login(String username, String password) {
        System.out.println("Logged in to Cloud Storage Service with username: " + username);
    }

    @Override
    public void logout() {
        System.out.println("Logged out of Cloud Storage Service");
    }

    @Override
    public void accessContent(String contentId) {


        System.out.println("Accessing content: " + contentId + " on Cloud Storage Service");
        
    }

    @Override
    public void updateProfile(String firstName, String lastName, String email) {
  
        System.out.println("Updated profile on Cloud Storage Service with first name: " + firstName);
    }
}