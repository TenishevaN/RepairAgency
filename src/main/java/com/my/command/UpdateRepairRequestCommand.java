package com.my.command;

import com.my.Path;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class UpdateRepairRequestCommand implements Command {

    private static final Logger log = Logger.getLogger(UpdateRepairRequestCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

          try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            RepairRequest repairRequest = repairRequestDAO.get(Integer.parseInt(req.getParameter("idRepairRequest")));

            String statusId = req.getParameter("statusId");
            if(statusId != null && !statusId.isEmpty()){
                repairRequest.setStatusId(Integer.parseInt(statusId));
            }
            String masterId = req.getParameter("masterId");
            if((masterId != null) && (!masterId.isEmpty())){
               repairRequest.setMasterId(Integer.parseInt(masterId));
            }
            repairRequest.setMasterName(req.getParameter("master_name"));
            String cost = req.getParameter("cost");
            if( cost != null && !cost.isEmpty()){
                repairRequest.setCost(BigDecimal.valueOf(Long.parseLong(req.getParameter("cost"))));
            }

            String description = req.getParameter("description");
            if(description != null){
                repairRequest.setDescription(description);
            }
          repairRequestDAO.update(repairRequest);

        } catch (Exception ex) {
              log.debug(ex.getMessage());
        }
        return Path.COMMAND_OPEN_REPAIR_REQUEST_BY_ID + req.getParameter("idRepairRequest");
    }
}
