package JavaCodingAssignment;
import java.util.ArrayList;
import java.util.List;

public class AddEmployee {
	public static List<Employee> employeelist= new ArrayList<Employee>();
	
	public void addEmployee(Employee employee) {
		if(employee!=null) {
			employeelist.add(employee);
			System.out.println("The new employee added with employee id "+employee.getEmpid()+"Sucessfully");
		}
		else {
			System.out.println("No data was founded");
		}
		}
	
	public Employee getEmployee(int employeeid) {
		Employee st=null;
		for(Employee emp:employeelist) {
			 if(emp.getEmpid()==employeeid) {
					st=emp;
				}
		 }
		 if(st==null) {
			 System.out.println("No Employee was found");
		 }
		 return st;
	}
	public List<Employee> getallEmployee() {
		return employeelist;
		
	}

}
