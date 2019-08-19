package ntut.csie.sprintService.controller.sprint;

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
import ntut.csie.sprintService.useCase.sprint.add.AddSprintInput;
import ntut.csie.sprintService.useCase.sprint.add.AddSprintOutput;
import ntut.csie.sprintService.useCase.sprint.add.AddSprintUseCase;

@Path("/products/{product_id}/sprints")
@Singleton
public class AddSprintRestfulAPI implements AddSprintOutput {
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private AddSprintUseCase addSprintUseCase = applicationContext.newAddSprintUseCase();
	
	private boolean addSuccess;
	private String errorMessage;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized AddSprintOutput addSprint(
			@PathParam("product_id") String productId, 
			String sprintInfo) {
		String goal = "";
		int interval = 0;
		String startDate = "";
		String endDate = "";
		String demoDate = "";
		String demoPlace = "";
		String daily = "";
		
		AddSprintOutput output = this;
		
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
			output.setAddSuccess(false);
			output.setErrorMessage("Sorry, please try again!");
			return output;
		}
		
		AddSprintInput input = (AddSprintInput) addSprintUseCase;
		input.setGoal(goal);
		input.setInterval(interval);
		input.setStartDate(startDate);
		input.setEndDate(endDate);
		input.setDemoDate(demoDate);
		input.setDemoPlace(demoPlace);
		input.setDaily(daily);
		input.setProductId(productId);
		
		addSprintUseCase.execute(input, output);

		return output;
	}
	
	@Override
	public boolean isAddSuccess() {
		return addSuccess;
	}

	@Override
	public void setAddSuccess(boolean addSuccess) {
		this.addSuccess = addSuccess;
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
