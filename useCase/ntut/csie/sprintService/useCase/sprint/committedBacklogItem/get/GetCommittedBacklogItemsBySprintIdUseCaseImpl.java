package ntut.csie.sprintService.useCase.sprint.committedBacklogItem.get;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.sprintService.model.sprint.CommittedBacklogItem;
import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.useCase.sprint.SprintRepository;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.CommittedBacklogItemModel;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.ConvertCommittedBacklogItemToDTO;

public class GetCommittedBacklogItemsBySprintIdUseCaseImpl implements GetCommittedBacklogItemsBySprintIdUseCase, GetCommittedBacklogItemsBySprintIdInput {
	private SprintRepository sprintRepository;
	
	private String sprintId;
	
	public GetCommittedBacklogItemsBySprintIdUseCaseImpl(SprintRepository sprintRepository) {
		this.sprintRepository = sprintRepository;
	}
	
	@Override
	public void execute(GetCommittedBacklogItemsBySprintIdInput input,
			GetCommittedBacklogItemsBySprintIdOutput output) {
		List<CommittedBacklogItemModel> committedBacklogItemList = new ArrayList<>();
		Sprint sprint = sprintRepository.getSprintById(input.getSprintId());
		for(CommittedBacklogItem committedBacklogItem : sprint.getCommittedBacklogItems()) {
			committedBacklogItemList.add(ConvertCommittedBacklogItemToDTO.transform(committedBacklogItem));
		}
		output.setCommittedBacklogItemList(committedBacklogItemList);
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
