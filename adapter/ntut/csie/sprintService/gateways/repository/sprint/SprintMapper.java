package ntut.csie.sprintService.gateways.repository.sprint;

import ntut.csie.sprintService.gateways.repository.sprint.committedBacklogItem.CommittedBacklogItemData;
import ntut.csie.sprintService.model.sprint.Sprint;

public class SprintMapper {
	public Sprint transformToSprint(SprintData data) {
		Sprint sprint = new Sprint();
		sprint.setSprintId(data.getSprintId());
		sprint.setOrderId(data.getOrderId());
		sprint.setGoal(data.getGoal());
		sprint.setInterval(data.getInterval());
		sprint.setStartDate(data.getStartDate());
		sprint.setEndDate(data.getEndDate());
		sprint.setDemoDate(data.getDemoDate());
		sprint.setDemoPlace(data.getDemoPlace());
		sprint.setDaily(data.getDaily());
		sprint.setProductId(data.getProductId());
		for(CommittedBacklogItemData committedBacklogItemData : data.getCommittedBacklogItemDatas()) {
			sprint.addCommittedBacklogItem(committedBacklogItemData.getBacklogItemId());
		}
		sprint.setRetrospective(data.getRetrospective());
		return sprint;
	}
	
	public SprintData transformToSprintData(Sprint sprint) {
		SprintData data = new SprintData();
		data.setSprintId(sprint.getSprintId());
		data.setOrderId(sprint.getOrderId());
		data.setGoal(sprint.getGoal());
		data.setInterval(sprint.getInterval());
		data.setStartDate(sprint.getStartDate());
		data.setEndDate(sprint.getEndDate());
		data.setDemoDate(sprint.getDemoDate());
		data.setDemoPlace(sprint.getDemoPlace());
		data.setDaily(sprint.getDaily());
		data.setProductId(sprint.getProductId());
		data.setRetrospective(sprint.getRetrospective());
		return data;
	}
}
