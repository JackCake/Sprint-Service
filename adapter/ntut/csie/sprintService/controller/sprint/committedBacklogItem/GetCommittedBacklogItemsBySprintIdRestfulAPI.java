package ntut.csie.sprintService.controller.sprint.committedBacklogItem;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.sprintService.ApplicationContext;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.CommittedBacklogItemModel;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.get.GetCommittedBacklogItemsBySprintIdInput;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.get.GetCommittedBacklogItemsBySprintIdOutput;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.get.GetCommittedBacklogItemsBySprintIdUseCase;

@Path("/sprints/{sprint_id}/committed_backlog_items")
@Singleton
public class GetCommittedBacklogItemsBySprintIdRestfulAPI implements GetCommittedBacklogItemsBySprintIdOutput {
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private GetCommittedBacklogItemsBySprintIdUseCase getCommittedBacklogItemsBySprintIdUseCase = applicationContext.newGetCommittedBacklogItemsBySprintIdUseCase();
	
	private List<CommittedBacklogItemModel> committedBacklogItemList;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized GetCommittedBacklogItemsBySprintIdOutput getCommittedBacklogItemsBySprintId(@PathParam("sprint_id") String sprintId) {
		GetCommittedBacklogItemsBySprintIdOutput output = this;
		
		GetCommittedBacklogItemsBySprintIdInput input = (GetCommittedBacklogItemsBySprintIdInput) getCommittedBacklogItemsBySprintIdUseCase;
		
		input.setSprintId(sprintId);
		
		getCommittedBacklogItemsBySprintIdUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public List<CommittedBacklogItemModel> getCommittedBacklogItemList() {
		return committedBacklogItemList;
	}

	@Override
	public void setCommittedBacklogItemList(List<CommittedBacklogItemModel> committedBacklogItemList) {
		this.committedBacklogItemList = committedBacklogItemList;
	}

}
