package web_jstl;

import com.my.db.dao.StatusDAO;
import com.my.db.model.Status;
import org.apache.logging.log4j.LogManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@ code FieldStatusTag} class represents the custom tag to display status field for the different roles.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class FieldStatusTag extends SimpleTagSupport {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FieldStatusTag.class);

    private int idStatus;
    private String nameRole;
    private String currentLocale;
    private String area;
    private String output;
    private List<Status> listStatus;

    public void setIdStatus(String idStatus) {
        this.idStatus = Integer.parseInt(idStatus);
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public void doTag() throws JspException {

        JspWriter out = getJspContext().getOut();
        String output = getOutput();
        try {
            out.println(output);
        } catch (IOException e) {
            log.debug(" FieldStatusTag exception {} ", e.getMessage());
        }
    }

    private String getOutput() {

        StatusDAO statusDAO = new StatusDAO();
        listStatus = statusDAO.getAll(currentLocale);

        if (area.equals("list")) {
            return statusDAO.get(currentLocale, idStatus).toString();
        }
        if ("admin".equals(nameRole)) {
            return formStatusSelectForAdmin();
        }
        if ("manager".equals(nameRole)) {
            return formStatusSelectForManager();
        }
        if (("master".equals(nameRole))) {
            return formStatusSelectForMaster();
        }
        output = ""+ statusDAO.get(currentLocale, idStatus);

        return output;
    }

    private String formStatusSelectForAdmin() {

        output = "<select name=statusId>";
        for (Status status : listStatus) {
            if (status.getId() == idStatus) {
                output += "<option  value=" + status.getId() + " selected>" + status.getName() + "</option>";
            } else {
                output += "<option  value=" + status.getId() + ">" + status.getName() + "</option>";
            }
        }
        output += "</select>";
        return output;
    }

    private String formStatusSelectForMaster() {

        output = "<select name=statusId>";
        List<Status> masterList = listStatus.stream().filter(item -> (item.getId() == 2) || (item.getId() == 5) || (item.getId() == idStatus)).collect(Collectors.toList());
        for (Status status : masterList) {
            if (status.getId() == idStatus) {
                output += "<option  value=" + status.getId() + " selected>" + status.getName() + "</option>";
            } else {
                output += "<option  value=" + status.getId() + ">" + status.getName() + "</option>";
            }
        }
        output += "</select>";
        return output;
    }

    private String formStatusSelectForManager() {

        output = "<select name=statusId>";
        List<Status> masterList = listStatus.stream().filter(item -> (item.getId() == 6) || (item.getId() == 3) || (item.getId() == 4) || (item.getId() == idStatus)).collect(Collectors.toList());
        for (Status status : masterList) {
            if (status.getId() == idStatus) {
                output += "<option  value=" + status.getId() + " selected>" + status.getName() + "</option>";
            } else {
                output += "<option  value=" + status.getId() + ">" + status.getName() + "</option>";
            }
        }
        output += "</select>";
        return output;
    }
}

