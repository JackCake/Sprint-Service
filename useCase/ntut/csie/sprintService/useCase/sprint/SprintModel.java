package ntut.csie.sprintService.useCase.sprint;

public class SprintModel {
	private String sprintId;
	private int orderId;
	private String goal;
	private int interval;
	private String startDate;
	private String endDate;
	private String demoDate;
	private String demoPlace;
	private String daily;
	private String productId;
	private boolean sprintOverdue;
	private String retrospective;
	
	public String getSprintId() {
		return sprintId;
	}
	
	public void setSprintId(String sprintId) {
		this.sprintId = sprintId;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public String getGoal() {
		return goal;
	}
	
	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	public int getInterval() {
		return interval;
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getDemoDate() {
		return demoDate;
	}
	
	public void setDemoDate(String demoDate) {
		this.demoDate = demoDate;
	}
	
	public String getDemoPlace() {
		return demoPlace;
	}
	
	public void setDemoPlace(String demoPlace) {
		this.demoPlace = demoPlace;
	}
	
	public String getDaily() {
		return daily;
	}
	
	public void setDaily(String daily) {
		this.daily = daily;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public boolean isSprintOverdue() {
		return sprintOverdue;
	}
	
	public void setSprintOverdue(boolean sprintOverdue) {
		this.sprintOverdue = sprintOverdue;
	}

	public String getRetrospective() {
		return retrospective;
	}

	public void setRetrospective(String retrospective) {
		this.retrospective = retrospective;
	}
}
