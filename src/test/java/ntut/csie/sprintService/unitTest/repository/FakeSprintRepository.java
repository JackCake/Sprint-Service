package ntut.csie.sprintService.unitTest.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ntut.csie.sprintService.model.sprint.CommittedBacklogItem;
import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.useCase.sprint.SprintRepository;

public class FakeSprintRepository implements SprintRepository {
	private Map<String, Sprint> sprints;
	private Map<String, CommittedBacklogItem> committedBacklogItems;
	
	public FakeSprintRepository() {
		sprints = Collections.synchronizedMap(new LinkedHashMap<String, Sprint>());
		committedBacklogItems = Collections.synchronizedMap(new LinkedHashMap<String, CommittedBacklogItem>());
	}
	
	@Override
	public void save(Sprint sprint) {
		for(CommittedBacklogItem committedBacklogItem : sprint.getCommittedBacklogItems()) {
			addCommittedBacklogItem(committedBacklogItem);
		}
		sprints.put(sprint.getSprintId(), sprint);
	}

	@Override
	public void remove(Sprint sprint) {
		for(CommittedBacklogItem committedBacklogItem : sprint.getCommittedBacklogItems()) {
			removeCommittedBacklogItem(committedBacklogItem);
		}
		sprints.remove(sprint.getSprintId());
	}

	@Override
	public Sprint getSprintById(String sprintId) {
		return sprints.get(sprintId);
	}

	@Override
	public Collection<Sprint> getSprintsByProductId(String productId) {
		List<Sprint> sprintList = new ArrayList<>();
		for(Sprint sprint : sprints.values()) {
			if(sprint.getProductId().equals(productId)) {
				sprintList.add(sprint);
			}
		}
		return sprintList;
	}
	
	private void addCommittedBacklogItem(CommittedBacklogItem committedBacklogItem) {
		committedBacklogItems.put(committedBacklogItem.getBacklogItemId(), committedBacklogItem);
	}

	private void removeCommittedBacklogItem(CommittedBacklogItem committedBacklogItem) {
		committedBacklogItems.remove(committedBacklogItem.getBacklogItemId());
	}

	public void clearAll() {
		sprints.clear();
		committedBacklogItems.clear();
	}
}
