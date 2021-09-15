package com.my.command;

import java.util.*;
import javax.servlet.http.*;
import com.my.db.dao.UserDAO;
import com.my.db.model.User;

public class ListUsersCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {

		try {
			UserDAO userDAO = new UserDAO();
	    	List<User> users = userDAO.getAll();
		    req.setAttribute("users", users);
			System.out.println("Result from DB " + users);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "listUsers.jsp";
	}

}
