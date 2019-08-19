package ntut.csie.sprintService.useCase.sprint;

import java.util.Collection;

import ntut.csie.sprintService.model.sprint.Sprint;

public interface SprintRepository {
	public void save(Sprint sprint) throws Exception;
	
	public void remove(Sprint sprint) throws Exception;
	
	public Sprint getSprintById(String sprintId);
	
	public Collection<Sprint> getSprintsByProductId(String productId);
}
