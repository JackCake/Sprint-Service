package ntut.csie.sprintService.useCase.sprint.committedBacklogItem.commit;

import ntut.csie.sprintService.useCase.Output;

public interface CommitBacklogItemToSprintOutput extends Output {
	public boolean isCommitSuccess();
	
	public void setCommitSuccess(boolean commitSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
