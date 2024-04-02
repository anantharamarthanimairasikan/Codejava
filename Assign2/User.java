package Assign2;

public class User {
	private int id;
	private String password;
	
	public User(int id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	public User() {
		
	}

	public int getId() {
		return id;
	}

	public User setId(int id) {
		this.id = id;
		return null;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + "]";
	}
	
	
	
	

}
