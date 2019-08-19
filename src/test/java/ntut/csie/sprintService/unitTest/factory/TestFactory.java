package ntut.csie.sprintService.unitTest.factory;

import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.model.sprint.SprintBuilder;
import ntut.csie.sprintService.unitTest.repository.FakeSprintRepository;

public class TestFactory {
	private FakeSprintRepository fakeSprintRepository;
	
	public TestFactory(FakeSprintRepository fakeSprintRepository) {
		this.fakeSprintRepository = fakeSprintRepository;
	}
	
	public Sprint newSprint(String goal, int interval, String startDate, String endDate, String demoDate, String productId) {
		int orderId = fakeSprintRepository.getSprintsByProductId(productId).size() + 1;
		Sprint sprint = null;
		try {
			sprint = SprintBuilder.newInstance().
					orderId(orderId).
					goal(goal).
					interval(interval).
					startDate(startDate).
					endDate(endDate).
					demoDate(demoDate).
					demoPlace("The place for demo ezScrum").
					productId(productId).
					build();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		fakeSprintRepository.save(sprint);
		return sprint;
	}
}
