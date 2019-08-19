package ntut.csie.sprintService.controller.sprint;

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
import ntut.csie.sprintService.useCase.sprint.edit.EditSprintInput;
import ntut.csie.sprintService.useCase.sprint.edit.EditSprintOutput;
import ntut.csie.sprintService.useCase.sprint.edit.EditSprintUseCase;

@Path("/sprints")
@Singleton
public class EditSprintRestfulAPI implements EditSprintOutput{
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private EditSprintUseCase editSprintUseCase = applicationContext.newEditSprintUseCase();
	
	private boolean editSuccess;
	private boolean overlap;
	private String errorMessage;

	@PUT
	@Path("/{sprint_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized EditSprintOutput editSprint(
			@PathParam("sprint_id") String sprintId, 
			String sprintInfo) {
		String goal = "";
		int interval = 0;
		String startDate = "";
		String endDate = "";
		String demoDate = "";
		String demoPlace = "";
		String daily = "";
		
		EditSprintOutput output = this;
		
		try {
			JSONObject sprintJSON = new JSONObject(sprintInfo);
			goal = sprintJSON.getString("goal");
			interval = sprintJSON.getInt("interval");
			startDate = sprintJSON.getString("startDate");
			endDate = sprintJSON.getString("endDate");
			demoDate = sprintJSON.getString("demoDate");
			demoPlace = sprintJSON.getString("demoPlace");
			daily = sprintJSON.getString("daily");
		} catch (JSONException e) {
			e.printStackTrace();
			output.setEditSuccess(false);
			output.setErrorMessage("Sorry, please try again!");
			return output;
		}
		
		EditSprintInput input = (EditSprintInput) editSprintUseCase;
		input.setSprintId(sprintId);
		input.setGoal(goal);
		input.setInterval(interval);
		input.setStartDate(startDate);
		input.setEndDate(endDate);
		input.setDemoDate(demoDate);
		input.setDemoPlace(demoPlace);
		input.setDaily(daily);
		
		editSprintUseCase.execute(input, output);
		
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

	@Override
	public boolean isOverlap() {
		return overlap;
	}

	@Override
	public void setOverlap(boolean overlap) {
		this.overlap = overlap;
	}
}
