package web_jstl;

import com.my.ServiceUtil;
import com.my.db.dao.UserDAO;
import com.my.db.model.AccountLocalization;
import com.my.db.model.Language;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * {@ code FieldMasterFilterTag} class represents the master filter tag to display the master filter.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
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
        String name = "";
        JspWriter out = getJspContext().getOut();

        output = "<select name=master_id>";

        UserDAO userDAO = new UserDAO();
        Map<User, List<AccountLocalization>> listMaster = userDAO.getMasterList();

        if (idMaster == -1) {
            output += "<option  value = -1  selected>" + ServiceUtil.getKey("all", currentLocale) + "</option>";
        } else {
            output += "<option  value = -1>" + ServiceUtil.getKey("all", currentLocale) + "</option>";
        }
        int idLocale = Language.getId(currentLocale);
        for (Map.Entry<User, List<AccountLocalization>> master : listMaster.entrySet()) {
            try {
                name = master.getValue().stream().filter(c -> c.getLanguage_id() == idLocale).findFirst().get().getName();
            } catch (Exception ex) {
                log.debug(ex.getMessage());
            }

            if (master.getKey().getId() == idMaster) {
                output += "<option  value = " + master.getKey().getId() + " selected>" + name + "</option>";
            } else {
                output += "<option  value = " + master.getKey().getId() + ">" + name + "</option>";
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
