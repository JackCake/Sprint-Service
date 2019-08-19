package ntut.csie.sprintService.useCase.sprint.committedBacklogItem.get;

import java.util.List;

import ntut.csie.sprintService.useCase.Output;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.CommittedBacklogItemModel;

public interface GetCommittedBacklogItemsBySprintIdOutput extends Output {
	public List<CommittedBacklogItemModel> getCommittedBacklogItemList();
	
	public void setCommittedBacklogItemList(List<CommittedBacklogItemModel> committedBacklogItemList);
}
