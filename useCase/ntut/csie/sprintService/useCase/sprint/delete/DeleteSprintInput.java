package ntut.csie.sprintService.useCase.sprint.delete;

import ntut.csie.sprintService.useCase.Input;

public interface DeleteSprintInput extends Input{
	public String getSprintId();
	
	public void setSprintId(String sprintId);
}
