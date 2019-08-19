package ntut.csie.sprintService.useCase.sprint;

import ntut.csie.sprintService.model.sprint.Sprint;

public class ConvertSprintToDTO {
	public static SprintModel transform(Sprint sprint) {
		SprintModel dto = new SprintModel();
		dto.setSprintId(sprint.getSprintId());
		dto.setOrderId(sprint.getOrderId());
		dto.setGoal(sprint.getGoal());
		dto.setStartDate(sprint.getStartDate());
		dto.setInterval(sprint.getInterval());
		dto.setEndDate(sprint.getEndDate());
		dto.setDemoDate(sprint.getDemoDate());
		dto.setDemoPlace(sprint.getDemoPlace());
		dto.setDaily(sprint.getDaily());
		dto.setProductId(sprint.getProductId());
		dto.setSprintOverdue(sprint.isSprintOverdue());
		dto.setRetrospective(sprint.getRetrospective());
		return dto;
	}
}
