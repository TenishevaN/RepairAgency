package com.my.command;

import com.my.Path;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteRepairRequestCommand implements Command {

    private static final Logger log =  LogManager.getLogger(DeleteRepairRequestCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            RepairRequest repairRequest = repairRequestDAO.get(Integer.parseInt(req.getParameter("id")));
            if("new".equals(repairRequest.getStatusName())){
                repairRequestDAO.delete(repairRequest);
            } else{
                req.setAttribute("errorMessage", "Only new request can be deleted!");
                return  Path.PAGE_ERROR_PAGE;
            }

         } catch (Exception ex) {
            log.debug("insert user exception "+ex.getMessage());
            return  Path.PAGE_ERROR_PAGE;
        }
        return Path.COMMAND_LIST_REQUESTS;
    }
}
