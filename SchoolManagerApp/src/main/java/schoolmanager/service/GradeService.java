package schoolmanager.service;

import schoolmanager.entity.Grade;

public interface GradeService {

	public void gradeStudent(int studentId, int gradeMark, String gradeComment, int subjectId);

	public Grade getGrade(int gradeId);

	public void deleteGrade(int gradeId);
	
}
