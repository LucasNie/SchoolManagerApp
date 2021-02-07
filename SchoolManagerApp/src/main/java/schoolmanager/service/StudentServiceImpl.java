package schoolmanager.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import schoolmanager.dao.StudentDAO;
import schoolmanager.entity.Role;
import schoolmanager.entity.Student;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDAO studentDAO;
	
	@Override
	@Transactional
	public Set<Student> getStudents() {
		return studentDAO.getStudents();
	}

	@Override
	@Transactional
	public void saveStudent(Student theStudent) {
		studentDAO.saveStudent(theStudent);
	}

	@Override
	@Transactional
	public Student getStudent(int theStudentId) {
		return studentDAO.getStudent(theStudentId);
	}

	@Override
	@Transactional
	public void updateStudent(Student theStudent) {
		studentDAO.updateStudent(theStudent);
	}

	@Override
	@Transactional
	public void deleteStudent(int theStudentId) {
		studentDAO.deleteStudent(theStudentId);	
	}

	@Override
	@Transactional
	public void addToGrupa(int theStudentId, int theGroupChosen) {
		studentDAO.addToGrupa(theStudentId, theGroupChosen);
	}

	@Override
	@Transactional
	public void addToSubject(int theStudentId, int theSubjectChosen) {
		studentDAO.addToSubject(theStudentId, theSubjectChosen);
	}

	@Override
	@Transactional
	public Student findByEmail(String email) {
		return studentDAO.findByEmail(email);
	}
	
	@Override
	@Transactional
	public Student findByUsername(String username) {
		return studentDAO.findByUsername(username);
	}

	//SPRING SECURITY
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Student theStudent = studentDAO.findByUsername(username);
		if(theStudent == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		
		return new org.springframework.security.core.userdetails.User(theStudent.getUsername(), theStudent.getPassword(), mapRolesToAuthorities(theStudent.getRoles()));
		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
	}

}
