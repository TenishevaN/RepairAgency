package web_jstl;

import com.my.db.dao.ReviewDAO;
import com.my.db.model.Review;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class ReviewSectionTag extends SimpleTagSupport {

    private int idRepairRequest;
    private static final Logger log = Logger.getLogger(ReviewSectionTag.class);

    public void setIdRepairRequest(String idRepairRequest) {
        this.idRepairRequest = Integer.parseInt(idRepairRequest);
    }

    @Override
    public void doTag() throws JspException {
        String output = "";
        JspWriter out = getJspContext().getOut();
        ReviewDAO reviewDAO = new ReviewDAO();
        List<Review> reviews = reviewDAO.getAllByRequestId(idRepairRequest);

        if (reviews.size() == 0){
            return;
        }

        output +=  "<p>Reviews:</p>";
       for(Review item : reviews) {
           output +=  "<div >";
           output +=    "<time  class=date-answer > " + item.getDate() + "</time >";
           output +=    "</div >";
           output +=    "<p class=ask-info >";
           output +=    item.getComment();
           output +=    "</p >";
       }
        try {
            out.println(output);
            System.out.println("We get reviews: " + output);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
}

