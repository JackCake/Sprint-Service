package ntut.csie.sprintService.gateways.repository.sprint;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ntut.csie.sprintService.gateways.database.CommittedBacklogItemTable;
import ntut.csie.sprintService.gateways.database.SprintTable;
import ntut.csie.sprintService.gateways.database.SqlDatabaseHelper;
import ntut.csie.sprintService.gateways.repository.sprint.committedBacklogItem.CommittedBacklogItemData;
import ntut.csie.sprintService.gateways.repository.sprint.committedBacklogItem.CommittedBacklogItemMapper;
import ntut.csie.sprintService.model.sprint.CommittedBacklogItem;
import ntut.csie.sprintService.model.sprint.Sprint;
import ntut.csie.sprintService.useCase.sprint.SprintRepository;

public class MySqlSprintRepositoryImpl implements SprintRepository {
	private SqlDatabaseHelper sqlDatabaseHelper;
	private SprintMapper sprintMapper;
	private CommittedBacklogItemMapper committedBacklogItemMapper;
	
	public MySqlSprintRepositoryImpl() {
		sqlDatabaseHelper = new SqlDatabaseHelper();
		sprintMapper = new SprintMapper();
		committedBacklogItemMapper = new CommittedBacklogItemMapper();
	}

	@Override
	public synchronized void save(Sprint sprint) throws Exception {
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		try {
			sqlDatabaseHelper.transactionStart();
			//當記憶體中的sprint下的committedBacklogItem被移除時，那麼資料庫也必須被同步刪除
			Sprint sprintInDatabase = getSprintById(sprint.getSprintId());
			if(sprintInDatabase != null) {
				Set<String> backlogItemIds = new HashSet<>();
				for(CommittedBacklogItem committedBacklogItem : sprint.getCommittedBacklogItems()) {
					backlogItemIds.add(committedBacklogItem.getBacklogItemId());
				}
				for(CommittedBacklogItem committedBacklogItem : sprintInDatabase.getCommittedBacklogItems()) {
					if(!backlogItemIds.contains(committedBacklogItem.getBacklogItemId())) {
						removeCommittedBacklogItem(committedBacklogItem);
					}
				}
				
				//開始儲存
				for(CommittedBacklogItem committedBacklogItem : sprint.getCommittedBacklogItems()) {
					addCommittedBacklogItem(committedBacklogItem);
				}
			}
			
			SprintData data = sprintMapper.transformToSprintData(sprint);
			String sql = String.format("Insert Into %s Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
					+ "On Duplicate Key Update %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?",
					SprintTable.tableName, SprintTable.orderId, SprintTable.goal, SprintTable.sprintInterval, 
					SprintTable.startDate, SprintTable.endDate, SprintTable.demoDate, 
					SprintTable.demoPlace, SprintTable.daily, SprintTable.retrospective);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, data.getSprintId());
			preparedStatement.setInt(2, data.getOrderId());
			preparedStatement.setString(3, data.getGoal());
			preparedStatement.setInt(4, data.getInterval());
			preparedStatement.setString(5, data.getStartDate());
			preparedStatement.setString(6, data.getEndDate());
			preparedStatement.setString(7, data.getDemoDate());
			preparedStatement.setString(8, data.getDemoPlace());
			preparedStatement.setString(9, data.getDaily());
			preparedStatement.setString(10, data.getProductId());
			preparedStatement.setString(11, data.getRetrospective());
			preparedStatement.setInt(12, data.getOrderId());
			preparedStatement.setString(13, data.getGoal());
			preparedStatement.setInt(14, data.getInterval());
			preparedStatement.setString(15, data.getStartDate());
			preparedStatement.setString(16, data.getEndDate());
			preparedStatement.setString(17, data.getDemoDate());
			preparedStatement.setString(18, data.getDemoPlace());
			preparedStatement.setString(19, data.getDaily());
			preparedStatement.setString(20, data.getRetrospective());
			preparedStatement.executeUpdate();
			sqlDatabaseHelper.transactionEnd();
		} catch(SQLException e) {
			sqlDatabaseHelper.transactionError();
			e.printStackTrace();
			throw new Exception("Sorry, there is the problem when save the sprint. Please try again!");
		} finally {
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
	}

	@Override
	public synchronized void remove(Sprint sprint) throws Exception {
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		try {
			sqlDatabaseHelper.transactionStart();
			for(CommittedBacklogItem committedBacklogItem : sprint.getCommittedBacklogItems()) {
				removeCommittedBacklogItem(committedBacklogItem);
			}
			
			String sql = String.format("Delete From %s Where %s = '%s'",
					SprintTable.tableName,
					SprintTable.sprintId,
					sprint.getSprintId());
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.executeUpdate();
			sqlDatabaseHelper.transactionEnd();
		} catch(SQLException e) {
			sqlDatabaseHelper.transactionError();
			e.printStackTrace();
			throw new Exception("Sorry, there is the problem when remove the sprint. Please try again!");
		} finally {
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
	}

	@Override
	public synchronized Sprint getSprintById(String sprintId) {
		if(!sqlDatabaseHelper.isTransacting()) {
			sqlDatabaseHelper.connectToDatabase();
		}
		ResultSet resultSet = null;
		Sprint sprint = null;
		try {
			String query = String.format("Select * From %s Where %s = '%s'",
					SprintTable.tableName,
					SprintTable.sprintId,
					sprintId);
			resultSet = sqlDatabaseHelper.getResultSet(query);
			if (resultSet.first()) {
				int orderId = resultSet.getInt(SprintTable.orderId);
				String goal = resultSet.getString(SprintTable.goal);
				int interval = resultSet.getInt(SprintTable.sprintInterval);
				String startDate = resultSet.getString(SprintTable.startDate);
				String endDate = resultSet.getString(SprintTable.endDate);
				String demoDate = resultSet.getString(SprintTable.demoDate);
				String demoPlace = resultSet.getString(SprintTable.demoPlace);
				String daily = resultSet.getString(SprintTable.daily);
				String productId = resultSet.getString(SprintTable.productId);
				String retrospective = resultSet.getString(SprintTable.retrospective);
				
				SprintData data = new SprintData();
				data.setSprintId(sprintId);
				data.setOrderId(orderId);
				data.setGoal(goal);
				data.setInterval(interval);
				data.setStartDate(startDate);
				data.setEndDate(endDate);
				data.setDemoDate(demoDate);
				data.setDemoPlace(demoPlace);
				data.setDaily(daily);
				data.setProductId(productId);
				data.setCommittedBacklogItemDatas(getCommittedBacklogItemDatasBySprintId(sprintId));
				data.setRetrospective(retrospective);

				sprint = sprintMapper.transformToSprint(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sqlDatabaseHelper.closeResultSet(resultSet);
			if(!sqlDatabaseHelper.isTransacting()) {
				sqlDatabaseHelper.releaseConnection();
			}
		}
		return sprint;
	}
	
	@Override
	public synchronized Collection<Sprint> getSprintsByProductId(String productId) {
		if(!sqlDatabaseHelper.isTransacting()) {
			sqlDatabaseHelper.connectToDatabase();
		}
		ResultSet resultSet = null;
		Collection<Sprint> sprints = new ArrayList<>();
		try {
			String query = String.format("Select * From %s Where %s = '%s' Order By %s",
					SprintTable.tableName, 
					SprintTable.productId, 
					productId, 
					SprintTable.orderId);
			resultSet = sqlDatabaseHelper.getResultSet(query);
			while (resultSet.next()) {
				String sprintId = resultSet.getString(SprintTable.sprintId);
				int orderId = resultSet.getInt(SprintTable.orderId);
				String goal = resultSet.getString(SprintTable.goal);
				int interval = resultSet.getInt(SprintTable.sprintInterval);
				String startDate = resultSet.getString(SprintTable.startDate);
				String endDate = resultSet.getString(SprintTable.endDate);
				String demoDate = resultSet.getString(SprintTable.demoDate);
				String demoPlace = resultSet.getString(SprintTable.demoPlace);
				String daily = resultSet.getString(SprintTable.daily);
				String retrospective = resultSet.getString(SprintTable.retrospective);
				
				SprintData data = new SprintData();
				data.setSprintId(sprintId);
				data.setOrderId(orderId);
				data.setGoal(goal);
				data.setInterval(interval);
				data.setStartDate(startDate);
				data.setEndDate(endDate);
				data.setDemoDate(demoDate);
				data.setDemoPlace(demoPlace);
				data.setDaily(daily);
				data.setProductId(productId);
				data.setCommittedBacklogItemDatas(getCommittedBacklogItemDatasBySprintId(sprintId));
				data.setRetrospective(retrospective);

				Sprint sprint = sprintMapper.transformToSprint(data);
				sprints.add(sprint);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sqlDatabaseHelper.closeResultSet(resultSet);
			if(!sqlDatabaseHelper.isTransacting()) {
				sqlDatabaseHelper.releaseConnection();
			}
		}
		return sprints;
	}
	
	private void addCommittedBacklogItem(CommittedBacklogItem committedBacklogItem) throws SQLException {
		CommittedBacklogItemData data = committedBacklogItemMapper.transformToCommittedBacklogItemData(committedBacklogItem);
		String sql = String.format("Insert Into %s Values (?, ?) On Duplicate Key Update %s=?",
				CommittedBacklogItemTable.tableName, CommittedBacklogItemTable.sprintId);
		PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
		preparedStatement.setString(1, data.getBacklogItemId());
		preparedStatement.setString(2, data.getSprintId());
		preparedStatement.setString(3, data.getSprintId());
		preparedStatement.executeUpdate();
		sqlDatabaseHelper.closePreparedStatement(preparedStatement);
	}

	private void removeCommittedBacklogItem(CommittedBacklogItem committedBacklogItem) throws SQLException {
		String sql = String.format("Delete From %s Where %s = '%s'",
				CommittedBacklogItemTable.tableName,
				CommittedBacklogItemTable.backlogItemId,
				committedBacklogItem.getBacklogItemId());
		PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
		preparedStatement.executeUpdate();
		sqlDatabaseHelper.closePreparedStatement(preparedStatement);
	}
	
	private Collection<CommittedBacklogItemData> getCommittedBacklogItemDatasBySprintId(String sprintId) {
		ResultSet resultSet = null;
		Collection<CommittedBacklogItemData> committedBacklogItemDatas = new ArrayList<>();
		try {
			String query = String.format("Select * From %s Where %s = '%s'",
					CommittedBacklogItemTable.tableName, 
					CommittedBacklogItemTable.sprintId, 
					sprintId);
			resultSet = sqlDatabaseHelper.getResultSet(query);
			while (resultSet.next()) {
				String backlogItemId = resultSet.getString(CommittedBacklogItemTable.backlogItemId);
				
				CommittedBacklogItemData data = new CommittedBacklogItemData();
				data.setBacklogItemId(backlogItemId);
				data.setSprintId(sprintId);

				committedBacklogItemDatas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sqlDatabaseHelper.closeResultSet(resultSet);
		}
		return committedBacklogItemDatas;
	}
}
