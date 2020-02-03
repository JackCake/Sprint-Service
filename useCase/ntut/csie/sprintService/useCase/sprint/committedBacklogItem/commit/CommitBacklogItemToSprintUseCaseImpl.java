package ntut.csie.sprintService.useCase.sprint.committedBacklogItem.commit;

import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.useCase.sprint.SprintRepository;

public class CommitBacklogItemToSprintUseCaseImpl implements CommitBacklogItemToSprintUseCase, CommitBacklogItemToSprintInput {
	private SprintRepository sprintRepository;
	
	private String backlogItemId;
	private String sprintId;
	
	public CommitBacklogItemToSprintUseCaseImpl(SprintRepository sprintRepository) {
		this.sprintRepository = sprintRepository;
	}
	
	@Override
	public void execute(CommitBacklogItemToSprintInput input, CommitBacklogItemToSprintOutput output) {
		Sprint sprint = sprintRepository.getSprintById(input.getSprintId());
		if(sprint == null) {
			output.setCommitSuccess(false);
			output.setErrorMessage("Sorry, the sprint is not exist!");
			return;
		}
		try {
			sprint.commit(input.getBacklogItemId());
			sprintRepository.save(sprint);
		} catch (Exception e) {
			output.setCommitSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setCommitSuccess(true);
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
