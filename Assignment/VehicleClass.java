package Assignment;

public class VehicleClass {
	private int id;
	public String type;
	private int days;
	public boolean available;
	
	public VehicleClass(int id, String type, boolean avaliable, int days) {
		super();
		this.id = id;
		this.type = type;
		this.available = avaliable;
		this.days = days;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isAvaliable() {
		return available;
	}
	public void setAvaliable(boolean avaliable) {
		this.available = avaliable;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "VehicleClass [id=" + id + ", type=" + type + ", avaliable=" + available + ", days=" + days + "]";
	}
	
	
	

}
