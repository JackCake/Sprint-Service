package ntut.csie.sprintService.useCase.sprint.edit;

import ntut.csie.sprintService.useCase.Output;

public interface EditSprintOutput extends Output{
	public boolean isEditSuccess();
	
	public void setEditSuccess(boolean editSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
