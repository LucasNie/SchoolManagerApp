package schoolmanager.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import schoolmanager.entity.Grupa;
import schoolmanager.entity.Student;

@Repository
public class GrupaDAOImpl implements GrupaDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Set<Grupa> getGrupas() {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Grupa> theQuery = currentSession.createQuery("from Grupa", Grupa.class);
		Set<Grupa> theGrupas = new HashSet(theQuery.getResultList());
		return theGrupas;
		
	}

	@Override
	public void saveGrupa(Grupa theGrupa) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(theGrupa);
		
	}

	@Override
	public void deleteGrupa(int theGroupId) {

		Session currentSession = sessionFactory.getCurrentSession();
		Grupa theGrupa = currentSession.get(Grupa.class, theGroupId);
		if(theGrupa != null) {
			currentSession.delete(theGrupa);
		}
		
	}

	@Override
	public void updateGrupa(Grupa theGrupa) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery = currentSession.createQuery("update Grupa set description =: newDescription where id =: grupaId");
		theQuery.setParameter("newDescription", theGrupa.getDescription());
		theQuery.setParameter("grupaId", theGrupa.getId());
		theQuery.executeUpdate();
		
	}

	@Override
	public Grupa getGrupa(int theGroupId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Grupa theGroup = currentSession.get(Grupa.class, theGroupId);
		return theGroup;
		
	}

	@Override
	public void removeStudentFromGrupa(int theStudentId, int theGroupId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Student theStudent = currentSession.get(Student.class, theStudentId);
		Grupa theGrupa = currentSession.get(Grupa.class, theGroupId);
		theGrupa.removeStudent(theStudent);
	}

}
