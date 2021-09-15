package com.my.command;

import com.my.Path;
import com.my.db.dao.UserDAO;
import com.my.db.model.Role;
import com.my.db.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InsertUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();
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
            session.setAttribute("user", user);
            session.setAttribute("role", userRole);
            return Path.COMMAND_MAIN_PAGE;
            //	log.trace("Set the session attribute: userRole --> " + userRole);
    //        log.info("User " + user + " logged as " + userRole.toString().toLowerCase());
        } catch (Exception ex) {
            System.out.println("insert user " + user);
            //   log.info("exception "+ex.getMessage());
            return  Path.PAGE_ERROR_PAGE;
        }
    }
}
