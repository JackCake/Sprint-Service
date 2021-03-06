package ntut.csie.sprintService.useCase.sprint.edit;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.useCase.sprint.SprintRepository;

public class EditSprintUseCaseImpl implements EditSprintUseCase, EditSprintInput{
	private SprintRepository sprintRepository;
	
	private String sprintId;
	private String goal;
	private int interval;
	private String startDate;
	private String endDate;
	private String demoDate;
	private String demoPlace;
	private String daily;
	private String productId;
	
	public EditSprintUseCaseImpl(SprintRepository sprintRepository) {
		this.sprintRepository = sprintRepository;
	}
	
	@Override
	public void execute(EditSprintInput input, EditSprintOutput output) {
		String sprintId = input.getSprintId();
		String goal = input.getGoal();
		int interval = input.getInterval();
		String startDate = input.getStartDate();
		String demoDate = input.getDemoDate();
		Sprint sprint = sprintRepository.getSprintById(sprintId);
		if(sprint == null) {
			output.setEditSuccess(false);
			output.setErrorMessage("Sorry, the sprint is not exist!");
			return;
		}
		String exceptionMessage = "";
		if(goal == null || goal.isEmpty()) {
			exceptionMessage += "The goal of the sprint should be required!\n";
		}
		if(interval == 0) {
			exceptionMessage += "The interval of the sprint should not be zero.\n";
		}
		if(startDate == null || startDate.isEmpty()) {
			exceptionMessage += "The start date of the sprint should be required!\n";
		}
		if(demoDate == null || demoDate.isEmpty()) {
			exceptionMessage += "The demo date of the sprint should be required!\n";
		}
		if(!exceptionMessage.isEmpty()) {
			output.setEditSuccess(false);
			output.setErrorMessage(exceptionMessage);
			return;
		}
		Sprint originalSprint = sprint;
		sprint.setGoal(input.getGoal());
		sprint.setInterval(input.getInterval());
		sprint.setStartDate(input.getStartDate());
		sprint.setEndDate(input.getEndDate());
		sprint.setDemoDate(input.getDemoDate());
		sprint.setDemoPlace(input.getDemoPlace());
		sprint.setDaily(input.getDaily());
		try {
			if(sprint.isSprintStartDateAfterEndDate()) {
				output.setEditSuccess(false);
				output.setErrorMessage("Sorry, the start date must be before the end date!");
				return;
			}
			if(sprint.isSprintStartDateAfterDemoDate()) {
				output.setEditSuccess(false);
				output.setErrorMessage("Sorry, the start date must be before the demo date!");
				return;
			}
			if(isSprintOverlap(originalSprint, sprint)) {
				output.setEditSuccess(false);
				output.setErrorMessage("Sorry, the start date or the end date is overlap with the other sprint!");
				return;
			}
			sprintRepository.save(sprint);
		} catch (Exception e) {
			output.setEditSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setEditSuccess(true);
	}
	
	private boolean isSprintOverlap(Sprint originalSprint, Sprint editedSprint) throws ParseException {
		String productId = editedSprint.getProductId();
		List<Sprint> sprintList = new ArrayList<>(sprintRepository.getSprintsByProductId(productId));
		for(Sprint otherSprint : sprintList) {
			if(!originalSprint.getSprintId().equals(otherSprint.getSprintId())) {
				String otherStartDate = otherSprint.getStartDate();
				String otherEndDate = otherSprint.getEndDate();
				if(editedSprint.isSprintOverlap(otherStartDate, otherEndDate)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String getSprintId() {
		return sprintId;
	}
	
	@Override
	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
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
