package com.my.command;

import com.my.Path;
import com.my.ServiceUtil;
import com.my.db.dao.AccountLocalizationDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.AccountLocalization;
import com.my.db.model.Role;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * {@ code UpdateUserCommand} class represents the implementation of the command to update user in the database.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class UpdateUserCommand implements Command {

    private static final Logger log = LogManager.getLogger(UpdateUserCommand.class);
    private static int userRoleId;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");
        UserDAO userDAO = new UserDAO();
        int roleId = Integer.parseInt(req.getParameter("roleId"));
        User user = updateUser(req, userDAO, roleId);
        try {
            userDAO.update(user);
        } catch (Exception ex) {
            log.debug("user  was not updated " + ex.getMessage());
            req.setAttribute("errorMessage", ServiceUtil.getKey("user_was_not_updated", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }

        updateLocalizationForMaster(req, user);
        boolean masterRoleChaned = roleChanged(userRoleId, roleId);
        if (masterRoleChaned) {
            Map<User, List<AccountLocalization>> masters = userDAO.getMasterList();
            session.getServletContext().setAttribute("listMasters", masters);

            Map<User, List<AccountLocalization>> masters1 = (Map<User, List<AccountLocalization>>) session.getServletContext().getAttribute("listMasters");

        }
        return Path.COMMAND_OPEN_USER_BY_ID + req.getParameter("id");
    }

    private void updateLocalizationForMaster(HttpServletRequest req, User user) {

        String nameEn = req.getParameter("nameEN");
        if ((nameEn != null) || (!nameEn.isEmpty())) {
            updateAccauntLocalization(user, 1, nameEn);
        }
        String nameRU = req.getParameter("nameRU");
        if ((nameRU != null) || (!nameRU.isEmpty())) {
            updateAccauntLocalization(user, 3, nameRU);
        }
        String nameUK = req.getParameter("nameUK");
        if ((nameUK != null) || (!nameUK.isEmpty())) {
            updateAccauntLocalization(user, 2, nameUK);
        }
    }

    private void updateAccauntLocalization(User user, int i, String name) {

        AccountLocalization accountLocalization;
        AccountLocalizationDAO accountLocalizationDAO = new AccountLocalizationDAO();
        accountLocalization = accountLocalizationDAO.get(user.getId(), i);
        accountLocalization.setName(name);
        accountLocalizationDAO.update(accountLocalization);
    }

    private boolean roleChanged(final int userRoleId, final int roleId) {

        int masterId = Role.MASTER.getId();
        if ((userRoleId != roleId) && ((roleId == masterId) || (userRoleId == masterId))) {
            return true;
        }
        return false;
    }

    private User updateUser(HttpServletRequest req, UserDAO userDAO, int roleId) {

        User user = userDAO.get(Integer.parseInt(req.getParameter("id")));
        user.setLogin(req.getParameter("login"));
        user.setName(req.getParameter("name"));
        user.setEmail(req.getParameter("email"));
        userRoleId = user.getRoleId();
        user.setRoleId(roleId);
        String invoiceId = req.getParameter("invoiceId");
        if ((invoiceId != null) && (!invoiceId.isEmpty())) {
            user.setInvoiceId(Integer.parseInt(invoiceId));
        }
        return user;
    }
}
