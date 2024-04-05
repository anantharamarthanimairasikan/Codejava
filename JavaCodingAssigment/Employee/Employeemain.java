package JavaCodingAssignment;
import java.util.Arrays;
import java.util.List;

public class Employeemain {
	
	public static void main(String[]args) {
		Employee emp1= new Employee();
		emp1.setEmpid(1);
		emp1.setEmpname("Harish");
		emp1.setCity("Chennai");
		Employee emp2 = new Employee(2,"Ashwin","Tuticorin");
		Employee emp3 = new Employee(3,"Sai","Chennai");
		Employee emp4 = new Employee(4,"Rajesh","Tuticorin");
		Employee emp5 = new Employee(5,"Amirtha","Hosur");
		Employee emp6 = new Employee(6,"Shiv","Chennai");
		Employee emp7 = new Employee(7,"Thiru","THirunindrvur");
		Employee emp8 = new Employee(8,"Preethi","KalaiyarKovil");
		Employee emp9 = new Employee(9,"Jyoti","Kalpakkam");
		Employee emp10 = new Employee(10,"Jv","Chennai");
		
		AddEmployee addemp = new AddEmployee();
		List<Employee> employeelist = Arrays.asList(emp1,emp2,emp3,emp4,emp5,emp6,emp7,emp8,emp9,emp10);
		
		for(Employee employee:employeelist) {
			addemp.addEmployee(employee);
		}
		System.out.println(addemp.getallEmployee());
		System.out.println(addemp.getEmployee(7));
		
	}

}
