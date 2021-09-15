package web_jstl;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class FieldDescriptionTag extends SimpleTagSupport {

    private String descriptionText;
    private String nameRole;
    private static final Logger log = Logger.getLogger(FieldDescriptionTag.class);

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    @Override
    public void doTag() throws JspException {
        String output = "";
        JspWriter out = getJspContext().getOut();

        if (("user".equals(nameRole))) {
            output = "<textarea rows=5 class=form-control name = description>" + descriptionText + "</textarea>";

        } else {
            output += descriptionText;
        }
        try {
            out.println(output);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
}

