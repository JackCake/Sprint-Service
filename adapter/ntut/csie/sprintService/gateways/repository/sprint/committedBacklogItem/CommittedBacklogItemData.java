package ntut.csie.sprintService.gateways.repository.sprint.committedBacklogItem;

public class CommittedBacklogItemData {
	private String backlogItemId;
	private String sprintId;

	public String getBacklogItemId() {
		return backlogItemId;
	}
	
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}
	
	public String getSprintId() {
		return sprintId;
	}

	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}
}
