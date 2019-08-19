package ntut.csie.sprintService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import ntut.csie.sprintService.gateways.database.SqlDatabaseHelper;
import ntut.csie.sprintService.useCase.DomainEventListener;

@SuppressWarnings("serial")
public class SprintServiceStart extends HttpServlet implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Sprint Service Start!");
		SqlDatabaseHelper sqlDatabaseHelper = new SqlDatabaseHelper();
		sqlDatabaseHelper.initialize();
		ApplicationContext context = ApplicationContext.getInstance();
		DomainEventListener.getInstance().init(context.newEventStore());
	}
}