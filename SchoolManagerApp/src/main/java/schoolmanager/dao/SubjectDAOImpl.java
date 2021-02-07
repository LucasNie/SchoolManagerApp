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
import schoolmanager.entity.Student;
import schoolmanager.entity.Subject;

@Repository
public class SubjectDAOImpl implements SubjectDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Set<Subject> getSubjects() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Subject> theQuery = currentSession.createQuery("from Subject", Subject.class);
		Set<Subject> theSubjects = new HashSet(theQuery.getResultList());
		return theSubjects;
	}

	@Override
	public void saveSubject(Subject theSubject) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(theSubject);
	}

	@Override
	public Subject getSubject(int theSubjectId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Subject theSubject = currentSession.get(Subject.class, theSubjectId);
		return theSubject;
	}

	@Override
	public void updateSubject(Subject theSubject) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery = currentSession.createQuery("update Subject set title =: newTitle where id =: subjectId");
		theQuery.setParameter("newTitle", theSubject.getTitle());
		theQuery.setParameter("subjectId", theSubject.getId());
		theQuery.executeUpdate();
	}

	@Override
	public void deleteSubject(int theSubjectId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Subject subjectToDelete = currentSession.get(Subject.class, theSubjectId);
		
		if(subjectToDelete != null) {
		Iterator<Grade> iterator = subjectToDelete.getGrades().iterator();
		while(iterator.hasNext()) {
			Grade tempGrade = iterator.next();
			iterator.remove();
			tempGrade.removeGrade();
			currentSession.delete(tempGrade);
		}
		
		currentSession.delete(subjectToDelete);
		}
	}

	@Override
	public void removeStudentFromSubject(int theStudentId, int theSubjectId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Student theStudent = currentSession.get(Student.class, theStudentId);
		Subject theSubject = currentSession.get(Subject.class, theSubjectId);
		theSubject.removeStudent(theStudent);
	}

}
