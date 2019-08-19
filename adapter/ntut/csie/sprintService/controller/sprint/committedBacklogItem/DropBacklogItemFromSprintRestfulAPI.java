package ntut.csie.sprintService.controller.sprint.committedBacklogItem;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.sprintService.ApplicationContext;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.drop.DropBacklogItemFromSprintInput;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.drop.DropBacklogItemFromSprintOutput;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.drop.DropBacklogItemFromSprintUseCase;

@Path("/sprints/{sprint_id}/committed_backlog_items")
@Singleton
public class DropBacklogItemFromSprintRestfulAPI implements DropBacklogItemFromSprintOutput {
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private DropBacklogItemFromSprintUseCase dropBacklogItemFromSprintUseCase = applicationContext.newDropBacklogItemFromSprintUseCase();
	
	private boolean dropSuccess;
	private String errorMessage;
	
	@DELETE
	@Path("/{backlog_item_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized DropBacklogItemFromSprintOutput dropBacklogItemFromSprint(
			@PathParam("sprint_id") String sprintId, 
			@PathParam("backlog_item_id") String backlogItemId) {
		DropBacklogItemFromSprintOutput output = this;
		
		DropBacklogItemFromSprintInput input = (DropBacklogItemFromSprintInput) dropBacklogItemFromSprintUseCase;
		input.setBacklogItemId(backlogItemId);
		input.setSprintId(sprintId);
		
		dropBacklogItemFromSprintUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public boolean isDropSuccess() {
		return dropSuccess;
	}

	@Override
	public void setDropSuccess(boolean dropSuccess) {
		this.dropSuccess = dropSuccess;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
