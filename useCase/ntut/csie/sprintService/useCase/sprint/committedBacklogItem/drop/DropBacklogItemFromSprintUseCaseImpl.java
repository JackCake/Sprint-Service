package ntut.csie.sprintService.useCase.sprint.committedBacklogItem.drop;

import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.useCase.sprint.SprintRepository;

public class DropBacklogItemFromSprintUseCaseImpl implements DropBacklogItemFromSprintUseCase, DropBacklogItemFromSprintInput {
	private SprintRepository sprintRepository;
	
	private String backlogItemId;
	private String sprintId;
	
	public DropBacklogItemFromSprintUseCaseImpl(SprintRepository sprintRepository) {
		this.sprintRepository = sprintRepository;
	}
	
	@Override
	public void execute(DropBacklogItemFromSprintInput input, DropBacklogItemFromSprintOutput output) {
		Sprint sprint = sprintRepository.getSprintById(input.getSprintId());
		if(sprint == null) {
			output.setDropSuccess(false);
			output.setErrorMessage("Sorry, the sprint is not exist!");
			return;
		}
		sprint.drop(input.getBacklogItemId());
		try {
			sprintRepository.save(sprint);
		} catch (Exception e) {
			output.setDropSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setDropSuccess(true);
	}

	@Override
	public String getBacklogItemId() {
		return backlogItemId;
	}

	@Override
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}

	@Override
	public String getSprintId() {
		return sprintId;
	}

	@Override
	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}
}
