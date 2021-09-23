package com.my.command;

import com.my.Path;
import com.my.ServiceUtil;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InsertRepairRequestCommand implements Command {

    private static final Logger log =  LogManager.getLogger(InsertRepairRequestCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");
        try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            RepairRequest repairRequest = new RepairRequest();

            User user = (User) req.getSession().getAttribute("user");
            repairRequest.setUserId(user.getId());
            repairRequest.setDescription(req.getParameter("description"));
            repairRequestDAO.insert(repairRequest);

        } catch (Exception ex) {
            log.debug("request not added exception " + ex.getMessage());
            req.setAttribute("errorMessage",  ServiceUtil.getKey("request_not_added", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }
        return Path.COMMAND_LIST_REQUESTS;
    }
}
