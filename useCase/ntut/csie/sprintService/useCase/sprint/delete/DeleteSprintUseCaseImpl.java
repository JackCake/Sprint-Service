package ntut.csie.sprintService.useCase.sprint.delete;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.useCase.sprint.SprintRepository;

public class DeleteSprintUseCaseImpl implements DeleteSprintUseCase ,DeleteSprintInput{
	private SprintRepository sprintRepository;
	
	private String sprintId;

	public DeleteSprintUseCaseImpl(SprintRepository sprintRepository) {
		this.sprintRepository = sprintRepository;
	}
	
	@Override
	public void execute(DeleteSprintInput input, DeleteSprintOutput output) {
		String sprintId = input.getSprintId();
		Sprint sprint = sprintRepository.getSprintById(sprintId);
		if(sprint == null) {
			output.setDeleteSuccess(false);
			output.setErrorMessage("Sorry, the sprint is not exist.");
			return;
		}
		int orderId = sprint.getOrderId();
		String productId = sprint.getProductId();
		List<Sprint> sprintList = new ArrayList<>(sprintRepository.getSprintsByProductId(productId));
		try {
			for(int i = orderId; i < sprintList.size(); i++) {
				sprintList.get(i).setOrderId(i);
				sprintRepository.save(sprintList.get(i));
			}
			sprintRepository.remove(sprint);
		} catch (Exception e) {
			output.setDeleteSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setDeleteSuccess(true);
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
