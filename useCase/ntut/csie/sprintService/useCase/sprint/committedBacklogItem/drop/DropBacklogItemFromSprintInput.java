package ntut.csie.sprintService.useCase.sprint.committedBacklogItem.drop;

import ntut.csie.sprintService.useCase.Input;

public interface DropBacklogItemFromSprintInput extends Input {
	public String getBacklogItemId();
	
	public void setBacklogItemId(String backlogItemId);
	
	public String getSprintId();
	
	public void setSprintId(String sprintId);
}
