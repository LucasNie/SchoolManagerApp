package schoolmanager.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import schoolmanager.entity.Grade;
import schoolmanager.entity.Student;
import schoolmanager.entity.Subject;

@Repository
public class GradeDAOImpl implements GradeDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void gradeStudent(int studentId, int gradeMark, String gradeComment, int subjectId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Grade newGrade = new Grade(gradeMark, gradeComment);
		Student theStudent = currentSession.get(Student.class, studentId);
		Subject theSubject = currentSession.get(Subject.class, subjectId);
		newGrade.addGrade(theSubject, theStudent);
		currentSession.save(newGrade);
		
	}

	@Override
	public Grade getGrade(int gradeId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Grade theGrade = currentSession.get(Grade.class, gradeId);
		return theGrade;
	}

	@Override
	public void deleteGrade(int gradeId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Grade newGrade = currentSession.get(Grade.class, gradeId);
		newGrade.removeGrade();
		currentSession.delete(newGrade);
	}
		
}