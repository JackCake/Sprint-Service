package ntut.csie.sprintService.unitTest.useCase;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.sprintService.controller.history.GetHistoriesByBacklogItemIdRestfulAPI;
import ntut.csie.sprintService.controller.sprint.committedBacklogItem.CommitBacklogItemToSprintRestfulAPI;
import ntut.csie.sprintService.controller.sprint.committedBacklogItem.DropBacklogItemFromSprintRestfulAPI;
import ntut.csie.sprintService.controller.sprint.committedBacklogItem.GetCommittedBacklogItemsBySprintIdRestfulAPI;
import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.unitTest.factory.TestFactory;
import ntut.csie.sprintService.unitTest.repository.FakeEventStore;
import ntut.csie.sprintService.unitTest.repository.FakeSprintRepository;
import ntut.csie.sprintService.useCase.DomainEventListener;
import ntut.csie.sprintService.useCase.history.HistoryModel;
import ntut.csie.sprintService.useCase.history.get.GetHistoriesByBacklogItemIdInput;
import ntut.csie.sprintService.useCase.history.get.GetHistoriesByBacklogItemIdOutput;
import ntut.csie.sprintService.useCase.history.get.GetHistoriesByBacklogItemIdUseCase;
import ntut.csie.sprintService.useCase.history.get.GetHistoriesByBacklogItemIdUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.CommittedBacklogItemModel;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.commit.CommitBacklogItemToSprintInput;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.commit.CommitBacklogItemToSprintOutput;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.commit.CommitBacklogItemToSprintUseCase;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.commit.CommitBacklogItemToSprintUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.drop.DropBacklogItemFromSprintInput;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.drop.DropBacklogItemFromSprintOutput;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.drop.DropBacklogItemFromSprintUseCase;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.drop.DropBacklogItemFromSprintUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.get.GetCommittedBacklogItemsBySprintIdInput;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.get.GetCommittedBacklogItemsBySprintIdOutput;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.get.GetCommittedBacklogItemsBySprintIdUseCase;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.get.GetCommittedBacklogItemsBySprintIdUseCaseImpl;

public class CommittedBacklogItemUseCaseTest {
	private FakeSprintRepository fakeSprintRepository;
	private FakeEventStore fakeEventStore;
	
	private TestFactory testFactory;
	
	private Sprint sprint;
	
	@Before
	public void setUp() {
		fakeSprintRepository = new FakeSprintRepository();
		fakeEventStore = new FakeEventStore();
		DomainEventListener.getInstance().init(fakeEventStore);
		
		testFactory = new TestFactory(fakeSprintRepository);
		
		String productId = "1";
		String goal = "Implement the function of committing the backlog item to the sprint.";
		String startDate = "2018-04-09";
		String endDate = "2018-04-22";
		int interval = 2;
		String demoDate = "2018-04-22";
		sprint = testFactory.newSprint(goal, interval, startDate, endDate, demoDate, productId);
	}
	
	@After
	public void tearDown() {
		fakeSprintRepository.clearAll();
		fakeEventStore.clearAll();
	}
	
	@Test
	public void Should_Success_When_CommitBacklogItemToSprint() {
		String backlogItemId = "1";
		
		int originalNumberOfCommittedBacklogItems = sprint.getCommittedBacklogItems().size();
		
		commitBacklogItemToSprint(backlogItemId, sprint.getSprintId());
		
		int newNumberOfCommittedBacklogItems = sprint.getCommittedBacklogItems().size();
		
		assertEquals(1, newNumberOfCommittedBacklogItems - originalNumberOfCommittedBacklogItems);
	}
	
	@Test
	public void Should_ReturnCommittedBacklogItemList_When_GetCommittedBacklogItemsBySprintId() {
		String[] backlogItemIds = {"1", "2"};
		
		int numberOfCommittedBacklogItems = backlogItemIds.length;
		
		for(int i = 0; i < numberOfCommittedBacklogItems; i++) {
			commitBacklogItemToSprint(backlogItemIds[i], sprint.getSprintId());
		}
		
		assertEquals(numberOfCommittedBacklogItems, getCommittedBacklogItemsBySprintId(sprint.getSprintId()).size());
	}
	
	@Test
	public void Should_BacklogItemNotBelongToAnySprint_When_DropBacklogItemFromSprint() {
		String backlogItemId = "1";
		commitBacklogItemToSprint(backlogItemId, sprint.getSprintId());
		int numberOfCommittedBacklogItemsBeforeDropped = sprint.getCommittedBacklogItems().size();
		
		dropBacklogItemFromSprint(backlogItemId, sprint.getSprintId());
		int numberOfCommittedBacklogItemsAfterDropped = sprint.getCommittedBacklogItems().size();
		
		assertEquals(1, numberOfCommittedBacklogItemsBeforeDropped - numberOfCommittedBacklogItemsAfterDropped);
	}
	
	@Test
	public void Should_ReturnHistoryList_When_GetHistoriesByBacklogItemId() {
		String backlogItemId = "1";
		commitBacklogItemToSprint(backlogItemId, sprint.getSprintId());
		dropBacklogItemFromSprint(backlogItemId, sprint.getSprintId());
		
		assertEquals(2, getHistoriesByBacklogItemId(backlogItemId).size());
	}
	
	private void commitBacklogItemToSprint(String backlogItemId, String sprintId) {
		CommitBacklogItemToSprintUseCase commitBacklogItemToSprintUseCase = new CommitBacklogItemToSprintUseCaseImpl(fakeSprintRepository);
		CommitBacklogItemToSprintInput input = (CommitBacklogItemToSprintInput) commitBacklogItemToSprintUseCase;
		input.setBacklogItemId(backlogItemId);
		input.setSprintId(sprintId);
		CommitBacklogItemToSprintOutput output = new CommitBacklogItemToSprintRestfulAPI();
		commitBacklogItemToSprintUseCase.execute(input, output);
	}
	
	private List<CommittedBacklogItemModel> getCommittedBacklogItemsBySprintId(String sprintId) {
		GetCommittedBacklogItemsBySprintIdUseCase getCommittedBacklogItemBySprintIdUseCase = new GetCommittedBacklogItemsBySprintIdUseCaseImpl(fakeSprintRepository);
		GetCommittedBacklogItemsBySprintIdInput input = (GetCommittedBacklogItemsBySprintIdInput) getCommittedBacklogItemBySprintIdUseCase;
		input.setSprintId(sprintId);
		GetCommittedBacklogItemsBySprintIdOutput output = new GetCommittedBacklogItemsBySprintIdRestfulAPI();
		getCommittedBacklogItemBySprintIdUseCase.execute(input, output);
		return output.getCommittedBacklogItemList();
	}
	
	private void dropBacklogItemFromSprint(String backlogItemId, String sprintId) {
		DropBacklogItemFromSprintUseCase dropBacklogItemFromSprintUseCase = new DropBacklogItemFromSprintUseCaseImpl(fakeSprintRepository);
		DropBacklogItemFromSprintInput input = (DropBacklogItemFromSprintInput) dropBacklogItemFromSprintUseCase;
		input.setBacklogItemId(backlogItemId);
		input.setSprintId(sprintId);
		DropBacklogItemFromSprintOutput output = new DropBacklogItemFromSprintRestfulAPI();
		dropBacklogItemFromSprintUseCase.execute(input, output);
	}
	
	private List<HistoryModel> getHistoriesByBacklogItemId(String backlogItemId) {
		GetHistoriesByBacklogItemIdUseCase getHistoriesByBacklogItemIdUseCase = new GetHistoriesByBacklogItemIdUseCaseImpl(fakeEventStore);
		GetHistoriesByBacklogItemIdInput input = (GetHistoriesByBacklogItemIdInput) getHistoriesByBacklogItemIdUseCase;
		input.setBacklogItemId(backlogItemId);
		GetHistoriesByBacklogItemIdOutput output = new GetHistoriesByBacklogItemIdRestfulAPI();
		getHistoriesByBacklogItemIdUseCase.execute(input, output);
		return output.getHistoryList();
	}
}
