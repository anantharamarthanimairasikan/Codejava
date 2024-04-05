package JavaCodingAssignment;

public class Employee {
	private int empid;
	private String empname;
	private String city;
	
	public Employee(int empid, String empname, String city) {
		super();
		this.empid = empid;
		this.empname = empname;
		this.city = city;
	}

	public Employee() {
		super();
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Employee [empid=" + empid + ", empname=" + empname + ", city=" + city + "]";
	}
	
	

}
