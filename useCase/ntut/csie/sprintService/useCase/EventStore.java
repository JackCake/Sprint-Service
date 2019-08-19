package ntut.csie.sprintService.useCase;

import java.util.List;

import ntut.csie.sprintService.model.DomainEvent;

public interface EventStore {
	public void save(DomainEvent event);
	
	public List<DomainEvent> getByEventType(String eventType);
	
	public List<DomainEvent> getAllEvent();
}
