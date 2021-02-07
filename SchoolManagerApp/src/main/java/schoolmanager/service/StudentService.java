package schoolmanager.service;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;

import schoolmanager.entity.Student;

public interface StudentService extends UserDetailsService {

	public Set<Student> getStudents();

	public void saveStudent(Student theStudent);

	public Student getStudent(int theStudentId);

	public void updateStudent(Student theStudent);

	public void deleteStudent(int theStudentId);

	public void addToGrupa(int theStudentId, int theGroupChosen);

	public void addToSubject(int theStudentId, int theSubjectChosen);

	public Student findByEmail(String email);

	public Student findByUsername(String username);
	
}
