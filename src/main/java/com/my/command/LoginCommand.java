package com.my.command;

import com.my.Path;
import com.my.db.model.Role;
import com.my.db.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;
import com.my.db.dao.UserDAO;

/**
 * Login command.
 * 
 * @author Tenisheva N.
 * 
 */
public class LoginCommand implements Command {

	private static final long serialVersionUID = -3071536593627692473L;
	
	private static final Logger log = Logger.getLogger(String.valueOf(LoginCommand.class));
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
	//	log.debug("Command starts");
		
		HttpSession session = request.getSession();
		
		// obtain login and password from the request
		String login = request.getParameter("login");
	//	log.trace("Request parameter: loging --> " + login);
		
		String password = request.getParameter("password");
		
		// error handler
		String errorMessage = null;		
		String forward = Path.PAGE_ERROR_PAGE;
		
		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			errorMessage = "Login/password cannot be empty";
			request.setAttribute("errorMessage", errorMessage);
		//	log.error("errorMessage --> " + errorMessage);
			return forward;
		}
		UserDAO userDAO = new UserDAO();
		User user = userDAO.get(login);
	//	log.trace("Found in DB: user --> " + user);
			
		if (user == null || !password.equals(user.getPassword())) {
			errorMessage = "Cannot find user with such login/password";
			request.setAttribute("errorMessage", errorMessage);
		//	log.error("errorMessage --> " + errorMessage);
			return forward;
		} else {
			Role userRole = Role.getRole(user);
			System.out.println("userRole ---" + userRole);
		//	log.trace("userRole --> " + userRole);
				
//			if (userRole == Role.USER) {
//				forward = Path.COMMAND_LIST_REQUESTS;
//			} else{
				forward = Path.COMMAND_MAIN_PAGE;
		//	}
			session.setAttribute("user", user);
		//	log.trace("Set the session attribute: user --> " + user);
				
			session.setAttribute("role", userRole);
		//	log.trace("Set the session attribute: userRole --> " + userRole);
				
			log.info("User " + user + " logged as " + userRole.toString().toLowerCase());
			
//			// work with i18n
//			String userLocaleName = user.getLocaleName();
//		//	log.trace("userLocalName --> " + userLocaleName);
//
//			if (userLocaleName != null && !userLocaleName.isEmpty()) {
//				Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);
//
//				session.setAttribute("defaultLocale", userLocaleName);
//		//		log.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);
//
//				log.info("Locale for user: defaultLocale --> " + userLocaleName);
//			}
		}
		
	//	log.debug("Command finished");
		return forward;
	}

}