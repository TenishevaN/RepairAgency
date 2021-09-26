<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="balance_owed" required="true" rtexprvalue="true" type="java.lang.Double" %>
<%@ attribute name="role" required="true" rtexprvalue="true" type="java.lang.String" %>

<div class="form-inline">
    <div>
        <label><fmt:message key="balance_owed"></fmt:message> ${balance_owed}</label>
    </div>
    </br>
    <c:set var="role" value="${role}"></c:set>
    <c:if test="${role eq 'user'}">

        <form action="controller" method="get">
            <button type="button" class="custom-margin-2" data-toggle="modal"
                    data-target="#insertPaymentPage">
                <fmt:message key="pay"></fmt:message>
            </button>
        </form>
    </c:if>
</div>