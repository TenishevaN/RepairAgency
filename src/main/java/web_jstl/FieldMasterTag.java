package web_jstl;

import com.my.ServiceUtil;
import com.my.db.dao.UserDAO;
import com.my.db.model.AccountLocalization;
import com.my.db.model.Language;
import com.my.db.model.User;
import com.my.web.Controller;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.apache.logging.log4j.LogManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FieldMasterTag extends SimpleTagSupport {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FieldMasterTag.class);

    private int idMaster;
    private String nameRole;
    private String currentLocale;
    private String area;

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }


    public void setIdMaster(String idMaster) {
        this.idMaster = Integer.parseInt(idMaster);
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
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
            log.debug(" FieldMasterTag exception {} ", e.getMessage());
        }
    }

    private String getOutput() {

        String output = "";
        UserDAO userDAO = new UserDAO();
        User currentMaster = userDAO.get(idMaster);
        Map<User, List<AccountLocalization>> listMaster = Controller.masterList;
        int idLocale = Language.EN.getId(currentLocale);

        if (area.equals("list")) {
            if (currentMaster != null) {
                String name = listMaster.entrySet().stream().filter(map -> map.getKey().equals(currentMaster)).findFirst().get().getValue().stream().filter(c -> c.getLanguage_id() == idLocale).findFirst().get().getName();
                output += name;
            }
            return output;
        }
        if (("admin".equals(nameRole)) || ("manager".equals(nameRole))) {
            output = "<select name = masterId>";
            log.debug(" FieldMasterTag {} ", listMaster);
            if (idMaster == -1) {
                output += "<option  value = -1></option>";
            }
            for (Map.Entry<User, List<AccountLocalization>> master : listMaster.entrySet()) {
                String name = master.getValue().stream().filter(c -> c.getLanguage_id() == idLocale).findFirst().get().getName();
                if (master.getKey().getId() == idMaster) {
                    output += "<option  value=" + master.getKey().getId() + " selected>" + name + "</option>";
                } else {
                    output += "<option  value=" + master.getKey().getId() + ">" + name + "</option>";
                }
            }
            output += "</select>";
            return output;

        } else {
            if (currentMaster != null) {
                String name = listMaster.entrySet().stream().filter(map -> map.getKey().equals(currentMaster)).findFirst().get().getValue().stream().filter(c -> c.getLanguage_id() == idLocale).findFirst().get().getName();
                output += name;
                return output;
            }
        }
        return output;
    }
}

