package com.my.command;

import com.my.Path;
import com.my.db.model.Role;
import com.my.db.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.my.db.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Login command.
 *
 * @author Tenisheva N.
 */
public class LoginCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger log = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) {


        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        String errorMessage;
        String forward = Path.PAGE_ERROR_PAGE;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.get(login);
        log.info("Found in DB: user --> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        } else {
            Role userRole = Role.getRole(user);
            forward = Path.COMMAND_MAIN_PAGE;
            session.setAttribute("user", user);
            session.setAttribute("role", userRole);
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

        return forward;
    }

}