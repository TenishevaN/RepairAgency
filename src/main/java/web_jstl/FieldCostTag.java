package web_jstl;

import org.apache.logging.log4j.LogManager;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code FieldCostTag} class represents the custom tag to display cost for the different roles.
 */
public class FieldCostTag extends SimpleTagSupport {

    private String costValue;
    private String nameRole;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FieldCostTag.class);

    public void setCostValue(String costValue) {
        this.costValue = costValue;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    @Override
    public void doTag() throws JspException {
        String output = "";
        JspWriter out = getJspContext().getOut();

        if (("admin".equals(nameRole)) || ("manager".equals(nameRole))) {
            output = "<input type = number step = 0.01 class=form - control name = cost value= " + costValue + ">";
        } else {
            output += costValue;
        }
        try {
            out.println(output);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
}

