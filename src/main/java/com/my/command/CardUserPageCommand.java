package com.my.command;

import com.my.Path;

import com.my.ServiceUtil;
import com.my.db.dao.PaymentDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.Role;
import com.my.db.model.User;
import org.apache.logging.log4j.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class CardUserPageCommand implements Command {

    private static final Logger log =  LogManager.getLogger(CardUserPageCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        User user;
        HttpSession session = req.getSession(false);
        try {
            Role userRole = (Role) session.getAttribute("role");
            UserDAO userDAO = new UserDAO();
            String id = req.getParameter("id");

            if (id.isEmpty()) {
                User userSession = (User) session.getAttribute("user");
                user = userDAO.get(userSession.getId());
            } else {
                user = userDAO.get(Integer.parseInt(id));
            }
            User currentUser = (User) session.getAttribute("user");
            if(("user".equals(userRole.getName()) && (user.getId() != currentUser.getId()))){
                log.error("user {} has no right to see the user  card № {}", user.getName(), user.getId());
                req.setAttribute("errorMessage",  ServiceUtil.getKey("no_right_for_document", "en"));
                return Path.PAGE_ERROR_PAGE;
            }

            req.setAttribute("user", user);
            req.setAttribute("role", userRole.getName());
            BigDecimal total = new PaymentDAO().getTotal(user.getInvoiceId());
            req.setAttribute("total", total);
        } catch (Exception ex) {
            log.debug("exception open user {}", ex.getMessage());
            req.setAttribute("errorMessage",  ServiceUtil.getKey("no_document", "en"));
            return Path.PAGE_ERROR_PAGE;
        }
        return Path.PAGE_USER;
    }
}