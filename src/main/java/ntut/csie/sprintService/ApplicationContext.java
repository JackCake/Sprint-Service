package ntut.csie.sprintService;

import ntut.csie.sprintService.gateways.repository.event.MySqlEventStoreImpl;
import ntut.csie.sprintService.gateways.repository.sprint.MySqlSprintRepositoryImpl;
import ntut.csie.sprintService.useCase.EventStore;
import ntut.csie.sprintService.useCase.history.get.GetHistoriesByBacklogItemIdUseCase;
import ntut.csie.sprintService.useCase.history.get.GetHistoriesByBacklogItemIdUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.SprintRepository;
import ntut.csie.sprintService.useCase.sprint.add.AddSprintUseCase;
import ntut.csie.sprintService.useCase.sprint.add.AddSprintUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.commit.CommitBacklogItemToSprintUseCase;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.commit.CommitBacklogItemToSprintUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.drop.DropBacklogItemFromSprintUseCase;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.drop.DropBacklogItemFromSprintUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.get.GetCommittedBacklogItemsBySprintIdUseCase;
import ntut.csie.sprintService.useCase.sprint.committedBacklogItem.get.GetCommittedBacklogItemsBySprintIdUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.delete.DeleteSprintUseCase;
import ntut.csie.sprintService.useCase.sprint.delete.DeleteSprintUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.edit.EditSprintUseCase;
import ntut.csie.sprintService.useCase.sprint.edit.EditSprintUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.get.GetSprintsByProductIdUseCase;
import ntut.csie.sprintService.useCase.sprint.get.GetSprintsByProductIdUseCaseImpl;
import ntut.csie.sprintService.useCase.sprint.retrospective.edit.EditRetrospectiveUseCase;
import ntut.csie.sprintService.useCase.sprint.retrospective.edit.EditRetrospectiveUseCaseImpl;

public class ApplicationContext {
	private static ApplicationContext instance = null;
	
	private static SprintRepository sprintRepository = null;
	private static EventStore eventStore = null;
	
	private AddSprintUseCase addSprintUseCase;
	private GetSprintsByProductIdUseCase getSprintsByProductIdUseCase;
	private EditSprintUseCase editSprintUseCase;
	private DeleteSprintUseCase deleteSprintUseCase;
	
	private CommitBacklogItemToSprintUseCase commitBacklogItemToSprintUseCase;
	private GetCommittedBacklogItemsBySprintIdUseCase getCommittedBacklogItemsBySprintIdUseCase;
	private DropBacklogItemFromSprintUseCase dropBacklogItemFromSprintUseCase;
	private GetHistoriesByBacklogItemIdUseCase getHistoriesByBacklogItemIdUseCase;
	
	private EditRetrospectiveUseCase editRetrospectiveUseCase;
	
	private ApplicationContext() {}
	
	public static synchronized ApplicationContext getInstance() {
		if(instance == null){
			instance = new ApplicationContext();
		}
		return instance;
	}
	
	public SprintRepository newSprintRepository() {
		if(sprintRepository == null) {
			sprintRepository = new MySqlSprintRepositoryImpl();
		}
		return sprintRepository;
	}
	
	public EventStore newEventStore() {
		if(eventStore == null) {
			eventStore = new MySqlEventStoreImpl();
		}
		return eventStore;
	}
	
	public AddSprintUseCase newAddSprintUseCase() {
		addSprintUseCase = new AddSprintUseCaseImpl(newSprintRepository());
		return addSprintUseCase;
	}
	
	public GetSprintsByProductIdUseCase newGetSprintsByProductIdUseCase() {
		getSprintsByProductIdUseCase = new GetSprintsByProductIdUseCaseImpl(newSprintRepository());
		return getSprintsByProductIdUseCase;
	}
	
	public EditSprintUseCase newEditSprintUseCase() {
		editSprintUseCase = new EditSprintUseCaseImpl(newSprintRepository());
		return editSprintUseCase;
	}
	
	public DeleteSprintUseCase newDeleteSprintUseCase() {
		deleteSprintUseCase = new DeleteSprintUseCaseImpl(newSprintRepository());
		return deleteSprintUseCase;
	}
	
	public CommitBacklogItemToSprintUseCase newCommitBacklogItemToSprintUseCase() {
		commitBacklogItemToSprintUseCase = new CommitBacklogItemToSprintUseCaseImpl(newSprintRepository());
		return commitBacklogItemToSprintUseCase;
	}
	
	public GetCommittedBacklogItemsBySprintIdUseCase newGetCommittedBacklogItemsBySprintIdUseCase() {
		getCommittedBacklogItemsBySprintIdUseCase = new GetCommittedBacklogItemsBySprintIdUseCaseImpl(newSprintRepository());
		return getCommittedBacklogItemsBySprintIdUseCase;
	}
	
	public DropBacklogItemFromSprintUseCase newDropBacklogItemFromSprintUseCase() {
		dropBacklogItemFromSprintUseCase = new DropBacklogItemFromSprintUseCaseImpl(newSprintRepository());
		return dropBacklogItemFromSprintUseCase;
	}
	
	public GetHistoriesByBacklogItemIdUseCase newGetHistoriesByBacklogItemIdUseCase() {
		getHistoriesByBacklogItemIdUseCase = new GetHistoriesByBacklogItemIdUseCaseImpl(newEventStore());
		return getHistoriesByBacklogItemIdUseCase;
	}
	
	public EditRetrospectiveUseCase newEditRetrospectiveUseCase() {
		editRetrospectiveUseCase = new EditRetrospectiveUseCaseImpl(newSprintRepository());
		return editRetrospectiveUseCase;
	}
}
