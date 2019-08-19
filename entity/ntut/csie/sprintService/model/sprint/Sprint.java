package ntut.csie.sprintService.model.sprint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ntut.csie.sprintService.model.DomainEventPublisher;

public class Sprint {
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
	private List<CommittedBacklogItem> committedBacklogItems;
	private String retrospective;
	
	public Sprint() {
		committedBacklogItems = new ArrayList<>();
	}
	
	public Sprint(String sprintId, String goal, int interval, String startDate,
			String demoDate, String productId) {
		this.setSprintId(sprintId);
		this.setGoal(goal);
		this.setInterval(interval);
		this.setStartDate(startDate);
		this.setDemoDate(demoDate);
		this.setProductId(productId);
		committedBacklogItems = new ArrayList<>();
		this.setRetrospective("");
	}
	
	public boolean isSprintOverlap(String otherStartDate, String otherEndDate) {
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long thisSprintStartDate = 0;
		long thisSprintEndDate = 0;
		long otherSprintStartDate = 0;
		long otherSprintEndDate = 0;
		try {
			thisSprintStartDate = simpleDateFormat.parse(startDate).getTime();
			thisSprintEndDate = simpleDateFormat.parse(endDate).getTime();
			otherSprintStartDate = simpleDateFormat.parse(otherStartDate).getTime();
			otherSprintEndDate = simpleDateFormat.parse(otherEndDate).getTime();
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		if((thisSprintStartDate >= otherSprintStartDate && thisSprintStartDate <= otherSprintEndDate) ||
			(thisSprintEndDate >= otherSprintStartDate	&& thisSprintEndDate <= otherSprintEndDate) ||
			(thisSprintStartDate <= otherSprintStartDate && thisSprintEndDate >= otherSprintEndDate)) {
			return true;
		}
		return false;
	}
	
	public boolean isSprintOverdue() {
		Calendar calendar = Calendar.getInstance();
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long today = calendar.getTimeInMillis();
		long thisSprintEndDate = 0;
		try {
			thisSprintEndDate = simpleDateFormat.parse(endDate).getTime();
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return thisSprintEndDate < today;
	}
	
	public void commit(String backlogItemId) {
		addCommittedBacklogItem(backlogItemId);
		DomainEventPublisher.getInstance().publish(new BacklogItemCommitted(
				backlogItemId, goal));
	}
	
	public void drop(String backlogItemId) {
		removeCommittedBacklogItem(backlogItemId);
		DomainEventPublisher.getInstance().publish(new BacklogItemDropped(
				backlogItemId, goal));
	}
	
	public void addCommittedBacklogItem(String backlogItemId) {
		CommittedBacklogItem committedBacklogItem = CommittedBacklogItemBuilder.newInstance()
				.backlogItemId(backlogItemId)
				.sprintId(sprintId)
				.build();
		committedBacklogItems.add(committedBacklogItem);
	}
	
	public void removeCommittedBacklogItem(String backlogItemId) {
		for(CommittedBacklogItem committedBacklogItem : committedBacklogItems) {
			if(committedBacklogItem.getBacklogItemId().equals(backlogItemId)) {
				committedBacklogItems.remove(committedBacklogItem);
				break;
			}
		}
	}
	
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

	public List<CommittedBacklogItem> getCommittedBacklogItems() {
		return committedBacklogItems;
	}

	public void setCommittedBacklogItems(List<CommittedBacklogItem> committedBacklogItems) {
		this.committedBacklogItems = committedBacklogItems;
	}

	public String getRetrospective() {
		return retrospective;
	}

	public void setRetrospective(String retrospective) {
		this.retrospective = retrospective;
	}
}
