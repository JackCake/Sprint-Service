package ntut.csie.sprintService.useCase.sprint.committedBacklogItem.get;

import ntut.csie.sprintService.useCase.Input;

public interface GetCommittedBacklogItemsBySprintIdInput extends Input {
	public String getSprintId();
	
	public void setSprintId(String sprintId);
}
