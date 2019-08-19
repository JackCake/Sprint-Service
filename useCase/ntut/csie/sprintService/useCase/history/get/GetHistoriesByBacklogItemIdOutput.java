package ntut.csie.sprintService.useCase.history.get;

import java.util.List;

import ntut.csie.sprintService.useCase.Output;
import ntut.csie.sprintService.useCase.history.HistoryModel;

public interface GetHistoriesByBacklogItemIdOutput extends Output{
	public List<HistoryModel> getHistoryList();
	
	public void setHistoryList(List<HistoryModel> historyList);
}
