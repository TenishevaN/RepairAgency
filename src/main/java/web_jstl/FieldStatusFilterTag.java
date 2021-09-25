package web_jstl;

import com.my.ServiceUtil;
import com.my.db.dao.StatusDAO;
import com.my.db.model.Status;
import org.apache.logging.log4j.LogManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class FieldStatusFilterTag extends SimpleTagSupport {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FieldStatusFilterTag.class);
    private String currentLocale;
    private int idStatus;

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    @Override
    public void doTag() throws JspException {
        String output = "";
        JspWriter out = getJspContext().getOut();
        output = "<select name = status_id>";
        StatusDAO statusDAO = new StatusDAO();
        List<Status> listStatus = statusDAO.getAll(currentLocale);
        if (idStatus == -1){
            output += "<option  value = -1  selected>" + ServiceUtil.getKey("all", currentLocale) + "</option>";
        } else{
            output += "<option  value = -1>" + ServiceUtil.getKey("all", currentLocale) + "</option>";
        }
        for (Status status : listStatus) {
            if (status.getId() == idStatus) {
                output += "<option  value = " + status.getId() + " selected>" + status.getName() + "</option>";
            } else{
                output += "<option  value = " + status.getId() + ">" + status.getName() + "</option>";
            }
        }
        output += "</select>";

        try {
            out.println(output);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
}

