package Assignment;

public class RentalRequest {
	private String Type;
	int  rentingdays;
	
	public RentalRequest(String type, int days) {
		super();
		Type = type;
		this.rentingdays = days;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public int getDays() {
		return rentingdays;
	}

	public void setDays(int days) {
		this.rentingdays = days;
	}
	
	
	
	
}
