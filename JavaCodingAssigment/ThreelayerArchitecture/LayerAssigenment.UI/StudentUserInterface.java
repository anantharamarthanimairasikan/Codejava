package LayeredAssignmentStudentUI;

import java.util.Arrays;
import java.util.List;

import LayeredAssignment.Student;
import LayeredAssignmentStudentException.DataNotPresentException;
import LayeredAssignmentStudentService.StudentServiceImple;

public class StudentUserInterface {
	public static void main(String[]args) throws DataNotPresentException {
		Student stud = new Student();
		stud.setId(1);
		stud.setName("MS Dhoni");
		stud.setCity("Ranchi");
		Student stud1 = new Student(2,"SKY","Lucknow");
		Student stud2 = new Student(3,"R Ashwin","Chennai");
		Student stud3 = new Student(4,"Ishan Kishan","Patna");
		Student stud4 = new Student(5,"Virat Kohli","New Delhi");
		Student stud5 = new Student(6,"Rohit Sharma","New Delhi");
		Student stud6 = new Student(7,"Irfan Pathan","Ahmedabad");
		Student stud7 = new Student(8,"Yusuf Patha","Ahmedabad");
		Student stud8 = new Student(9,"Sorauv Ganguly","Kolkata");
		Student stud9 = new Student(10,"Sunil Gavaskar","Mumbai");
		Student stud10 = new Student(11,"Ishant Sharma","New Delhi");
		Student stud11 = new Student(12,"Mohammed Kaif","Muradabad");
		Student stud12 = new Student(13,"Kapil Dev","Chandigarh");
		
		StudentServiceImple studentservice = new StudentServiceImple();
		
		List<Student> StudentList = Arrays.asList(stud1,stud2,stud3,stud4,stud5,stud6,stud7,stud8,stud9,stud10,stud11,stud12);
		
		for(Student student:StudentList) {
			System.out.println(studentservice.addStudent(student));
		}
//		Student stud13=null;
//		System.out.println(studentservice.addStudent(stud13));
		
		int studentid=12;
		String area="Mumbai";
		String name ="SKY";
		String[]cities= {"Mumbai","Kolkata"};
		
		List<Student> studtentList = studentservice.readStudent();
		System.out.println(studtentList);
		System.out.println(studentservice.DisplayStudent(studentid));
		System.out.println(studentservice.DisplayStudentbaseCities(cities));
		System.out.println(studentservice.DisplayStudentbaseCity(area));
		System.out.println(studentservice.DisplayStudentbaseName(name));
		
	}

}
