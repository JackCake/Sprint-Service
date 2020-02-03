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
	
	public CommittedBacklogItem build() throws Exception {
		String exceptionMessage = "";
		if(backlogItemId == null || backlogItemId.isEmpty()) {
			exceptionMessage += "The backlog item id of the committed backlog item should be required!\n";
		}
		if(!exceptionMessage.isEmpty()) {
			throw new Exception(exceptionMessage);
		}
		
		CommittedBacklogItem committedBacklogItem = new CommittedBacklogItem(backlogItemId, sprintId);
		return committedBacklogItem;
	}
}
