package ntut.csie.sprintService.useCase.sprint.edit;

import ntut.csie.sprintService.useCase.Input;

public interface EditSprintInput extends Input{
	public String getSprintId();
	
	public void setSprintId(String sprintId);
	
	public String getGoal();
	
	public void setGoal(String goal);
	
	public int getInterval();
	
	public void setInterval(int interval);
	
	public String getStartDate();
	
	public void setStartDate(String startDate);
	
	public String getEndDate();
	
	public void setEndDate(String endDate);
	
	public String getDemoDate();
	
	public void setDemoDate(String demoDate);
	
	public String getDemoPlace();
	
	public void setDemoPlace(String demoPlace);
	
	public String getDaily();
	
	public void setDaily(String daily);
	
	public String getProductId();
	
	public void setProductId(String productId);
}
