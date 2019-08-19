package ntut.csie.sprintService.useCase.sprint.get;

import ntut.csie.sprintService.useCase.Input;

public interface GetSprintsByProductIdInput extends Input {
	public String getProductId();
	
	public void setProductId(String productId);
}
