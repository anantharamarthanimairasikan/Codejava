package LayeredAssignmentStudentService;
import java.util.List;
import LayeredAssignment.Student;
import LayeredAssignmentStudentDAO.StudentDaoImple;
import LayeredAssignmentStudentException.DataNotPresentException;

public class StudentServiceImple implements StudentService{

	@Override
	public String addStudent(Student student) throws DataNotPresentException {
		StudentDaoImple stud = new StudentDaoImple();
		return stud.addStudent(student);
	}

	@Override
	public Student DisplayStudent(int studentid) throws DataNotPresentException {
		StudentDaoImple stud = new StudentDaoImple();
		return stud.DisplayStudent(studentid);
	}

	@Override
	public List<Student> DisplayStudentbaseCity(String city) throws DataNotPresentException{
		StudentDaoImple stud = new StudentDaoImple();
		return stud.DisplayStudentbaseCity(city);
	}

	@Override
	public List<Student> DisplayStudentbaseName(String name) throws DataNotPresentException {
		StudentDaoImple stud = new StudentDaoImple();
		return stud.DisplayStudentbaseName(name);
	}

	@Override
	public List<Student> DisplayStudentbaseCities(String[] cities) throws DataNotPresentException {
		StudentDaoImple stud = new StudentDaoImple();
		return stud.DisplayStudentbaseCities(cities);
	}
	
	public  List<Student> readStudent() throws DataNotPresentException{
		StudentDaoImple stud = new StudentDaoImple();
		return stud.readStudent();
	}

}
