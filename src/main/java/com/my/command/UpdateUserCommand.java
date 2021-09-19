package com.my.command;

import com.my.Path;
import com.my.db.dao.UserDAO;
import com.my.db.model.Role;
import com.my.db.model.User;
import com.my.web.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserCommand implements Command {

    private static final Logger log = LogManager.getLogger(UpdateUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        UserDAO userDAO = new UserDAO();
        User user = userDAO.get(Integer.parseInt(req.getParameter("id")));
        user.setLogin(req.getParameter("login"));
        user.setName(req.getParameter("name"));
        user.setEmail(req.getParameter("email"));
        int roleId = Integer.parseInt(req.getParameter("roleId"));
        int userRoleId = user.getRoleId();
        user.setRoleId(roleId);
        String invoiceId = req.getParameter("invoiceId");
        if ((invoiceId != null) && (!invoiceId.isEmpty())) {
            user.setInvoiceId(Integer.parseInt(invoiceId));
        }
        try {
            userDAO.update(user);
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            return Path.PAGE_ERROR_PAGE;
        }
        roleChanged(userRoleId, roleId);
        return Path.COMMAND_OPEN_USER_BY_ID + req.getParameter("id");
    }

    private void roleChanged(final int userRoleId, final int roleId) {

        int masterId = Role.MASTER.getId();
        if ((userRoleId != roleId) && ((roleId == masterId) || (userRoleId == masterId))) {
            Controller.setMasterList();
        }
    }
}
