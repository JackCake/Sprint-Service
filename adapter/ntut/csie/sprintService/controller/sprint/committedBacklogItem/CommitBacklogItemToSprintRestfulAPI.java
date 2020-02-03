package ntut.csie.sprintService.controller.sprint.committedBacklogItem;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.sprintService.ApplicationContext;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.commit.CommitBacklogItemToSprintInput;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.commit.CommitBacklogItemToSprintOutput;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.commit.CommitBacklogItemToSprintUseCase;

@Path("/sprints/{sprint_id}/committed_backlog_items")
@Singleton
public class CommitBacklogItemToSprintRestfulAPI implements CommitBacklogItemToSprintOutput {
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private CommitBacklogItemToSprintUseCase commitBacklogItemToSprintUseCase = applicationContext.newCommitBacklogItemToSprintUseCase();
	
	private boolean commitSuccess;
	private String errorMessage;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized CommitBacklogItemToSprintOutput commitBacklogItemToSprint(
			@PathParam("sprint_id") String sprintId, 
			String committedBacklogItemInfo) {
		String backlogItemId = "";
		
		CommitBacklogItemToSprintOutput output = this;
		
		try {
			JSONObject committedBacklogItemJSON = new JSONObject(committedBacklogItemInfo);
			backlogItemId = committedBacklogItemJSON.getString("backlogItemId");
		} catch (JSONException e) {
			e.printStackTrace();
			output.setCommitSuccess(false);
			output.setErrorMessage("Sorry, there is the service problem when commit the backlog item to the sprint. Please contact to the system administrator!");
			return output;
		}
		
		CommitBacklogItemToSprintInput input = (CommitBacklogItemToSprintInput) commitBacklogItemToSprintUseCase;
		input.setBacklogItemId(backlogItemId);
		input.setSprintId(sprintId);
		
		commitBacklogItemToSprintUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public boolean isCommitSuccess() {
		return commitSuccess;
	}

	@Override
	public void setCommitSuccess(boolean commitSuccess) {
		this.commitSuccess = commitSuccess;
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
