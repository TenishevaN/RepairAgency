package com.my.command;

import java.util.*;
import javax.servlet.http.*;

import com.my.Path;
import com.my.ServiceUtil;
import com.my.db.dao.UserDAO;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@ code ListUsersCommand} class represents the implementation of the command to obtain list of users from the database.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class ListUsersCommand implements Command {

    private static final Logger log = LogManager.getLogger(ListUsersCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");
        try {
            UserDAO userDAO = new UserDAO();
            List<User> users = userDAO.getAll();
            req.setAttribute("users", users);
        } catch (Exception ex) {
            log.debug("Get Users exception " + ex.getMessage());
            req.setAttribute("errorMessage", ServiceUtil.getKey("contact_manager", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }

        return Path.PAGE_USER_LIST;
    }

}
