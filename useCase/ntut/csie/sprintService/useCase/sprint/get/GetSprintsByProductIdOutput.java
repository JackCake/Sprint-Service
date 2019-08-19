package ntut.csie.sprintService.useCase.sprint.get;

import java.util.List;

import ntut.csie.sprintService.useCase.Output;
import ntut.csie.sprintService.useCase.sprint.SprintModel;

public interface GetSprintsByProductIdOutput extends Output {
	public List<SprintModel> getSprintList();
	
	public void setSprintList(List<SprintModel> sprintList);
}
