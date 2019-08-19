package ntut.csie.sprintService.useCase.sprint.retrospective.edit;

import ntut.csie.sprintService.useCase.Input;

public interface EditRetrospectiveInput extends Input{
	public String getRetrospective();
	
	public void setRetrospective(String retrospective);

	public String getSprintId();
	
	public void setSprintId(String sprintId);
}
