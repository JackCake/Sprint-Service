package ntut.csie.sprintService.useCase.history.get;

import ntut.csie.sprintService.useCase.Input;

public interface GetHistoriesByBacklogItemIdInput extends Input{
	public String getBacklogItemId();
	
	public void setBacklogItemId(String backlogItemId);
}
