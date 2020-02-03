package ntut.csie.sprintService.controller.sprint.retrospective;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import ntut.csie.sprintService.ApplicationContext;
import ntut.csie.sprintService.useCase.sprint.retrospective.edit.EditRetrospectiveInput;
import ntut.csie.sprintService.useCase.sprint.retrospective.edit.EditRetrospectiveOutput;
import ntut.csie.sprintService.useCase.sprint.retrospective.edit.EditRetrospectiveUseCase;

@Path("/sprint_retrospectives/")
@Singleton
public class EditRetrospectiveRestfulAPI implements EditRetrospectiveOutput{
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private EditRetrospectiveUseCase editRetrospectiveUseCase = applicationContext.newEditRetrospectiveUseCase();
	
	private boolean editSuccess;
	private String errorMessage;
	
	@PUT
	@Path("/{sprint_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized EditRetrospectiveOutput editRetrospective(
			@PathParam("sprint_id") String sprintId, 
			String retrospectiveInfo) {
		String retrospective = "";
		
		EditRetrospectiveOutput output = this;
		
		try {
			JSONObject retrospectiveJSON = new JSONObject(retrospectiveInfo);
			retrospective = retrospectiveJSON.getString("retrospective");
		} catch (JSONException e) {
			e.printStackTrace();
			output.setEditSuccess(false);
			output.setErrorMessage("Sorry, there is the service problem when edit the retrospective. Please contact to the system administrator!");
			return output;
		}
		
		EditRetrospectiveInput input = (EditRetrospectiveInput) editRetrospectiveUseCase;
		input.setRetrospective(retrospective);
		input.setSprintId(sprintId);
		
		editRetrospectiveUseCase.execute(input, output);
		
		return output;
	}
	
	@Override
	public boolean isEditSuccess() {
		return editSuccess;
	}

	@Override
	public void setEditSuccess(boolean editSuccess) {
		this.editSuccess = editSuccess;
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
