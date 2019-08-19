package ntut.csie.sprintService.useCase;

import ntut.csie.sprintService.model.DomainEventPublisher;
import ntut.csie.sprintService.model.DomainEventSubscriber;
import ntut.csie.sprintService.model.sprint.BacklogItemCommitted;
import ntut.csie.sprintService.model.sprint.BacklogItemDropped;

public class DomainEventListener {
	private static DomainEventListener instance = null;
	
	private EventStore eventStore;
	
	private DomainEventListener() {}
	
	public static synchronized DomainEventListener getInstance() {
		if(instance == null) {
			instance = new DomainEventListener();
		}
		return instance;
	}
	
	public void init(EventStore eventStore) {
		this.eventStore = eventStore;
		DomainEventPublisher.getInstance().reset();
		eventSubscribe();
	}
	
	private void eventSubscribe() {
		DomainEventPublisher.getInstance().subscribe(new DomainEventSubscriber<BacklogItemCommitted>() {

			@Override
			public void handleEvent(BacklogItemCommitted domainEvent) {
				eventStore.save(domainEvent);
			}

			@Override
			public Class<BacklogItemCommitted> subscribedToEventType() {
				return BacklogItemCommitted.class;
			}
           
        });
		
		DomainEventPublisher.getInstance().subscribe(new DomainEventSubscriber<BacklogItemDropped>() {

			@Override
			public void handleEvent(BacklogItemDropped domainEvent) {
				eventStore.save(domainEvent);
			}

			@Override
			public Class<BacklogItemDropped> subscribedToEventType() {
				return BacklogItemDropped.class;
			}
           
        });
	}
}
