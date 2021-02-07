package schoolmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import schoolmanager.dao.GradeDAO;
import schoolmanager.entity.Grade;

@Service
public class GradeServiceImpl implements GradeService {
	
	@Autowired
	GradeDAO gradeDAO;
	
	@Override
	@Transactional
	public void gradeStudent(int studentId, int gradeMark, String gradeComment, int subjectId) {
		
		gradeDAO.gradeStudent(studentId, gradeMark, gradeComment, subjectId);
		
	}

	@Override
	@Transactional
	public Grade getGrade(int gradeId) {
		return gradeDAO.getGrade(gradeId);
	}

	@Override
	@Transactional
	public void deleteGrade(int gradeId) {
		gradeDAO.deleteGrade(gradeId);
	}

}
