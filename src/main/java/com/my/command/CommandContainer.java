package com.my.command;

import java.util.*;

public class CommandContainer {
	
	private static Map<String, Command> commands;
	
	static {
		commands = new HashMap<>();
		commands.put("error", new ErrorCommand());
		commands.put("login", new LoginCommand());
		commands.put("insertUser", new InsertUserCommand());
		commands.put("listUsers", new ListUsersCommand());
		commands.put("listRequests", new ListRequestsCommand());
	    commands.put("updateRepairRequest", new UpdateRepairRequestCommand());
		commands.put("insertRepairRequest", new InsertRepairRequestCommand());
		commands.put("openCardRepairRequest", new CardRepairRequestPageCommand());
		commands.put("insertReview", new InsertReviewCommand());
		commands.put("openCardUser", new CardUserPageCommand());
		commands.put("updateCardUser", new UpdateUserCommand());
		commands.put("reports", new ReportPageCommand());
		commands.put("sortListRequestsByDate", new SortListRequestsByDateCommand());
		commands.put("sortListRequestsByCost", new SortListRequestsByCostCommand());
		commands.put("sortListRequestsByStatus", new SortListRequestsByStatusCommand());
	    commands.put("filtertListRequestsByStatus", new FilterListRequestsByStatusCommand());
		commands.put("filtertListRequestsByMaster", new FilterListRequestsByMasterCommand());
	}

	public static Command getCommmand(String commmandName) {
		System.out.println("CommmandName " +commmandName);
		return commands.get(commmandName);
	}
}
