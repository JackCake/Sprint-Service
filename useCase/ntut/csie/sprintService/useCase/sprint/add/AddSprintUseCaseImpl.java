package ntut.csie.sprintService.useCase.sprint.add;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.model.sprint.SprintBuilder;
import ntut.csie.sprintService.useCase.sprint.SprintRepository;

public class AddSprintUseCaseImpl implements AddSprintUseCase, AddSprintInput {
	private SprintRepository sprintRepository;
	
	private String goal;
	private int interval;
	private String startDate;
	private String endDate;
	private String demoDate;
	private String demoPlace;
	private String daily;
	private String productId;
	
	public AddSprintUseCaseImpl(SprintRepository sprintRepository) {
		this.sprintRepository = sprintRepository;
	}
	
	@Override
	public void execute(AddSprintInput input, AddSprintOutput output) {
		try {
			int orderId = sprintRepository.getSprintsByProductId(input.getProductId()).size() + 1;
			Sprint sprint = SprintBuilder.newInstance().
					orderId(orderId).
					goal(input.getGoal()).
					interval(input.getInterval()).
					startDate(input.getStartDate()).
					endDate(input.getEndDate()).
					demoDate(input.getDemoDate()).
					demoPlace(input.getDemoPlace()).
					daily(input.getDaily()).
					productId(input.getProductId()).
					build();
			if(isSprintOverlap(sprint)) {
				output.setAddSuccess(false);
				output.setErrorMessage("Sorry, the start date or the end date is overlap with the other sprint.");
				return;
			}
			sprintRepository.save(sprint);
		} catch (Exception e) {
			output.setAddSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setAddSuccess(true);
	}
	
	private boolean isSprintOverlap(Sprint thisSprint) {
		List<Sprint> sprintList = new ArrayList<>(sprintRepository.getSprintsByProductId(thisSprint.getProductId()));
		for(Sprint otherSprint : sprintList) {
			String otherStartDate = otherSprint.getStartDate();
			String otherEndDate = otherSprint.getEndDate();
			if(thisSprint.isSprintOverlap(otherStartDate, otherEndDate)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getGoal() {
		return goal;
	}
	
	@Override
	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	@Override
	public int getInterval() {
		return interval;
	}
	
	@Override
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	@Override
	public String getStartDate() {
		return startDate;
	}
	
	@Override
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	@Override
	public String getEndDate() {
		return endDate;
	}
	
	@Override
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String getDemoDate() {
		return demoDate;
	}
	
	@Override
	public void setDemoDate(String demoDate) {
		this.demoDate = demoDate;
	}
	
	@Override
	public String getDemoPlace() {
		return demoPlace;
	}
	
	@Override
	public void setDemoPlace(String demoPlace) {
		this.demoPlace = demoPlace;
	}
	
	@Override
	public String getDaily() {
		return daily;
	}
	
	@Override
	public void setDaily(String daily) {
		this.daily = daily;
	}
	
	@Override
	public String getProductId() {
		return productId;
	}
	
	@Override
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
