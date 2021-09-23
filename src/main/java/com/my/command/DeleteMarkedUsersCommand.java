package com.my.command;

import com.my.Path;
import com.my.db.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteMarkedUsersCommand implements Command {

    private static final Logger log =  LogManager.getLogger(DeleteMarkedUsersCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            UserDAO userDAO = new UserDAO();
            userDAO.deleteMarkedUsers();
        } catch (Exception ex) {
            log.debug("delete marked usere exception exception {}", ex.getMessage());
            return  Path.PAGE_ERROR_PAGE;
        }
        return Path.SERVICE_PAGE;
    }
}
