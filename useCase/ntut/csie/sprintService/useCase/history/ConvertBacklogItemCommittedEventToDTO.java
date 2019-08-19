package ntut.csie.sprintService.useCase.history;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ntut.csie.sprintService.model.sprint.BacklogItemCommitted;
import ntut.csie.sprintService.model.sprint.CommittedBacklogItemBehavior;

public class ConvertBacklogItemCommittedEventToDTO {
	public static HistoryModel transform(BacklogItemCommitted backlogItemCommitted) {
		HistoryModel dto = new HistoryModel();
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String occurredOn = simpleDateFormat.format(backlogItemCommitted.occurredOn());
		dto.setOccurredOn(occurredOn);
		dto.setBehavior(CommittedBacklogItemBehavior.commitBacklogItem);
		dto.setDescription("Commit Backlog Item To Sprint " + "\"" + backlogItemCommitted.sprintGoal() + "\"");
		return dto;
	}
}
