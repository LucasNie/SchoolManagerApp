package schoolmanager.dao;

import schoolmanager.entity.Grade;

public interface GradeDAO {

	public void gradeStudent(int studentId, int gradeMark, String gradeComment, int subjectId);

	public Grade getGrade(int gradeId);

	public void deleteGrade(int gradeId);

}
