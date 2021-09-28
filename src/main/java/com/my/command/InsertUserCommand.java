package com.my.command;

import com.my.Path;
import com.my.ServiceUtil;
import com.my.db.dao.AccountLocalizationDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.Role;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code InsertUserCommand} class represents the implementation of the command to insert user into the database.
 */
public class InsertUserCommand implements Command {

    private static final Logger log =  LogManager.getLogger(InsertUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        boolean inserted;
        String currentLocale = (String) req.getAttribute("currentLocale");
        if (currentLocale == null) {
            req.getSession().setAttribute("currentLocale", "en");
        }
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setName(req.getParameter("name"));
        user.setEmail(email);
        user.setRoleId(4);

        try {
            UserDAO userDAO = new UserDAO();
            inserted = userDAO.insert(user);
        } catch (Exception ex) {
            log.debug("insert user exception "+ex.getMessage());
            req.setAttribute("errorMessage",  ServiceUtil.getKey("insert_user_exception", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }

        if (!inserted){
            req.setAttribute("errorMessage",  ServiceUtil.getKey("insert_user_exception", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }

        int userId = user.getId();
        System.out.println("userId " + userId);
        AccountLocalizationDAO accountLocalizationDAO = new AccountLocalizationDAO();
        accountLocalizationDAO.insert(userId, 1);
        accountLocalizationDAO.insert(userId, 2);
        accountLocalizationDAO.insert(userId, 3);

        Role userRole = Role.getRole(user);
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        session.setAttribute("role", userRole);
        log.info("User " + user + " logged as " + userRole.toString().toLowerCase());
        return Path.COMMAND_MAIN_PAGE;
    }
}
