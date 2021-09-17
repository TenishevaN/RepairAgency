package com.my.command;

import com.my.Path;

import com.my.db.dao.PaymentDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.Role;
import com.my.db.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class CardUserPageCommand implements Command {

    private static final Logger log = Logger.getLogger(CardUserPageCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        User user;
        HttpSession session = req.getSession(false);
        try {
            UserDAO userDAO = new UserDAO();
            String id = req.getParameter("id");

            if (id.isEmpty()) {
                User userSession = (User) session.getAttribute("user");
                user = userDAO.get(userSession.getId());
            } else {
                user = userDAO.get(Integer.parseInt(id));
            }
            req.setAttribute("user", user);

            Role userRole = (Role) session.getAttribute("role");
            req.setAttribute("role", userRole.getName());
            BigDecimal total = new PaymentDAO().getTotal(user.getInvoiceId());
            req.setAttribute("total", total);
        } catch (Exception ex) {
            log.debug("exception user " + ex.getMessage());
        }

        return Path.PAGE_USER;
    }
}