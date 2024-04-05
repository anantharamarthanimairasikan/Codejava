package LayeredAssignmentStudentService;

import java.util.List;

import LayeredAssignment.Student;
import LayeredAssignmentStudentException.DataNotPresentException;

public interface StudentService {
	public String addStudent(Student student) throws DataNotPresentException;

	public Student DisplayStudent(int studentid) throws DataNotPresentException;
	
	public List<Student> DisplayStudentbaseCity(String city)throws DataNotPresentException;
	
	public List<Student> DisplayStudentbaseName(String name) throws DataNotPresentException;
	
	public List<Student> DisplayStudentbaseCities(String[] cities)throws DataNotPresentException;	
	
	List<Student> readStudent() throws DataNotPresentException;
	
}
