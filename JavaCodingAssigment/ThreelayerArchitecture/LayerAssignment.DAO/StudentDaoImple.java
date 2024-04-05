package LayeredAssignmentStudentDAO;
import java.util.ArrayList;
import java.util.List;
import LayeredAssignment.Student;
import LayeredAssignmentStudentException.DataNotPresentException;

public class StudentDaoImple implements StudentDAo{
	private static List<Student> studentlist = new ArrayList<Student>();
	@Override
	public String addStudent(Student student) throws DataNotPresentException {
		
		if(student!=null) {
			studentlist.add(student);
			return "Added SucessFully"+student;
			
		}else {
			throw new DataNotPresentException("Unable to add student as no data was provided");
			
		}
	}

	@Override
	public Student DisplayStudent(int studentid) throws DataNotPresentException {
		Student st=null;
		for(Student stud:studentlist) {
			 if(stud.getId()==studentid) {
					st=stud;
				}
		 }
		 if(st==null) {
			 throw new DataNotPresentException("Invalid id or No Students are available");
		 }
		 return st;
	}
		
		

	@Override
	public List<Student> DisplayStudentbaseCity(String city) throws DataNotPresentException {
		List<Student>list1 = new ArrayList<Student>();
		if(city==null) {
			throw new DataNotPresentException("unable to find students from city as no data was provided");
		}
		for(Student stud:studentlist) {
			
			if(stud.getCity()==city) {
				list1.add(stud);
			}
		}
		if(list1.isEmpty()){
			 throw new DataNotPresentException("Invalid id or No Students are available");
		 }
		return list1;
	}

	@Override
	public List<Student> DisplayStudentbaseName(String name) throws DataNotPresentException {
		
		List<Student>list2 = new ArrayList<Student>();
		if(name==null) {
			throw new DataNotPresentException("unable to find student name as no data was provided");
		}
		for(Student stud:studentlist) {
			
			if(stud.getName()==name) {
				list2.add(stud);
			}
		}
		if(list2.isEmpty()){
			 throw new DataNotPresentException("Invalid name or No Students are available");
		 }
		return list2;

	}

	@Override
	public List<Student> DisplayStudentbaseCities(String[] cities) throws DataNotPresentException {
		List<Student>list = new ArrayList<Student>();
		if(cities==null) {
			throw new DataNotPresentException("unable to find cities names as no data was provided");
		}
		for(String city:cities) {
			for(Student stud:studentlist) {
				if(stud.getCity()==city) {
					list.add(stud);
				}
			}
			
		}
		if(list.isEmpty()){
			 throw new DataNotPresentException("Invalid City names or No Students coming from that cities are available");
		 }
		return list;
		
	}
	@Override
	public List<Student> readStudent() throws DataNotPresentException {
		// TODO Auto-generated method stub
	if(!studentlist.isEmpty()) {
		return studentlist;
	}else {
		throw new DataNotPresentException("data not present");
	}

}
}
