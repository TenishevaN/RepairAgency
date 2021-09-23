package com.my.command;

import com.my.Path;
import com.my.ServiceUtil;
import com.my.db.dao.AccountLocalizationDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.AccountLocalization;
import com.my.db.model.Role;
import com.my.db.model.User;
import com.my.web.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateUserCommand implements Command {

    private static final Logger log = LogManager.getLogger(UpdateUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");
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
            log.debug("user  was not updated " + ex.getMessage());
            req.setAttribute("errorMessage",  ServiceUtil.getKey("user_was_not_updated", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }

        boolean masterRole = roleChanged(userRoleId, roleId);
         if(masterRole){
             String nameEn = req.getParameter("nameEN");
             AccountLocalization accountLocalization = null;
             if ((nameEn != null) || (!nameEn.isEmpty())) {

                 AccountLocalizationDAO accountLocalizationDAO = new AccountLocalizationDAO();
                 accountLocalization = accountLocalizationDAO.get(user.getId(), 1);
                 accountLocalization.setName(nameEn);
                 accountLocalizationDAO.update(accountLocalization);
             }
             String nameRU = req.getParameter("nameRU");
             if ((nameRU != null) || (!nameRU.isEmpty())) {
                 AccountLocalizationDAO accountLocalizationDAO = new AccountLocalizationDAO();
                 accountLocalization = accountLocalizationDAO.get(user.getId(), 3);
                 accountLocalization.setName(nameRU);
                 accountLocalizationDAO.update(accountLocalization);
             }
             String nameUK = req.getParameter("nameUK");
             if ((nameUK != null) || (!nameUK.isEmpty())) {
                 AccountLocalizationDAO accountLocalizationDAO = new AccountLocalizationDAO();
                 accountLocalization = accountLocalizationDAO.get(user.getId(), 2);
                 accountLocalization.setName(nameUK);
                 accountLocalizationDAO.update(accountLocalization);
             }
         }
        return Path.COMMAND_OPEN_USER_BY_ID + req.getParameter("id");
    }

    private boolean roleChanged(final int userRoleId, final int roleId) {

        int masterId = Role.MASTER.getId();
        if ((userRoleId != roleId) && ((roleId == masterId) || (userRoleId == masterId))) {
            Controller.setMasterList();
        }

        return (roleId == masterId);
    }
}
