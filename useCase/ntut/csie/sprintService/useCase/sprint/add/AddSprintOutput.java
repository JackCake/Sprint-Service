package ntut.csie.sprintService.useCase.sprint.add;

import ntut.csie.sprintService.useCase.Output;

public interface AddSprintOutput extends Output{
	public boolean isAddSuccess();
	
	public void setAddSuccess(boolean addSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
