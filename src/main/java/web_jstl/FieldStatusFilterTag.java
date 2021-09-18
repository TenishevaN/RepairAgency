package web_jstl;

import com.my.db.dao.StatusDAO;
import com.my.db.model.Status;
import org.apache.logging.log4j.LogManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
        System.out.println(" -- currentLocale --" + currentLocale);
        output = "<select name = status_id>";
        StatusDAO statusDAO = new StatusDAO();
        List<Status> listStatus = statusDAO.getAll(currentLocale);
        if (idStatus == -1){
            output += "<option  value = -1  selected>" + getKey("all", currentLocale) + "</option>";
        } else{
            output += "<option  value = -1>" + getKey("all", currentLocale) + "</option>";
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

    public static String getKey(String key, String locale){
        ResourceBundle resource = ResourceBundle.getBundle("resources", new Locale(locale));
        return resource.getString(key);
    }
}

