package ntut.csie.sprintService.useCase.sprint.committedBacklogItem.drop;

import ntut.csie.sprintService.useCase.Output;

public interface DropBacklogItemFromSprintOutput extends Output {
	public boolean isDropSuccess();
	
	public void setDropSuccess(boolean dropSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
