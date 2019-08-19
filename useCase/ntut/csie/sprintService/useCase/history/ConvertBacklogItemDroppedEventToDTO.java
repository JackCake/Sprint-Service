package ntut.csie.sprintService.useCase.history;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ntut.csie.sprintService.model.sprint.BacklogItemDropped;
import ntut.csie.sprintService.model.sprint.CommittedBacklogItemBehavior;

public class ConvertBacklogItemDroppedEventToDTO {
	public static HistoryModel transform(BacklogItemDropped BacklogItemDropped) {
		HistoryModel dto = new HistoryModel();
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String occurredOn = simpleDateFormat.format(BacklogItemDropped.occurredOn());
		dto.setOccurredOn(occurredOn);
		dto.setBehavior(CommittedBacklogItemBehavior.dropBacklogItem);
		dto.setDescription("Drop Backlog Item From Sprint " + "\"" + BacklogItemDropped.sprintGoal() + "\"");
		return dto;
	}
}
