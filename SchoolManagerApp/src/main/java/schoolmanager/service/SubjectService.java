package schoolmanager.service;

import java.util.Set;

import schoolmanager.entity.Subject;

public interface SubjectService {

	public Set<Subject> getSubjects();

	public void saveSubject(Subject theSubject);

	public Subject getSubject(int theSubjectId);

	public void updateSubject(Subject theSubject);

	public void deleteSubject(int theSubjectId);

	public void removeStudentFromSubject(int theStudentId, int theSubjectId);
	
}
