package com.my.command;

import com.my.Path;
import com.my.db.dao.UserDAO;
import com.my.db.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        UserDAO userDAO = new UserDAO();
        User user = userDAO.get(Integer.parseInt(req.getParameter("id")));
        user.setLogin(req.getParameter("login"));
        user.setName(req.getParameter("name"));
        user.setEmail(req.getParameter("email"));
        user.setRoleId(Integer.parseInt(req.getParameter("roleId")));
        String invoiceId = req.getParameter("invoiceId");
        if ((invoiceId != null) && (!invoiceId.isEmpty())) {
            user.setInvoiceId(Integer.parseInt(invoiceId));
        }
        try {
            userDAO.update(user);
            return Path.COMMAND_OPEN_USER_BY_ID + req.getParameter("id");

            //        log.info("User " + user + " logged as " + userRole.toString().toLowerCase());
        } catch (Exception ex) {

            //   log.info("exception " + "insert user exception " + user);
            return Path.PAGE_ERROR_PAGE;
        }
    }
}
