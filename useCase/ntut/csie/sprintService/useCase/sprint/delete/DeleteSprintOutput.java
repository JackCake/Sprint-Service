package ntut.csie.sprintService.useCase.sprint.delete;

import ntut.csie.sprintService.useCase.Output;

public interface DeleteSprintOutput extends Output{
	public boolean isDeleteSuccess();
	
	public void setDeleteSuccess(boolean deleteSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
