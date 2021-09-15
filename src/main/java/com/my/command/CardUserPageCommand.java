package com.my.command;

import com.my.Path;

import com.my.db.dao.RepairRequestDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.RepairRequest;
import com.my.db.model.Role;
import com.my.db.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CardUserPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            UserDAO userDAO = new UserDAO();

            User user = userDAO.get(Integer.parseInt(req.getParameter("id")));

            req.setAttribute("user", user);
            HttpSession session = req.getSession(false);
            Role userRole = (Role) session.getAttribute("role");
            req.setAttribute("role", userRole.getName());

        } catch (Exception ex) {
               System.out.println("exception user "+ ex.getMessage());
        }

        return Path.PAGE_USER;
    }
}