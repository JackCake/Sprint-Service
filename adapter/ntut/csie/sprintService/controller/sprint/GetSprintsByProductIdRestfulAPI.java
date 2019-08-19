package ntut.csie.sprintService.controller.sprint;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.sprintService.ApplicationContext;
import ntut.csie.sprintService.useCase.sprint.SprintModel;
import ntut.csie.sprintService.useCase.sprint.get.GetSprintsByProductIdInput;
import ntut.csie.sprintService.useCase.sprint.get.GetSprintsByProductIdOutput;
import ntut.csie.sprintService.useCase.sprint.get.GetSprintsByProductIdUseCase;

@Path("/products/{product_id}/sprints")
@Singleton
public class GetSprintsByProductIdRestfulAPI implements GetSprintsByProductIdOutput{
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private GetSprintsByProductIdUseCase getSprintsByProductIdUseCase = applicationContext.newGetSprintsByProductIdUseCase();
	
	private List<SprintModel> sprintList;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized GetSprintsByProductIdOutput getSprintsByProductId(@PathParam("product_id") String productId) {
		GetSprintsByProductIdOutput output = this;
		
		GetSprintsByProductIdInput input = (GetSprintsByProductIdInput) getSprintsByProductIdUseCase;
		input.setProductId(productId);
		
		getSprintsByProductIdUseCase.execute(input, output);
		
		return output;
	}
	
	@Override
	public List<SprintModel> getSprintList() {
		return sprintList;
	}

	@Override
	public void setSprintList(List<SprintModel> sprintList) {
		this.sprintList = sprintList;
	}
}
