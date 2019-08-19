package ntut.csie.sprintService.model.sprint;

public class CommittedBacklogItem implements Cloneable {
	private String backlogItemId;
	private String sprintId;
	
	CommittedBacklogItem() {}
	
	CommittedBacklogItem(String backlogItemId, String sprintId) {
		this.backlogItemId = backlogItemId;
		this.sprintId = sprintId;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
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
