package com.my.command;

import com.my.FilePDF;
import com.my.Path;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FormReportPDFCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
        List<RepairRequest> list = repairRequestDAO.getAll();
        FilePDF.createDocumentPDF(list);
        return Path.COMMAND_REPORTS;
    }
}
