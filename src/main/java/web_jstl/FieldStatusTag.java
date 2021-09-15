package web_jstl;

import com.my.db.dao.StatusDAO;
import com.my.db.model.Status;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class FieldStatusTag extends SimpleTagSupport {

    private int idStatus;
    private String nameRole;
    private String  currentLocale;
    private static final Logger log = Logger.getLogger(FieldStatusTag.class);

    public void setIdStatus(String idStatus) {
        this.idStatus = Integer.parseInt(idStatus);
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

       public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }

    @Override
    public void doTag() throws JspException {
        String output = "";
        JspWriter out = getJspContext().getOut();

        System.out.println("currentLocale --- " + currentLocale);
        StatusDAO statusDAO = new StatusDAO();
        List<Status> listStatus= statusDAO.getAll(currentLocale);
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
            //    if ((status != Status.DONE.ON_THE_JOB) && (status != Status.DONE) && (status.getId() != idStatus)) {
             //       continue;
            //    }
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

        try {
            out.println(output);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
}

