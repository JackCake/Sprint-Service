package ntut.csie.sprintService.model.sprint;

public class CommittedBacklogItemBuilder {
	private String backlogItemId;
	private String sprintId;
	
	public static CommittedBacklogItemBuilder newInstance() {
		return new CommittedBacklogItemBuilder();
	}
	
	public CommittedBacklogItemBuilder backlogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
		return this;
	}
	
	public CommittedBacklogItemBuilder sprintId(String sprintId) {
		this.sprintId = sprintId;
		return this;
	}
	
	public CommittedBacklogItem build() {
		CommittedBacklogItem committedBacklogItem = new CommittedBacklogItem(backlogItemId, sprintId);
		return committedBacklogItem;
	}
}
