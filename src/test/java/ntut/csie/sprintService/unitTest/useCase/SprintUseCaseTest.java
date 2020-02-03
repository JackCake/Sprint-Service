package ntut.csie.sprintService.unitTest.useCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.sprintService.controller.sprint.AddSprintRestfulAPI;
import ntut.csie.sprintService.controller.sprint.DeleteSprintRestfulAPI;
import ntut.csie.sprintService.controller.sprint.EditSprintRestfulAPI;
import ntut.csie.sprintService.controller.sprint.GetSprintsByProductIdRestfulAPI;
import ntut.csie.sprintService.controller.sprint.retrospective.EditRetrospectiveRestfulAPI;
import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.unitTest.factory.TestFactory;
import ntut.csie.sprintService.unitTest.repository.FakeSprintRepository;
import ntut.csie.sprintService.useCase.sprint.SprintModel;
import ntut.csie.sprintService.useCase.sprint.add.AddSprintInput;
import ntut.csie.sprintService.useCase.sprint.add.AddSprintOutput;
import ntut.csie.sprintService.useCase.sprint.add.AddSprintUseCase;
import ntut.csie.sprintService.useCase.sprint.add.AddSprintUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.delete.DeleteSprintInput;
import ntut.csie.sprintService.useCase.sprint.delete.DeleteSprintOutput;
import ntut.csie.sprintService.useCase.sprint.delete.DeleteSprintUseCase;
import ntut.csie.sprintService.useCase.sprint.delete.DeleteSprintUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.edit.EditSprintInput;
import ntut.csie.sprintService.useCase.sprint.edit.EditSprintOutput;
import ntut.csie.sprintService.useCase.sprint.edit.EditSprintUseCase;
import ntut.csie.sprintService.useCase.sprint.edit.EditSprintUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.get.GetSprintsByProductIdInput;
import ntut.csie.sprintService.useCase.sprint.get.GetSprintsByProductIdOutput;
import ntut.csie.sprintService.useCase.sprint.get.GetSprintsByProductIdUseCase;
import ntut.csie.sprintService.useCase.sprint.get.GetSprintsByProductIdUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.retrospective.edit.EditRetrospectiveInput;
import ntut.csie.sprintService.useCase.sprint.retrospective.edit.EditRetrospectiveOutput;
import ntut.csie.sprintService.useCase.sprint.retrospective.edit.EditRetrospectiveUseCase;
import ntut.csie.sprintService.useCase.sprint.retrospective.edit.EditRetrospectiveUseCaseImpl;

public class SprintUseCaseTest {
	private FakeSprintRepository fakeSprintRepository;
	
	private TestFactory testFactory;
	
	private String productId;

	@Before
	public void setUp() {
		fakeSprintRepository = new FakeSprintRepository();
		
		testFactory = new TestFactory(fakeSprintRepository);
		
		productId = "1";
	}
	
	@After
	public void tearDown() {
		fakeSprintRepository.clearAll();
	}
	
	@Test
	public void Should_Success_When_AddSprint() {
		String goal = "Implement the function of creating sprint.";
		int interval = 2;
		String startDate = "2018-05-15";
		String endDate = "2018-05-28";
		String demoDate = "2018-05-28";
		
		AddSprintOutput output = addNewSprint(goal, interval, startDate, endDate, demoDate, productId);
		
		boolean isAddSuccess = output.isAddSuccess();
		assertTrue(isAddSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_AddSprintWithNullParamemters() {
		String goal = null;
		int interval = 0;
		String startDate = null;
		String endDate = null;
		String demoDate = null;
		
		AddSprintOutput output = addNewSprint(goal, interval, startDate, endDate, demoDate, null);
		
		boolean isAddSuccess = output.isAddSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The goal of the sprint should be required!\n" +
				"The interval of the sprint should not be zero!\n" +
				"The start date of the sprint should be required!\n" +
				"The demo date of the sprint should be required!\n" + 
				"The product id of the sprint should be required!\n";
		assertFalse(isAddSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_AddSprintWithEmptyParamemters() {
		String goal = "";
		int interval = 0;
		String startDate = "";
		String endDate = "";
		String demoDate = "";
		
		AddSprintOutput output = addNewSprint(goal, interval, startDate, endDate, demoDate, "");
		
		boolean isAddSuccess = output.isAddSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The goal of the sprint should be required!\n" +
				"The interval of the sprint should not be zero!\n" +
				"The start date of the sprint should be required!\n" +
				"The demo date of the sprint should be required!\n" + 
				"The product id of the sprint should be required!\n";
		assertFalse(isAddSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnSprintList_When_GetSprintsOfProduct() {
		String[] goal = {
				"This is the goal of Sprint#1",
				"This is the goal of Sprint#2",
				"This is the goal of Sprint#3"
		};
		int interval = 2;
		String[] startDate = {"2018-05-15", "2018-05-29", "2018-06-15"};
		String[] endDate = {"2018-05-28", "2018-06-14", "2018-06-28"};
		String[] demoDate = {"2018-05-28", "2018-06-14", "2018-06-28"};
		
		int numberOfNewSprints = goal.length;
		
		for(int i = 0; i < numberOfNewSprints; i++) {
			testFactory.newSprint(goal[i], interval, startDate[i], endDate[i], demoDate[i], productId);
		}
		
		GetSprintsByProductIdOutput output = getSprintsByProductId(productId);
		List<SprintModel> sprintList = output.getSprintList();
		
		for(int i = 0; i < numberOfNewSprints; i++) {
			assertEquals(goal[i], sprintList.get(i).getGoal());
			assertEquals(startDate[i], sprintList.get(i).getStartDate());
			assertEquals(endDate[i], sprintList.get(i).getEndDate());
			assertEquals(interval, sprintList.get(i).getInterval());
			assertEquals(demoDate[i], sprintList.get(i).getDemoDate());
		}
		assertEquals(numberOfNewSprints, sprintList.size());
	}
	
	@Test
	public void Should_Success_When_EditSprint() {
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-23";
		String endDate = "2018-05-06";
		int interval = 2;
		String demoDate = "2018-05-06";
		
		Sprint editedSprint = testFactory.newSprint(goal, interval, startDate, endDate, demoDate, productId);
		
		String editedSprintId = editedSprint.getSprintId();
		String editedGoal = "Implement the function of editing sprint.";
		String editedStartDate = "2018-05-07";
		int editedInterval = 3;
		String editedEndDate = "2018-05-27";
		String editedDemoDate = "2018-05-27";
		String editedDemoPlace = "03F Room";
		String editedDaily = "15:00 1323";
		
		EditSprintOutput output = editSprint(editedSprintId, editedGoal, editedInterval,
			editedStartDate, editedEndDate, editedDemoDate, editedDemoPlace, editedDaily, productId);
		
		boolean isEditSuccess = output.isEditSuccess();
		assertTrue(isEditSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditNotExistSprint() {
		String editedSprintId = null;
		String editedGoal = "Implement the function of editing sprint.";
		String editedStartDate = "2018-04-09";
		int editedInterval = 3;
		String editedEndDate = "2018-04-29";
		String editedDemoDate = "2018-04-29";
		String editedDemoPlace = "03F Room";
		String editedDaily = "15:00 1323";
		
		EditSprintOutput output = editSprint(editedSprintId, editedGoal, editedInterval,
				editedStartDate, editedEndDate, editedDemoDate, editedDemoPlace, editedDaily, productId);
		
		boolean isEditSuccess = output.isEditSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the sprint is not exist!";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditSprintWithNullParamemters() {
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-23";
		String endDate = "2018-05-06";
		int interval = 2;
		String demoDate = "2018-05-06";
		
		Sprint editedSprint = testFactory.newSprint(goal, interval, startDate, endDate, demoDate, productId);
		
		String editedSprintId = editedSprint.getSprintId();
		String editedGoal = null;
		String editedStartDate = null;
		int editedInterval = 0;
		String editedEndDate = null;
		String editedDemoDate = null;
		String editedDemoPlace = null;
		String editedDaily = null;
		
		EditSprintOutput output = editSprint(editedSprintId, editedGoal, editedInterval,
			editedStartDate, editedEndDate, editedDemoDate, editedDemoPlace, editedDaily, productId);
		
		boolean isEditSuccess = output.isEditSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The goal of the sprint should be required!\n" +
				"The interval of the sprint should not be zero.\n" +
				"The start date of the sprint should be required!\n" +
				"The demo date of the sprint should be required!\n";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditSprintWithEmptyParamemters() {
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-23";
		String endDate = "2018-05-06";
		int interval = 2;
		String demoDate = "2018-05-06";
		
		Sprint editedSprint = testFactory.newSprint(goal, interval, startDate, endDate, demoDate, productId);
		
		String editedSprintId = editedSprint.getSprintId();
		String editedGoal = "";
		String editedStartDate = "";
		int editedInterval = 0;
		String editedEndDate = "";
		String editedDemoDate = "";
		String editedDemoPlace = "";
		String editedDaily = "";
		
		EditSprintOutput output = editSprint(editedSprintId, editedGoal, editedInterval,
			editedStartDate, editedEndDate, editedDemoDate, editedDemoPlace, editedDaily, productId);
		
		boolean isEditSuccess = output.isEditSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The goal of the sprint should be required!\n" +
				"The interval of the sprint should not be zero.\n" +
				"The start date of the sprint should be required!\n" +
				"The demo date of the sprint should be required!\n";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_Success_When_DeleteSprint() {
		String goal = "Implement the function of creating sprint.";
		int interval = 2;
		String startDate = "2018-05-15";
		String endDate = "2018-05-28";
		String demoDate = "2018-05-28";
		
		Sprint deletedSprint = testFactory.newSprint(goal, interval, startDate, endDate, demoDate, productId);
		
		String deletedSprintId = deletedSprint.getSprintId();
		
		DeleteSprintOutput output = deleteSprint(deletedSprintId);
		
		boolean isDeleteSuccess = output.isDeleteSuccess();
		assertTrue(isDeleteSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_DeleteNotExistSprint() {
		String deletedSprintId = null;
		
		DeleteSprintOutput output = deleteSprint(deletedSprintId);
		
		boolean isDeleteSuccess = output.isDeleteSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the sprint is not exist!";
		assertFalse(isDeleteSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_OrderIdUpdated_When_DeleteSpint() {
		String[] goal = {
				"This is the goal of Sprint#1",
				"This is the goal of Sprint#2",
				"This is the goal of Sprint#3"
		};
		int interval = 2;
		String[] startDate = {"2018-05-15", "2018-05-29", "2018-06-15"};
		String[] endDate = {"2018-05-28", "2018-06-14", "2018-06-28"};
		String[] demoDate = {"2018-05-28", "2018-06-14", "2018-06-28"};
		
		int numberOfNewSprints = goal.length;
		
		List<Sprint> sprintList = new ArrayList<>();
		for(int i = 0; i < numberOfNewSprints; i++) {
			Sprint sprint = testFactory.newSprint(goal[i], interval, startDate[i], endDate[i], demoDate[i], productId);
			sprintList.add(sprint);
		}
		
		String deletedSprintId = sprintList.get(1).getSprintId();
		deleteSprint(deletedSprintId);
		
		GetSprintsByProductIdOutput output = getSprintsByProductId(productId);
		List<SprintModel> sprintListAfterDeleted = output.getSprintList();
		
		for(int i = 0; i < sprintListAfterDeleted.size(); i++) {
			assertEquals(i + 1, sprintListAfterDeleted.get(i).getOrderId());
		}
	}
	
	@Test
	public void Should_ReturnOverlapMessage_When_AddOverlapSprint() {
		String goal = "I hope overlap with other sprint.";
		String startDate = "2018-04-23";
		String endDate = "2018-05-06";
		int interval = 2;
		String demoDate = "2018-05-06";
		
		testFactory.newSprint(goal, interval, startDate, endDate, demoDate, productId);
		
		String overlapGoal = "I hope overlap with other sprint after adding the sprint.";
		String overlapStartDate = "2018-04-22";
		String overlapEndDate = "2018-05-05";
		int overlapInterval = 2;
		String overlapDemoDate = "2018-05-05";
		
		AddSprintOutput output = addNewSprint(overlapGoal, overlapInterval, overlapStartDate, overlapEndDate, overlapDemoDate, productId);
		
		boolean isAddSuccess = output.isAddSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the start date or the end date is overlap with the other sprint!";
		assertFalse(isAddSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_Success_When_EditOverlapSprint() {
		String goal = "I hope overlap with other sprint.";
		String startDate = "2018-04-23";
		String endDate = "2018-05-06";
		int interval = 2;
		String demoDate = "2018-05-06";
		
		testFactory.newSprint(goal, interval, startDate, endDate, demoDate, productId);
		
		String overlapGoal = "I hope overlap with other sprint after adding the sprint.";
		String overlapStartDate = "2019-04-09";
		String overlapEndDate = "2019-04-22";
		int overlapInterval = 2;
		String overlapDemoDate = "2019-04-22";
		
		Sprint editedOverlapSprint = testFactory.newSprint(overlapGoal, overlapInterval, overlapStartDate, overlapEndDate, overlapDemoDate, productId);
		
		String editedOverlapSprintId = editedOverlapSprint.getSprintId();
		String editedOverlapGoal = "I hope overlap with other sprint after editing the sprint.";
		String editedOverlapStartDate = "2018-04-22";
		String editedOverlapEndDate = "2018-05-05";
		int editedOverlapInterval = 2;
		String editedOverlapDemoDate = "2018-05-05";
		
		EditSprintOutput output = editSprint(editedOverlapSprintId, editedOverlapGoal, editedOverlapInterval, editedOverlapStartDate, editedOverlapEndDate, editedOverlapDemoDate, "", "", productId);
		
		boolean isEditSuccess = output.isEditSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the start date or the end date is overlap with the other sprint!";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_AddSprintWithStartDateAfterEndDate() {
		String goal = "Implement the function of creating sprint.";
		int interval = 2;
		String startDate = "2018-05-15";
		String endDate = "2018-05-14";
		String demoDate = "2018-05-28";
		
		AddSprintOutput output = addNewSprint(goal, interval, startDate, endDate, demoDate, productId);
		
		boolean isAddSuccess = output.isAddSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the start date must be before the end date!";
		assertFalse(isAddSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditSprintWithStartDateAfterEndDate() {
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-23";
		String endDate = "2018-05-06";
		int interval = 2;
		String demoDate = "2018-05-06";
		
		Sprint editedSprint = testFactory.newSprint(goal, interval, startDate, endDate, demoDate, productId);
		
		String editedSprintId = editedSprint.getSprintId();
		String editedGoal = "Implement the function of editing sprint.";
		String editedStartDate = "2018-05-07";
		int editedInterval = 3;
		String editedEndDate = "2018-05-06";
		String editedDemoDate = "2018-05-27";
		String editedDemoPlace = "03F Room";
		String editedDaily = "15:00 1323";
		
		EditSprintOutput output = editSprint(editedSprintId, editedGoal, editedInterval,
			editedStartDate, editedEndDate, editedDemoDate, editedDemoPlace, editedDaily, productId);
		
		boolean isEditSuccess = output.isEditSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the start date must be before the end date!";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_AddSprintWithStartDateAfterDemoDate() {
		String goal = "Implement the function of creating sprint.";
		int interval = 2;
		String startDate = "2018-05-15";
		String endDate = "2018-05-28";
		String demoDate = "2018-05-14";
		
		AddSprintOutput output = addNewSprint(goal, interval, startDate, endDate, demoDate, productId);
		
		boolean isAddSuccess = output.isAddSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the start date must be before the demo date!";
		assertFalse(isAddSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditSprintWithStartDateAfterDemoDate() {
		String goal = "Implement the function of creating sprint.";
		String startDate = "2018-04-23";
		String endDate = "2018-05-06";
		int interval = 2;
		String demoDate = "2018-05-06";
		
		Sprint editedSprint = testFactory.newSprint(goal, interval, startDate, endDate, demoDate, productId);
		
		String editedSprintId = editedSprint.getSprintId();
		String editedGoal = "Implement the function of editing sprint.";
		String editedStartDate = "2018-05-07";
		int editedInterval = 3;
		String editedEndDate = "2018-05-27";
		String editedDemoDate = "2018-05-06";
		String editedDemoPlace = "03F Room";
		String editedDaily = "15:00 1323";
		
		EditSprintOutput output = editSprint(editedSprintId, editedGoal, editedInterval,
			editedStartDate, editedEndDate, editedDemoDate, editedDemoPlace, editedDaily, productId);
		
		boolean isEditSuccess = output.isEditSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the start date must be before the demo date!";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_Success_When_EditRetrospective() {
		String goal = "Implement editing the retrospective of the sprint.";
		String startDate = "2018-04-23";
		String endDate = "2018-05-06";
		int interval = 2;
		String demoDate = "2018-05-06";
		
		Sprint sprint = testFactory.newSprint(goal, interval, startDate, endDate, demoDate, productId);
		
		String editedDescription = "The good thing is we have the machine to finish our thing automatally. So funny~";
		
		EditRetrospectiveOutput output = editRetrospective(editedDescription, sprint.getSprintId());
		
		boolean isEditSuccess = output.isEditSuccess();
		assertTrue(isEditSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_EditRetrospectiveWithNotExistSprint() {
		String editedDescription = "The good thing is we have the machine to finish our thing automatally. So funny~";
		String editedSprintId = "2";
		
		EditRetrospectiveOutput output = editRetrospective(editedDescription, editedSprintId);
		
		boolean isEditSuccess = output.isEditSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the sprint is not exist!";
		assertFalse(isEditSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	private AddSprintOutput addNewSprint(String goal, int interval, String startDate, String endDate, String demoDate, String productId) {
		AddSprintUseCase addSprintUseCase = new AddSprintUseCaseImpl(fakeSprintRepository);
		AddSprintInput input = (AddSprintInput) addSprintUseCase;
		input.setGoal(goal);
		input.setInterval(interval);
		input.setStartDate(startDate);
		input.setEndDate(endDate);
		input.setDemoDate(demoDate);
		input.setDemoPlace("Lab1321");
		input.setDaily("10:00 Lab1321");
		input.setProductId(productId);
		AddSprintOutput output = new AddSprintRestfulAPI();
		addSprintUseCase.execute(input, output);
		return output;
		
	}
	
	private GetSprintsByProductIdOutput getSprintsByProductId(String productId) {
		GetSprintsByProductIdUseCase getSprintsByProductIdUseCase = new GetSprintsByProductIdUseCaseImpl(fakeSprintRepository);
		GetSprintsByProductIdInput input = (GetSprintsByProductIdInput) getSprintsByProductIdUseCase;
		input.setProductId(productId);
		GetSprintsByProductIdOutput output = new GetSprintsByProductIdRestfulAPI();
		getSprintsByProductIdUseCase.execute(input, output);
		return output;
	}
	
	private EditSprintOutput editSprint(String sprintId, String editedGoal, int editedInterval, String editedStartDate, 
			String editedEndDate, String editedDemoDate, String editedDemoPlace, String editedDaily, String productId) {
		EditSprintUseCase editSprintUseCase = new EditSprintUseCaseImpl(fakeSprintRepository);
		EditSprintInput input = (EditSprintInput) editSprintUseCase;
		input.setSprintId(sprintId);
		input.setGoal(editedGoal);
		input.setInterval(editedInterval);
		input.setStartDate(editedStartDate);
		input.setEndDate(editedEndDate);
		input.setDemoDate(editedDemoDate);
		input.setDemoPlace(editedDemoPlace);
		input.setDaily(editedDaily);
		input.setProductId(productId);
		EditSprintOutput output = new EditSprintRestfulAPI();
		editSprintUseCase.execute(input, output);
		return output;
	}
	
	private DeleteSprintOutput deleteSprint(String sprintId) {
		DeleteSprintUseCase deleteSprintUseCase = new DeleteSprintUseCaseImpl(fakeSprintRepository);
		DeleteSprintInput input = (DeleteSprintInput) deleteSprintUseCase;
		input.setSprintId(sprintId);
		DeleteSprintOutput output = new DeleteSprintRestfulAPI();
		deleteSprintUseCase.execute(input, output);
		return output;
	}
	
	private EditRetrospectiveOutput editRetrospective(String editedDescription, String sprintId) {
		EditRetrospectiveUseCase editRetrospectiveUseCase = new EditRetrospectiveUseCaseImpl(fakeSprintRepository);
		EditRetrospectiveInput input = (EditRetrospectiveInput) editRetrospectiveUseCase;
		input.setRetrospective(editedDescription);
		input.setSprintId(sprintId);
		EditRetrospectiveOutput output = new EditRetrospectiveRestfulAPI();
		editRetrospectiveUseCase.execute(input, output);
		return output;
	}
}
