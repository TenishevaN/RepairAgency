package web_jstl;

import com.my.db.model.Role;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class FieldRoleTag extends SimpleTagSupport {

    private int id;
    private static final Logger log = Logger.getLogger(FieldStatusTag.class);

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }


    @Override
    public void doTag() throws JspException {
        String output = "";
        JspWriter out = getJspContext().getOut();


            output = "<select name=roleId>";
            for (Role role : Role.values()) {
                if (role.getId() == id) {
                    output += "<option  value=" + role.getId() + " selected>" + role.getName() + "</option>";
                } else {
                    output += "<option  value=" + role.getId() + ">" + role.getName() + "</option>";
                }
            }
            output += "</select>";


        try {
            out.println(output);
        } catch (IOException e) {
       //     log.debug(e.getMessage());
        }

    }
}

