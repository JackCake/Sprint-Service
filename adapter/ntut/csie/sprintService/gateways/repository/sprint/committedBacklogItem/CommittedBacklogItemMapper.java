package ntut.csie.sprintService.gateways.repository.sprint.committedBacklogItem;

import ntut.csie.sprintService.model.sprint.CommittedBacklogItem;

public class CommittedBacklogItemMapper {
	public CommittedBacklogItemData transformToCommittedBacklogItemData(CommittedBacklogItem committedBacklogItem) {
		CommittedBacklogItemData data = new CommittedBacklogItemData();
		data.setBacklogItemId(committedBacklogItem.getBacklogItemId());
		data.setSprintId(committedBacklogItem.getSprintId());
		return data;
	}
}
