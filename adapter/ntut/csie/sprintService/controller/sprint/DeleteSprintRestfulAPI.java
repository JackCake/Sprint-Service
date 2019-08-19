package ntut.csie.sprintService.controller.sprint;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import ntut.csie.sprintService.ApplicationContext;
import ntut.csie.sprintService.useCase.sprint.delete.DeleteSprintInput;
import ntut.csie.sprintService.useCase.sprint.delete.DeleteSprintOutput;
import ntut.csie.sprintService.useCase.sprint.delete.DeleteSprintUseCase;

@Path("/sprints")
@Singleton
public class DeleteSprintRestfulAPI implements DeleteSprintOutput{
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private DeleteSprintUseCase deleteSprintUseCase = applicationContext.newDeleteSprintUseCase();
	
	private boolean deleteSuccess;
	private String errorMessage;

	@DELETE
	@Path("/{sprint_id}")
	public synchronized DeleteSprintOutput deletSprint(@PathParam("sprint_id") String sprintId) {
		DeleteSprintOutput output = this;
		
		DeleteSprintInput input = (DeleteSprintInput) deleteSprintUseCase;
		input.setSprintId(sprintId);
		
		deleteSprintUseCase.execute(input, output);
		
		return output;
	}
	
	@Override
	public boolean isDeleteSuccess() {
		return deleteSuccess;
	}

	@Override
	public void setDeleteSuccess(boolean deleteSuccess) {
		this.deleteSuccess = deleteSuccess;
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
