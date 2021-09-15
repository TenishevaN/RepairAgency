package com.my.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.my.command.*;
import com.my.db.dao.UserDAO;
import com.my.db.model.User;
import com.my.web.filter.CustomFilter;
import org.apache.log4j.Logger;

@WebServlet("/controller")
public final class Controller extends HttpServlet {

	private static final Logger log = Logger.getLogger(Controller.class);
	public static List<User> masterList;

	public Controller(){
		UserDAO userDAO = new UserDAO();
		masterList = userDAO.getMasterList();
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String address = doProcess(req, resp);
		req.getRequestDispatcher(address).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

		String address = doProcess(req, resp);
		System.out.println("address "+ address);
		resp.sendRedirect(address);
	}

	 private String doProcess(HttpServletRequest req, HttpServletResponse resp){

		  String commmandName = req.getParameter("command");
		  log.debug("commmandName ==> " + commmandName);

		  Command command = CommandContainer.getCommmand(commmandName);
		  log.debug("command ==> " + command);
		  String address = "error.jsp";
		  try {
			  address = command.execute(req, resp);
		  } catch (Exception ex) {
			  req.setAttribute("errorMessage", ex.getMessage());
		  }
          return address;
	  }
}
