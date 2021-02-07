package schoolmanager.dao;

import java.util.Set;

import schoolmanager.entity.Grupa;

public interface GrupaDAO {

	public Set<Grupa> getGrupas();

	public void saveGrupa(Grupa theGrupa);

	public void deleteGrupa(int theGroupId);

	public void updateGrupa(Grupa theGrupa);

	public Grupa getGrupa(int theGroupId);

	public void removeStudentFromGrupa(int theStudentId, int theGroupId);
	
}
