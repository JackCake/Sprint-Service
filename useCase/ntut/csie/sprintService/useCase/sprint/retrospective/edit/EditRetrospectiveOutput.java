package ntut.csie.sprintService.useCase.sprint.retrospective.edit;

import ntut.csie.sprintService.useCase.Output;

public interface EditRetrospectiveOutput extends Output{
	public boolean isEditSuccess();
	
	public void setEditSuccess(boolean editSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
