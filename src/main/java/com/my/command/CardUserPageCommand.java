package com.my.command;

import com.my.Path;

import com.my.ServiceUtil;
import com.my.db.dao.AccountLocalizationDAO;
import com.my.db.dao.InvoiceDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.AccountLocalization;
import com.my.db.model.Invoice;
import com.my.db.model.Role;
import com.my.db.model.User;
import org.apache.logging.log4j.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code CardUserPageCommand} class represents the implementation of the command to open the user card.
 */
public class CardUserPageCommand implements Command {

    private static final Logger log = LogManager.getLogger(CardUserPageCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        User user;
        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");
        try {
            Role userRole = (Role) session.getAttribute("role");
            UserDAO userDAO = new UserDAO();
            String id = req.getParameter("id");

            User currentUser = (User) session.getAttribute("user");
            if (id.isEmpty()) {
                user = userDAO.get(currentUser.getId());
            } else {
                user = userDAO.get(Integer.parseInt(id));
            }

            //Server side data validation
            if (("user".equals(userRole.getName()) && (user.getId() != currentUser.getId()))) {
                log.error("user {} has no right to see the user  card № {}", user.getName(), user.getId());
                req.setAttribute("errorMessage", ServiceUtil.getKey("no_right_for_document", currentLocale));
                return Path.PAGE_ERROR_PAGE;
            }

            String role = userRole.getName();
            req.setAttribute("user", user);
            req.setAttribute("role", role);
            System.out.println("role " + role);
            Invoice invoice = new InvoiceDAO().get(user.getInvoiceId());
            BigDecimal total = BigDecimal.ZERO;
            if(invoice != null) {
                total = invoice.getAmmount();
            }
            if (total != null) {
                total = total.divide(BigDecimal.valueOf(100));
            }

            req.setAttribute("total", total);

            String showAccount = "false";
            if (user.getRoleId() == 4) {
                showAccount = "true";
            }
            req.setAttribute("showAccount", showAccount);

            AccountLocalizationDAO accountLocalizationDAO = new AccountLocalizationDAO();
            List<AccountLocalization> accountLocalization = accountLocalizationDAO.get(user.getId());
            for (AccountLocalization item : accountLocalization) {
                if (item.getLanguage_id() == 1) {
                    req.setAttribute("nameEN", item.getName());
                }
                if (item.getLanguage_id() == 2) {
                    req.setAttribute("nameUK", item.getName());
                }
                if (item.getLanguage_id() == 3) {
                    req.setAttribute("nameRU", item.getName());
                }
            }
        } catch (Exception ex) {
            log.debug("exception open user {}", ex.getMessage());
            req.setAttribute("errorMessage", ServiceUtil.getKey("no_document", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }
        return Path.PAGE_USER;
    }
}