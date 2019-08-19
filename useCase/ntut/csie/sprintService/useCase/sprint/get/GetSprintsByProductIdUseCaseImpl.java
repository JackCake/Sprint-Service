package ntut.csie.sprintService.useCase.sprint.get;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.useCase.sprint.ConvertSprintToDTO;
import ntut.csie.sprintService.useCase.sprint.SprintModel;
import ntut.csie.sprintService.useCase.sprint.SprintRepository;

public class GetSprintsByProductIdUseCaseImpl implements GetSprintsByProductIdUseCase, GetSprintsByProductIdInput {
	private SprintRepository sprintRepository;
	
	private String productId;
	
	public GetSprintsByProductIdUseCaseImpl(SprintRepository sprintRepository) {
		this.sprintRepository = sprintRepository;
	}

	@Override
	public void execute(GetSprintsByProductIdInput input, GetSprintsByProductIdOutput output) {
		List<SprintModel> sprintList = new ArrayList<>();
		for(Sprint sprint : sprintRepository.getSprintsByProductId(input.getProductId())) {
			sprintList.add(ConvertSprintToDTO.transform(sprint));
		}
		output.setSprintList(sprintList);
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
