package ntut.csie.sprintService.useCase.history.get;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.sprintService.model.DomainEvent;
import ntut.csie.sprintService.model.sprint.BacklogItemCommitted;
import ntut.csie.sprintService.model.sprint.BacklogItemDropped;
import ntut.csie.sprintService.useCase.EventStore;
import ntut.csie.sprintService.useCase.history.ConvertBacklogItemCommittedEventToDTO;
import ntut.csie.sprintService.useCase.history.ConvertBacklogItemDroppedEventToDTO;
import ntut.csie.sprintService.useCase.history.HistoryModel;

public class GetHistoriesByBacklogItemIdUseCaseImpl implements GetHistoriesByBacklogItemIdUseCase, GetHistoriesByBacklogItemIdInput {
	private EventStore eventStore;
	
	private String backlogItemId;
	
	public GetHistoriesByBacklogItemIdUseCaseImpl(EventStore eventStore) {
		this.eventStore = eventStore;
	}
	
	@Override
	public void execute(GetHistoriesByBacklogItemIdInput input, GetHistoriesByBacklogItemIdOutput output) {
		String backlogItemId = input.getBacklogItemId();
		List<HistoryModel> historyList = new ArrayList<>();
		for(DomainEvent domainEvent : eventStore.getAllEvent()) {
			if(domainEvent instanceof BacklogItemCommitted) {
				BacklogItemCommitted backlogItemCommitted = (BacklogItemCommitted) domainEvent;
				if(backlogItemCommitted.backlogItemId().equals(backlogItemId)) {
					historyList.add(ConvertBacklogItemCommittedEventToDTO.transform(backlogItemCommitted));
				}
			} else if(domainEvent instanceof BacklogItemDropped) {
				BacklogItemDropped backlogItemDropped = (BacklogItemDropped) domainEvent;
				if(backlogItemDropped.backlogItemId().equals(backlogItemId)) {
					historyList.add(ConvertBacklogItemDroppedEventToDTO.transform(backlogItemDropped));
				}
			}
		}
		output.setHistoryList(historyList);
	}

	@Override
	public String getBacklogItemId() {
		return backlogItemId;
	}

	@Override
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}
}
