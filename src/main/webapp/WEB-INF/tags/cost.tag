<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="cost" required="true" rtexprvalue="true" type="java.lang.Double" %>

<c:choose>
    <c:when test = "${cost != 0}">
        ${cost/100}
    </c:when>

    <c:otherwise>
        0.00
    </c:otherwise>
</c:choose>



