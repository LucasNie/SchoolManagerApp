package schoolmanager.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import schoolmanager.entity.Grade;
import schoolmanager.entity.Grupa;
import schoolmanager.entity.Role;
import schoolmanager.entity.Student;
import schoolmanager.entity.Subject;

@Repository
public class StudentDAOImpl implements StudentDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Set<Student> getStudents() {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Student> theQuery = currentSession.createQuery("from Student", Student.class);
		Set<Student> theStudents = new HashSet(theQuery.getResultList());
		
		return theStudents;
	}

	@Override
	public void saveStudent(Student theStudent) {

		Session currentSession = sessionFactory.getCurrentSession();
		String passwordWithPrefix = "{noop}" + theStudent.getPassword();
		theStudent.setPassword(passwordWithPrefix);
		Role newRole = new Role("ROLE_STUDENT");
		theStudent.addRole(newRole);
		currentSession.save(theStudent);
		
	}

	@Override
	public Student getStudent(int theStudentId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Student theStudent = currentSession.get(Student.class, theStudentId);
		return theStudent;
	}

	@Override
	public void updateStudent(Student theStudent) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery = currentSession.createQuery("update Student set password =: newPassword, firstName =: newFirstName, lastName =: newLastName, email =: newEmail where id =: studentId");
		
		theQuery.setParameter("newPassword", "{noop}" + theStudent.getPassword());
		theQuery.setParameter("newFirstName", theStudent.getFirstName());
		theQuery.setParameter("newLastName", theStudent.getLastName());
		theQuery.setParameter("newEmail", theStudent.getEmail());
		theQuery.setParameter("studentId", theStudent.getId());
		theQuery.executeUpdate();
		
	}

	@Override
	public void deleteStudent(int theStudentId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Student theStudent = currentSession.get(Student.class, theStudentId);
		
		if(theStudent != null) {
		Iterator<Grade> iteratorGrade = theStudent.getGrades().iterator();
		while(iteratorGrade.hasNext()) {
			Grade tempGrade = iteratorGrade.next();
			iteratorGrade.remove();
			tempGrade.removeGrade();
			currentSession.delete(tempGrade);
		}
		
		currentSession.delete(theStudent);
		}
	}

	@Override
	public void addToGrupa(int theStudentId, int theGroupChosen) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Grupa grupaToAdd = currentSession.get(Grupa.class, theGroupChosen);
		Student studentToAdd = currentSession.get(Student.class, theStudentId);
		
		if( studentToAdd!=null && !(studentToAdd.getGrupas().contains(grupaToAdd)) && (theGroupChosen > 0) ) {
			grupaToAdd.addStudent(studentToAdd);
		}
	}

	@Override
	public void addToSubject(int theStudentId, int theSubjectChosen) {
		Session currentSession = sessionFactory.getCurrentSession();
		Subject subjectToAdd = currentSession.get(Subject.class, theSubjectChosen);
		Student studentToAdd = currentSession.get(Student.class, theStudentId);
		
		if( studentToAdd!=null && !(studentToAdd.getSubjects().contains(subjectToAdd)) && (theSubjectChosen > 0) ) {
			subjectToAdd.addStudent(studentToAdd);
		}
		
	}

	@Override
	public Student findByEmail(String email) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Student> theQuery = currentSession.createQuery("from Student where email =: email", Student.class);
		theQuery.setParameter("email", email);
		
		Student theStudent = null;
		try {
			theStudent = theQuery.getSingleResult();
		} catch(Exception e) {
			theStudent = null;
		}
		
		return theStudent;
	}

	@Override
	public Student findByUsername(String username) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Student> theQuery = currentSession.createQuery("from Student where username =: username", Student.class);
		theQuery.setParameter("username", username);
		
		Student theStudent = null;
		try {
			theStudent = theQuery.getSingleResult();
		} catch(Exception e) {
			theStudent = null;
		}
		
		return theStudent;
	}

}
