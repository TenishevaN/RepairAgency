package com.my.command;

import com.my.Path;
import com.my.db.dao.UserDAO;
import com.my.db.model.Role;
import com.my.db.model.User;
import com.my.web.filter.CustomFilter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InsertUserCommand implements Command {

    private static final Logger log = Logger.getLogger(InsertUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {



        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setName(req.getParameter("name"));
        user.setEmail(req.getParameter("email"));
        user.setRoleId(4);

        try {
            UserDAO userDAO = new UserDAO();
            userDAO.insert(user);

            Role userRole = Role.getRole(user);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("role", userRole);
            log.info("User " + user + " logged as " + userRole.toString().toLowerCase());
            return Path.COMMAND_MAIN_PAGE;

        } catch (Exception ex) {
            log.debug("insert user exception "+ex.getMessage());
            return  Path.PAGE_ERROR_PAGE;
        }
    }
}
