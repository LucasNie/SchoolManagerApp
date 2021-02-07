package schoolmanager.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import schoolmanager.dao.GrupaDAO;
import schoolmanager.entity.Grupa;

@Service
public class GrupaServiceImpl implements GrupaService {

	@Autowired
	GrupaDAO grupaDAO;
	
	@Override
	@Transactional
	public Set<Grupa> getGrupas() {
		return grupaDAO.getGrupas();
	}

	@Override
	@Transactional
	public void saveGrupa(Grupa theGrupa) {
		grupaDAO.saveGrupa(theGrupa);
	}

	@Override
	@Transactional
	public void deleteGrupa(int theGroupId) {
		grupaDAO.deleteGrupa(theGroupId);
	}

	@Override
	@Transactional
	public void updateGrupa(Grupa theGrupa) {
		grupaDAO.updateGrupa(theGrupa);
	}

	@Override
	@Transactional
	public Grupa getGrupa(int theGroupId) {
		return grupaDAO.getGrupa(theGroupId);
	}

	@Override
	@Transactional
	public void removeStudentFromGrupa(int theStudentId, int theGroupId) {
		grupaDAO.removeStudentFromGrupa(theStudentId, theGroupId);
	}

}
