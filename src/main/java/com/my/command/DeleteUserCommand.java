package com.my.command;

import com.my.Path;
import com.my.db.dao.UserDAO;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code DeleteUserCommand} class represents the implementation of the command to delete user.
 */
public class DeleteUserCommand implements Command {

    private static final Logger log = LogManager.getLogger(DeleteUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.get(Integer.parseInt(req.getParameter("id")));
            userDAO.delete(user);
            return Path.COMMAND_LIST_USERS;
        } catch (Exception ex) {
            log.debug("delete user exception " + ex.getMessage());
            return Path.PAGE_ERROR_PAGE;
        }
    }
}
