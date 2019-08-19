package ntut.csie.sprintService.useCase.sprint.committedBacklogItem;

import ntut.csie.sprintService.model.sprint.CommittedBacklogItem;

public class ConvertCommittedBacklogItemToDTO {
	public static CommittedBacklogItemModel transform(CommittedBacklogItem committedBacklogItem) {
		CommittedBacklogItemModel dto = new CommittedBacklogItemModel();
		dto.setBacklogItemId(committedBacklogItem.getBacklogItemId());
		dto.setSprintId(committedBacklogItem.getSprintId());
		return dto;
	}
}
