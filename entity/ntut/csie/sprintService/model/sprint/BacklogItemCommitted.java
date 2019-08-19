package ntut.csie.sprintService.model.sprint;

import java.util.Date;

import ntut.csie.sprintService.model.DateProvider;
import ntut.csie.sprintService.model.DomainEvent;

public class BacklogItemCommitted implements DomainEvent {
	private Date occuredOn;
	private String backlogItemId;
	private String sprintGoal;

	public BacklogItemCommitted(String backlogItemId, String sprintGoal) {
		this.occuredOn = DateProvider.now();
		this.backlogItemId = backlogItemId;
		this.sprintGoal = sprintGoal;
	}
	
	@Override
	public Date occurredOn() {
		return occuredOn;
	}
	
	public String backlogItemId() {
		return backlogItemId;
	}
	
	public String sprintGoal() {
		return sprintGoal;
	}
}
