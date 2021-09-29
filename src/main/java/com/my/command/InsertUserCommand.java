package com.my.command;

import com.my.EmailService;
import com.my.Path;
import com.my.Security;
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
 * {@ code InsertUserCommand} class represents the implementation of the command to insert user into the database.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class InsertUserCommand implements Command {

    private static final Logger log = LogManager.getLogger(InsertUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        boolean inserted;
        HttpSession session = req.getSession();
        String currentLocale = getLocale(session);

        User user = createUser(req);
        try {
            UserDAO userDAO = new UserDAO();
            inserted = userDAO.insert(user);
        } catch (Exception ex) {
            log.debug("insert user exception " + ex.getMessage());
            req.setAttribute("errorMessage", ServiceUtil.getKey("insert_user_exception", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }
        if (!inserted) {
            req.setAttribute("errorMessage", ServiceUtil.getKey("insert_user_exception", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }
        int userId = user.getId();
        addAccountLocalization(userId);

        Role userRole = Role.getRole(user);
        session.setAttribute("user", user);
        session.setAttribute("role", userRole);
        log.info("User " + user + " logged as " + userRole.toString().toLowerCase());

        //email notification
        new EmailService(user, currentLocale);
        return Path.COMMAND_MAIN_PAGE;
    }

    private String getLocale(HttpSession session) {

        String currentLocale = (String) session.getAttribute("currentLocale");
        if (currentLocale == null) {
            currentLocale = "en";
            session.setAttribute("currentLocale", currentLocale);

        }
        return currentLocale;
    }

    private void addAccountLocalization(int userId) {
        AccountLocalizationDAO accountLocalizationDAO = new AccountLocalizationDAO();
        accountLocalizationDAO.insert(userId, 1);
        accountLocalizationDAO.insert(userId, 2);
        accountLocalizationDAO.insert(userId, 3);
    }

    private User createUser(HttpServletRequest req) {

        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        password = Security.encrypt(password);

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setName(req.getParameter("name"));
        user.setEmail(email);
        user.setRoleId(4);
        return user;
    }

}
