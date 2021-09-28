package com.my.command;

import com.my.Path;
import com.my.ServiceUtil;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code DeleteRepairRequestCommand} class represents the implementation of the command to delete repair request.
 */
public class DeleteRepairRequestCommand implements Command {

    private static final Logger log =  LogManager.getLogger(DeleteRepairRequestCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");
        try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            RepairRequest repairRequest = repairRequestDAO.get(Integer.parseInt(req.getParameter("id")));
            if("new".equals(repairRequest.getStatusName())){
                repairRequestDAO.delete(repairRequest);
            } else{
                req.setAttribute("errorMessage", ServiceUtil.getKey("only_new_request_can_be_deleted", currentLocale));
                return  Path.PAGE_ERROR_PAGE;
            }

         } catch (Exception ex) {
            log.debug("delete user exception "+ex.getMessage());
            return  Path.PAGE_ERROR_PAGE;
        }
        return Path.COMMAND_LIST_REQUESTS;
    }
}
