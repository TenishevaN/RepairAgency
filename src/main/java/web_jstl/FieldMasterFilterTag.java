package web_jstl;

import com.my.ServiceUtil;
import com.my.db.model.AccountLocalization;
import com.my.db.model.Language;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;

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
    private int idLocale;
    private String currentLocale;
    private Map<User, List<AccountLocalization>> listMaster;

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }

    public void setIdMaster(int idMaster) {
        this.idMaster = idMaster;
    }

    public void setListMaster(Map<User, List<AccountLocalization>> listMaster) {
        this.listMaster = listMaster;
    }

    @Override
    public void doTag(){

        String output = "";
        JspWriter out = getJspContext().getOut();

        output = "<select name=master_id>";

        if (idMaster == -1) {
            output += "<option  value = -1  selected>" + ServiceUtil.getKey("all", currentLocale) + "</option>";
        } else {
            output += "<option  value = -1>" + ServiceUtil.getKey("all", currentLocale) + "</option>";
        }
        idLocale = Language.getId(currentLocale);
        for (Map.Entry<User, List<AccountLocalization>> master : listMaster.entrySet()) {
             output += formOptionForSelectField(master);
        }
        output += "</select>";

        try {
            out.println(output);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

    private String formOptionForSelectField(Map.Entry<User, List<AccountLocalization>> master) {

        String name = getNameForSelectMenu(master);
        if (master.getKey().getId() == idMaster) {
            return "<option  value = " + master.getKey().getId() + " selected>" + name + "</option>";
        } else {
            return "<option  value = " + master.getKey().getId() + ">" + name + "</option>";
        }
    }

    private String getNameForSelectMenu(Map.Entry<User, List<AccountLocalization>> master) {

        try {
            String name = master.getValue().stream().filter(c -> c.getLanguage_id() == idLocale).findFirst().get().getName();
            if (name != null) {
                return name;
            }
            return "";
        } catch (Exception ex) {
            log.debug(" master tag without name for localization {} ", currentLocale);
            return "";
        }
    }
}
