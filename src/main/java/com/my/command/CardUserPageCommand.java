package com.my.command;

import com.my.Path;

import com.my.ServiceUtil;
import com.my.db.dao.AccountLocalizationDAO;
import com.my.db.dao.InvoiceBalanceDAO;
import com.my.db.dao.InvoiceDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.AccountLocalization;
import com.my.db.model.Role;
import com.my.db.model.User;
import org.apache.logging.log4j.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

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
          //  BigDecimal total = new InvoiceBalanceDAO().getTotal(user.getInvoiceId());
            BigDecimal total = new InvoiceDAO().get(user.getInvoiceId()).getAmmount();
            System.out.println("total " + total);
            if(total != null){
                req.setAttribute("total", total.divide(BigDecimal.valueOf(100)));
                System.out.println("total result " + total);
            }

            AccountLocalizationDAO accountLocalizationDAO = new AccountLocalizationDAO();
            List<AccountLocalization> accountLocalization = accountLocalizationDAO.get(user.getId());
             for(AccountLocalization item : accountLocalization){
                if(item.getLanguage_id() == 1){
                    req.setAttribute("nameEN", item.getName());
                }
                if(item.getLanguage_id() == 2){
                    req.setAttribute("nameUK", item.getName());
                }
                if(item.getLanguage_id() == 3){
                    req.setAttribute("nameRU", item.getName());
                }
            }


        } catch (Exception ex) {
            log.debug("exception open user {}", ex.getMessage());
            req.setAttribute("errorMessage",  ServiceUtil.getKey("no_document", "en"));
            return Path.PAGE_ERROR_PAGE;
        }
        return Path.PAGE_USER;
    }
}