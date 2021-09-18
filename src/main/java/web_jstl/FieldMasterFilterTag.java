package web_jstl;

import com.my.db.dao.UserDAO;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class FieldMasterFilterTag extends SimpleTagSupport {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FieldMasterFilterTag.class);
    private int idMaster;
    private String currentLocale;

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }

    public void setIdMaster(int idMaster) {
        this.idMaster = idMaster;
    }

    @Override
    public void doTag() throws JspException {
        String output = "";
        JspWriter out = getJspContext().getOut();

        output = "<select name=master_id>";
        UserDAO userDAO = new UserDAO();
        List<User> listMaster = userDAO.getMasterList();
        if (idMaster == -1) {
            output += "<option  value = -1  selected>" + getKey("all", currentLocale) + "</option>";
        } else {
            output += "<option  value = -1>" + getKey("all", currentLocale) + "</option>";
        }
        for (User master : listMaster) {
            System.out.println("master.getId() --- " + master.getId());
            if (master.getId() == idMaster) {
                output += "<option  value = " + master.getId() + " selected>" + master.getName() + "</option>";
            } else {
                output += "<option  value = " + master.getId() + ">" + master.getName() + "</option>";
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
