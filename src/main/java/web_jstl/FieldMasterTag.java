package web_jstl;

import com.my.db.dao.UserDAO;
import com.my.db.model.User;
import com.my.web.Controller;
import org.apache.logging.log4j.LogManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class FieldMasterTag extends SimpleTagSupport {

    private int idMaster;
    private String nameRole;
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FieldMasterTag.class);

    public void setIdMaster(String idMaster) {
        this.idMaster = Integer.parseInt(idMaster);
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    @Override
    public void doTag() throws JspException {

        String output = "";
        JspWriter out = getJspContext().getOut();
        UserDAO userDAO = new UserDAO();
        User currentMaster = userDAO.get(idMaster);
        if (("admin".equals(nameRole)) || ("manager".equals(nameRole))) {
            output = "<select name = masterId>";
            List<User> listMaster = Controller.masterList;
            log.debug(" FieldMasterTag {} ", listMaster);
            for (User master : listMaster) {
                if (master.getId() == idMaster) {
                    output += "<option  value=" + master.getId() + " selected>" + master.getName() + "</option>";
                } else {
                    output += "<option  value=" + master.getId() + ">" + master.getName() + "</option>";
                }
            }
            output += "</select>";

        } else {
            output += (currentMaster != null) ? currentMaster.getName() : "";
        }
        try {
            out.println(output);
        } catch (IOException e) {
            log.debug(" FieldMasterTag exception {} ", e.getMessage());
        }
    }
}

