package web_jstl;

import com.my.db.dao.StatusDAO;
import com.my.db.model.Status;
import org.apache.logging.log4j.LogManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class FieldStatusTag extends SimpleTagSupport {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FieldStatusTag.class);

    private int idStatus;
    private String nameRole;
    private String currentLocale;
    private String area;

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

        String output = "";
        StatusDAO statusDAO = new StatusDAO();
        List<Status> listStatus = statusDAO.getAll(currentLocale);

        if (area.equals("list")) {
            return statusDAO.get(currentLocale, idStatus).toString();
        }
        if (("admin".equals(nameRole)) || ("manager".equals(nameRole))) {

            output = "<select name=statusId>";
            for (Status status : listStatus) {
                if (status.getId() == idStatus) {
                    output += "<option  value=" + status.getId() + " selected>" + status.getName() + "</option>";
                } else {
                    output += "<option  value=" + status.getId() + ">" + status.getName() + "</option>";
                }
            }
            output += "</select>";
        } else if (("master".equals(nameRole))) {
            output = "<select name=statusId>";
            for (Status status : listStatus) {
                if (status.getId() == idStatus) {
                    output += "<option  value=" + status.getId() + " selected>" + status.getName() + "</option>";
                } else {
                    output += "<option  value=" + status.getId() + ">" + status.getName() + "</option>";
                }
            }
            output += "</select>";
        } else {
            output += statusDAO.get(currentLocale, idStatus);
        }
        return output;
    }
}

