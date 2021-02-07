package schoolmanager.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import schoolmanager.dao.SubjectDAO;
import schoolmanager.entity.Subject;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	SubjectDAO subjectDAO;
	
	@Override
	@Transactional
	public Set<Subject> getSubjects() {
		return subjectDAO.getSubjects();
	}

	@Override
	@Transactional
	public void saveSubject(Subject theSubject) {
		subjectDAO.saveSubject(theSubject);
	}

	@Override
	@Transactional
	public Subject getSubject(int theSubjectId) {
		return subjectDAO.getSubject(theSubjectId);
	}

	@Override
	@Transactional
	public void updateSubject(Subject theSubject) {
		subjectDAO.updateSubject(theSubject);
	}

	@Override
	@Transactional
	public void deleteSubject(int theSubjectId) {
		subjectDAO.deleteSubject(theSubjectId);
	}

	@Override
	@Transactional
	public void removeStudentFromSubject(int theStudentId, int theSubjectId) {
		subjectDAO.removeStudentFromSubject(theStudentId, theSubjectId);
	}

}
