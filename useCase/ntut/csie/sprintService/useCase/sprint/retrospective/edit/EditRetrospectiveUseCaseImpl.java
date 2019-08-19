package ntut.csie.sprintService.useCase.sprint.retrospective.edit;

import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.useCase.sprint.SprintRepository;

public class EditRetrospectiveUseCaseImpl implements EditRetrospectiveUseCase, EditRetrospectiveInput{
	private SprintRepository sprintRepository;
	
	private String retrospective;
	private String sprintId;
	
	public EditRetrospectiveUseCaseImpl (SprintRepository sprintRepository) {
		this.sprintRepository = sprintRepository;
	}
	
	@Override
	public void execute(EditRetrospectiveInput input, EditRetrospectiveOutput output) {
		Sprint sprint = sprintRepository.getSprintById(input.getSprintId());
		if(sprint == null) {
			output.setEditSuccess(false);
			output.setErrorMessage("Sorry, the sprint is not exist.");
			return;
		}
		sprint.setRetrospective(input.getRetrospective());
		try {
			sprintRepository.save(sprint);
		} catch (Exception e) {
			output.setEditSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setEditSuccess(true);
	}

	@Override
	public String getRetrospective() {
		return retrospective;
	}

	@Override
	public void setRetrospective(String retrospective) {
		this.retrospective = retrospective;
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
